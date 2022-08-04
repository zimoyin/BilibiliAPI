package github.zimoyin.core.live.massage;

import github.zimoyin.core.cookie.Cookie;
import github.zimoyin.core.live.massage.vertx.TcpVerticle;
import io.vertx.core.Vertx;

import java.util.ArrayList;

public class LiveMassage {
    private ArrayList<LiveMassageHandle> handles = new ArrayList<LiveMassageHandle>();
    private TcpVerticle tcpVerticle;

    private Cookie cookie;


    private long roomid;
    /**
     * 运行TCP客户端，用来对接直播服务器
     * @param roomid
     */
    public void run(long roomid){
        this.roomid = roomid;

        tcpVerticle = new TcpVerticle(roomid);
        tcpVerticle.setHandles(handles);
        Vertx.vertx().deployVerticle(tcpVerticle);
    }

    /**
     * 运行TCP客户端，用来对接直播服务器
     * @param roomid
     * @param handle
     */
    public void run(long roomid,LiveMassageHandle handle){
        tcpVerticle = new TcpVerticle(roomid);
        tcpVerticle.setHandles( handles);
        Vertx.vertx().deployVerticle(tcpVerticle);
        handles.add(handle);
    }

    /**
     * 添加监听
     * @param handle
     */
    public  void setListen(LiveMassageHandle handle){
        handles.add(handle);
    }

    /**
     * 移除监听器
     * @param handle
     */
    public void removeListener(LiveMassageHandle handle){
        handles.remove(handle);
    }

    /**
     * 关闭TCP
     */
    public void close(){
        tcpVerticle.close();
    }

    /**
     * 发送弹幕，注意需要登录
     * @param msg
     * @return
     */
    public boolean sendBarrage(String msg){
        LiveSendBarrage sendBarrage = new LiveSendBarrage(cookie);
        boolean b = sendBarrage.sendBarrage(String.valueOf(this.roomid), msg);
        return b;
    }


    public void setCookie(Cookie cookie) {
        this.cookie = cookie;
    }
}
