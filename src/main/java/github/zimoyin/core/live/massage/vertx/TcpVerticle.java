package github.zimoyin.core.live.massage.vertx;

import com.alibaba.fastjson.JSONObject;
import github.zimoyin.core.cookie.Cookie;
import github.zimoyin.core.live.massage.LiveMassageHandle;
import github.zimoyin.core.live.massage.data.Massage;
import github.zimoyin.core.live.massage.MassageStreamInfo;
import github.zimoyin.core.live.pojo.message.host.DanmuInfoJsonRootBean;
import github.zimoyin.core.live.pojo.message.host.Host;
import github.zimoyin.core.utils.ZLibUtil;
import io.vertx.core.*;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.buffer.impl.BufferImpl;
import io.vertx.core.net.NetClientOptions;
import io.vertx.core.net.NetSocket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


/**
 * b站直播的TCP客户端
 */
public class TcpVerticle extends AbstractVerticle {
    public final Logger logger = LoggerFactory.getLogger(this.getClass());

    private volatile ArrayList<LiveMassageHandle> handles;
    /**
     * 服务器地址
     */
    private String host;
    private String key;
    /**
     * 服务器端口
     */
    private int port;
    /**
     * 用户mid
     */
    private long mid;
    /**
     * 房间号
     */
    private long roomid;
    private volatile boolean run = true;
    /**
     * 临时包。防止服务器分包发送
     */
    private volatile Buffer timPacker;
    /**
     * 人气
     */
    private volatile int hot;

    public TcpVerticle(long roomid) {
        DanmuInfoJsonRootBean pojo = new MassageStreamInfo().getJsonPojo(roomid);
        pojo.setRoomID(roomid);
        init(pojo);
    }

    public TcpVerticle(DanmuInfoJsonRootBean json) {
        init(json);
    }

    private void init(DanmuInfoJsonRootBean json) {
        logger.debug("获取直播间信息流前置信息：{}",json.toString());
        List<Host> host_list = json.getData().getHost_list();
        Host host = host_list.get(host_list.size() - 1);
        this.host = host.getHost();
        this.port = host.getPort();
        this.key = json.getData().getToken();
        this.roomid = json.getRoomID();
        this.mid = json.getMid();
    }

    /**
     * 启动一个 Verticle 但是请不要直接调用
     *
     * @param start
     * @throws Exception
     */
    @Override
    public void start(Promise<Void> start) throws Exception {
        logger.info("实例化了一个 Verticle");
        //tcp设置
        NetClientOptions netClientOptions = new NetClientOptions();
        netClientOptions.setReceiveBufferSize(1024 * 5);//设置缓冲区大小
        netClientOptions.setLogActivity(false);//网络活动日志
        netClientOptions.setConnectTimeout(30*1000);//设置超时时间
        //开启客户端
        vertx.createNetClient(netClientOptions)
                .connect(port, host)
                .onSuccess(this::successHandle)
                .onFailure(this::failureHandle)
                .onComplete(this::completeHandle);
    }


    /**
     * 开启TCP成功处理
     *
     * @param socket
     */
    private void successHandle(NetSocket socket) {
        Package pack = new Package();
        logger.info("成功启动了一个 TCP 客户端在 {}:{}", host, port);
        logger.info("直播间ID :{}", this.roomid);
        //构建认证包的正文，注意：老版本服务器除了 roomid 其余参数都是可选
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("key", this.key);
//        jsonObject.put("uid", 204700919);
        jsonObject.put("uid", this.mid);
        jsonObject.put("roomid", this.roomid);
        jsonObject.put("protover", 2);//版本协议，2代表zilb压缩，3代表br压缩
        jsonObject.put("platform", "web");
        jsonObject.put("type", 2);
        //发送包
        socket.write(pack.getCertificationPackage(jsonObject.toString()));
        logger.debug("客户端发送信息体: " + jsonObject.toString());
        //定时发生心跳
        sendHeatLoop(socket, pack);
    }


    /**
     * 开启TCP失败处理
     *
     * @param e
     */
    private void failureHandle(Throwable e) {
        logger.error("TCP 客户端启动失败,请等待30s后服务器重置链接后再试。访问地址：{}:{} ", host , port, e);
        close();
    }


    /**
     * 与服务器交互处理
     *
     * @param header
     */
    private void completeHandle(AsyncResult<NetSocket> header) {
        NetSocket result = header.result();
        if (header.failed()) return;//启动失败就直接退出
        result.handler(buffer -> {
            //打印本次状态
            Package.Header header0 = new Package.Header(buffer);
            logger.debug("服务器响应实际长度 {} byte", buffer.length());
            logger.debug(header0.toString());
            //合并临时包
            Buffer buffer0 = appendPacket(buffer);
            if (buffer0 != null) {
                //将变量从指向附加包，改为指向整包
                buffer = buffer0;
                //将变量从指向附加包头，改为指向整包头
                header0 = new Package.Header(buffer);
                logger.debug("变量置换完成");
            }
            //如果存在临时包就不进行操作
            if (timPacker != null) {
                logger.debug("等待一个附加包");
                return;
            }
            //是否是分包,包头定义的数据大于传过来的数据
            if (header0.getPageSize() > buffer.length()) {
                timPacker = buffer;
                //包头定义数值大于包本身
                logger.debug("发现分包，开始等待附加包。 包头描述长度:{},包实际大小:{}", header0.getPageSize(), buffer.length());
                return;
            } else {
                timPacker = null;
            }

            //构建包
            Package page = new Package(buffer);
            if (buffer.length() != page.getHeader().getPageSize())
                logger.warn("这是一个损耗的包 包实际长度:{}, 包头描述包长度:{}。数据如下:\n{}",
                        buffer.length(), page.getHeader().getPageSize(),
                        buffer.toString()
                );
            //构建本次的信息体
            Massage massage = new Massage();
            massage.setPage(page);
            massage.setHot(this.hot);
            //解压信息
            analyzeData(buffer.getBytes(), massage);
            //发送事件
            if (handles != null) {
                for (LiveMassageHandle handle : handles) {
                    handle.handle(massage);
                }
            }
        });
        //链接关闭
        result.closeHandler(handler -> {
            logger.info("tcp 客户端的链接被中断");
            close();
        });
    }


