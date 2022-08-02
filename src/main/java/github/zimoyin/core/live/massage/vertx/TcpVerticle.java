package github.zimoyin.core.live.massage.vertx;

import com.alibaba.fastjson.JSONObject;
import github.zimoyin.core.live.massage.MassageStream;
import github.zimoyin.core.live.pojo.info.DanmuInfoJsonRootBean;
import github.zimoyin.core.live.pojo.info.Host;
import io.vertx.core.*;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.net.NetSocket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.zip.Inflater;


/**
 * b站直播的TCP客户端
 */
public class TcpVerticle extends AbstractVerticle {
    public final Logger logger = LoggerFactory.getLogger(this.getClass());
    private String host;
    private String key;
    private int port;
    private long mid;
    private long roomid;
    private volatile boolean run = true;

    public TcpVerticle(long roomid){
        DanmuInfoJsonRootBean pojo = new MassageStream().getJsonPojo(roomid);
        pojo.setRoomID(roomid);
        init(pojo);
    }


    public TcpVerticle(DanmuInfoJsonRootBean json) {
        init(json);
    }

    private  void  init(DanmuInfoJsonRootBean json){
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
     * @param start
     * @throws Exception
     */
    @Override
    public void start(Promise<Void> start) throws Exception {
        logger.info("实例化了一个 Verticle");
        vertx.createNetClient()
                .connect(port, host)
                .onSuccess(this::successHandle)
                .onFailure(this::failureHandle)
                .onComplete(this::completeHandle);
    }


    /**
     * 开启TCP成功处理
     * @param socket
     */
    private void successHandle(NetSocket socket) {
        Package pack = new Package();
        logger.info("成功启动了一个 TCP 客户端在 " + host + ":" + port);
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
     * @param e
     */
    private void failureHandle(Throwable e) {
        logger.error("失败启动了一个 TCP 客户端在 " + host + ":" + port, e);
    }


    /**
     * 与服务器交互处理
     * @param header
     */
    private void completeHandle(AsyncResult<NetSocket> header) {
        NetSocket result = header.result();
        result.handler(buffer -> {
            logger.debug("服务器响应实际长度:" + buffer.length());
            logger.debug(new Package.Header(buffer).toString());

            try {
                analyzeData(buffer.getBytes());
            } catch (Exception e) {
                logger.error("解压失败", e);
            }

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
    private void analyzeData(byte[] data) {
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
                    } else if (action == 4) {
                        inputStream.readInt();
                        int msgBodyLength = dataLength - 16;
                        byte[] msgBody = new byte[msgBodyLength];
                        if (inputStream.read(msgBody) == msgBodyLength) {
                            //版本协议
                            if (data[7] == 2) {
                                analyzeData(ZLibUtil.decompress(msgBody));
                                return;
                            }
                            String jsonStr = new String(msgBody, "utf-8");
                            logger.debug("msg: {}", jsonStr);
                        }
                    }
                } else if (msgLength > 16 && msgLength < dataLength) {
                    byte[] singleData = new byte[msgLength];
                    System.arraycopy(data, 0, singleData, 0, msgLength);
                    analyzeData(singleData);
                    int remainLen = dataLength - msgLength;
                    byte[] remainDate = new byte[remainLen];
                    System.arraycopy(data, msgLength, remainDate, 0, remainLen);
                    analyzeData(remainDate);
                }
            } catch (Exception ex) {
               logger.error("解压服务器发包失败",ex);
            }
        }
    }

    /**
     * 发送心跳包
     * @param socket
     * @param pack
     */
    private void sendHeatLoop(NetSocket socket,Package pack){
        new Thread(() -> {
            while (run) {
                try {
                    Thread.sleep(28 * 1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                socket.write(pack.getHeart());
                logger.debug("发送心跳");
            }
        }).start();
    }
}
