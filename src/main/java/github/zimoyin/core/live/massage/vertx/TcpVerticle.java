package github.zimoyin.core.live.massage.vertx;

import com.alibaba.fastjson.JSONObject;
import github.zimoyin.core.live.massage.LiveMassageHandle;
import github.zimoyin.core.live.massage.MassageStreamInfo;
import github.zimoyin.core.live.pojo.info.DanmuInfoJsonRootBean;
import github.zimoyin.core.live.pojo.info.Host;
import github.zimoyin.core.utils.ZLibUtil;
import io.vertx.core.*;
import io.vertx.core.net.NetClientOptions;
import io.vertx.core.net.NetSocket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;


/**
 * b站直播的TCP客户端
 */
public class TcpVerticle extends AbstractVerticle {
    public final Logger logger = LoggerFactory.getLogger(this.getClass());

    private volatile ArrayList<LiveMassageHandle> handles;
    private String host;
    private String key;
    private int port;
    private long mid;
    private long roomid;
    private volatile boolean run = true;

    public TcpVerticle(long roomid) {
        DanmuInfoJsonRootBean pojo = new MassageStreamInfo().getJsonPojo(roomid);
        pojo.setRoomID(roomid);
        init(pojo);
    }


    public TcpVerticle(DanmuInfoJsonRootBean json) {
        init(json);
    }

    private void init(DanmuInfoJsonRootBean json) {
        Host host = json.getData().getHost_list().get(2);
//        this.host = "tx-sh-live-comet-01.chat.bilibili.com";
//        this.port = 2243;
        this.host = host.getHost();
        this.port = host.getPort();
        this.key = json.getData().getToken();
        roomid = json.getRoomID();
        mid = json.getMid();
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
        netClientOptions.setReceiveBufferSize(1024 * 32);//设置缓冲区大小
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
        logger.info("成功启动了一个 TCP 客户端在 {}:{}", host,port);
        logger.info("直播间ID :{}",this.roomid);
        //构建认证包的正文
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("key", this.key);
        jsonObject.put("uid", this.mid);
        jsonObject.put("roomid", this.roomid);
        jsonObject.put("protover", 2);//版本协议，2代表zilb压缩，3代码br压缩
        jsonObject.put("platform", "web");
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
        logger.error("失败启动了一个 TCP 客户端在 " + host + ":" + port, e);
    }


    /**
     * 与服务器交互处理
     *
     * @param header
     */
    private void completeHandle(AsyncResult<NetSocket> header) {
        NetSocket result = header.result();
        result.handler(buffer -> {
            //打印本次状态
            Package page = new Package(buffer);
            logger.debug("服务器响应实际长度 {} byte", page.length());
            logger.debug(page.getHeader().toString());
            //构建本次的信息体
            LiveMassageHandle.Massage massage = new LiveMassageHandle.Massage();
            massage.setPage(page);

            //解压信息
            analyzeData(buffer.getBytes(), massage);
            massage.init();
            //发送事件
            vertx.exceptionHandler(throwable -> {
                if (handles != null && handles.size() > 0)
                    for (LiveMassageHandle handle : handles) {
                        massage.init();
                        handle.handle(massage);
                    }
            });
        });
        //链接关闭
        result.closeHandler(handler -> {
            logger.info("tcp 客户端的链接被中断");
            run = false;
            vertx.close();
        });
    }

    /**
     * 解压包
     * 等待后续拆包改进，减少不必要开销
     *
     * @param data
     * @Author mxnter
     */
    private void analyzeData(byte[] data, LiveMassageHandle.Massage message) {
        int dataLength = data.length;
        if (dataLength < 16) {
            logger.error("错误的数据");
        } else {
            DataInputStream inputStream = new DataInputStream(new ByteArrayInputStream(data));
            try {
                int msgLength = inputStream.readInt();
                if (msgLength < 16) {
                    logger.error("缓冲区大小可能需要扩大");
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
                    throw new RuntimeException(e);
                }
                if (run)socket.write(pack.getHeart());
                if (run)logger.debug("发送心跳");
            }
            logger.info("心跳停止发送");
        }).start();
    }

    public void setHandles(final ArrayList<LiveMassageHandle> handles) {
        this.handles = handles;
    }
}