    /**
     * 合并临时包
     *
     * @param buffer
     */
    public Buffer appendPacket(Buffer buffer) {
        //如果存在临时包就合并包
        if (timPacker != null) {
            //上次发来的分包与这次的包是否可以进行合并，如果不能就舍弃分包
            Package.Header timHeader = new Package.Header(timPacker);
            int pageSize = timHeader.getPageSize();
            int bufLength = buffer.length();
            int timLength = timPacker.length();
            //判断这次的包是临时包的补充还是一个单独的整包,如果是整包就返回
            if (buffer.length() == new Package.Header(buffer).getPageSize()) {
                logger.debug("本次发包为整包，发现残留临时包，以清理临时包（此时应该称为残包）");
                timPacker = null;//清理临时包，因为此时他是个残包了
                return null;
            }
            //还缺包
            if (pageSize > (bufLength + timLength)) {
                logger.debug("还需等待一个附加包");
                timPacker.appendBuffer(buffer);
                return null;
            }
            //不缺包
            else if (pageSize == (bufLength + timLength)) {
                logger.debug("所有附加包等待完毕，开始合并");
                Buffer buffer0 = new BufferImpl();
                buffer0.appendBuffer(timPacker);
                buffer0.appendBuffer(buffer);
                buffer = buffer0;
                timPacker = null;
                logger.debug("合并包完成,并清理临时包。包头长度描述:{} ,包实际长度:{}",new Package.Header(buffer).getPageSize(), buffer.length());
                return buffer;
            }
        }
        return null;
    }

    /**
     * 解压包
     * 等待后续拆包改进，减少不必要开销
     *
     * @param data
     * @Author mxnter
     */
    private void analyzeData(byte[] data, Massage message) {
        int dataLength = data.length;
        if (dataLength < 16) {
            logger.error("错误的数据");
        } else {
            DataInputStream inputStream = new DataInputStream(new ByteArrayInputStream(data));
            try {
                int msgLength = inputStream.readInt();
                if (msgLength < 16) {
                    logger.warn("缓冲区大小可能需要扩大");
                } else if (msgLength > 16 && msgLength == dataLength) {
                    // 其实是两个char
                    inputStream.readInt();
                    int action = inputStream.readInt() - 1;
                    // 直播间在线用户数目
                    if (action == 2) {
                        inputStream.readInt();
                        int userCount = inputStream.readInt();
                        logger.debug("人气：" + userCount);
                        message.setHot(userCount);
                        this.hot=userCount;
                    } else if (action == 4) {
                        inputStream.readInt();
                        int msgBodyLength = dataLength - 16;
                        byte[] msgBody = new byte[msgBodyLength];
                        if (inputStream.read(msgBody) == msgBodyLength) {
                            //版本协议
                            if (data[7] == 2) {
                                analyzeData(ZLibUtil.decompress(msgBody), message);
                                return;
                            }
                            String jsonStr = new String(msgBody, "utf-8");
                            logger.debug("msg: {}", jsonStr);
                            message.addCommand(jsonStr);
                        }
                    }
                } else if (msgLength > 16 && msgLength < dataLength) {
                    byte[] singleData = new byte[msgLength];
                    System.arraycopy(data, 0, singleData, 0, msgLength);
                    analyzeData(singleData, message);

                    int remainLen = dataLength - msgLength;
                    byte[] remainDate = new byte[remainLen];
                    System.arraycopy(data, msgLength, remainDate, 0, remainLen);
                    analyzeData(remainDate, message);
                }
            } catch (Exception ex) {
                logger.error("解压服务器发包失败", ex);
            }
        }
    }

    /**
     * 发送心跳包
     *
     * @param socket
     * @param pack
     */
    private void sendHeatLoop(NetSocket socket, Package pack) {
        new Thread(() -> {
            logger.info("心跳开始发送");
            while (run) {
                try {
                    Thread.sleep(8 * 1000);
                } catch (InterruptedException e) {
                    logger.error("这是一个严重的错误，心跳线程没有正常的休眠",e);
                }
                if (run) socket.write(pack.getHeart());
                if (run) logger.debug("发送心跳");
            }
            logger.info("心跳停止发送");
        }).start();
    }

    /**
     * 添加监听
     * @param handles
     */
    public void setHandles(final ArrayList<LiveMassageHandle> handles) {
        this.handles = handles;
    }

    /**
     * 关闭所有资源
     */
    public void close() {
        vertx.close();
        this.run = false;
        logger.info("已销毁 Vertx,并对心跳线程发出停止指令");
    }
}
