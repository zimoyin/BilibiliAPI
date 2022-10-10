package github.zimoyin.core.live.massage;

import github.zimoyin.core.cookie.Cookie;
import github.zimoyin.core.live.info.LiveInfo;
import github.zimoyin.core.live.massage.vertx.TcpVerticle;
import github.zimoyin.core.live.pojo.info.LiveInfoJsonRootBean;
import github.zimoyin.core.live.pojo.message.host.DanmuInfoJsonRootBean;
import io.vertx.core.Vertx;
import org.apache.http.HttpException;

import java.util.ArrayList;

public class LiveMessage {
    private ArrayList<LiveMassageHandle> handles = new ArrayList<LiveMassageHandle>();
    private TcpVerticle tcpVerticle;

    private Cookie cookie;


    private long roomid;

    /**
     * 游客身份进入直播间。
     * 运行TCP客户端，用来对接直播服务器
     *
     * @param roomid
     */
    public void run(long roomid) {
        run(roomid, null);
    }

    /**
     * 游客身份进入直播间。
     * 运行TCP客户端，用来对接直播服务器
     *
     * @param roomid
     * @param handle
     */
    public void run(long roomid, LiveMassageHandle handle) {
        //获取真实ID
        roomid = getLongRoomID(roomid);
        //TCP链接
        tcpVerticle = new TcpVerticle(roomid);
        tcpVerticle.setHandles(handles);
        Vertx.vertx().deployVerticle(tcpVerticle);
        //设置监听
        if (handle != null) handles.add(handle);
    }

    /**
     * 用自己账号进入直播间,注意需要cookie
     *
     * @param roomid
     * @param mid
     */
    public void run(long roomid, long mid) {
        run(roomid, mid, null);
    }

    /**
     * 用自己账号进入直播间,注意需要cookie
     *
     * @param roomid
     * @param mid
     * @param handle
     */
    public void run(long roomid, long mid, LiveMassageHandle handle) {
        //获取真实ID
        roomid = getLongRoomID(roomid);
        //获取key
        MassageStreamInfo info = new MassageStreamInfo();
        info.setCookie(cookie);
        info.setRoomid(roomid);
        info.setMid(mid);
        DanmuInfoJsonRootBean bean = info.getJsonPojo();
        //开启tcp
        tcpVerticle = new TcpVerticle(bean);
        tcpVerticle.setHandles(handles);
        Vertx.vertx().deployVerticle(tcpVerticle);
        //添加监听
        if (handle != null) handles.add(handle);
    }

    /**
     * 添加监听
     *
     * @param handle
     */
    public void setListen(LiveMassageHandle handle) {
        handles.add(handle);
    }

    /**
     * 移除监听器
     *
     * @param handle
     */
    public void removeListener(LiveMassageHandle handle) {
        handles.remove(handle);
    }

    /**
     * 关闭TCP
     */
    public void close() {
        tcpVerticle.close();
    }

    /**
     * 发送弹幕，注意需要登录
     *
     * @param msg
     * @return
     */
    public boolean sendBarrage(String msg) {
        LiveSendBarrage sendBarrage = new LiveSendBarrage(cookie);
        boolean b = sendBarrage.sendBarrage(String.valueOf(this.roomid), msg);
        return b;
    }


    public void setCookie(Cookie cookie) {
        this.cookie = cookie;
    }


    /**
     * 获取长房间号
     *
     * @return
     */
    public long getLongRoomID(long roomID) {
        long id = roomID;
        try {
            LiveInfoJsonRootBean pojo = new LiveInfo(roomID).getPojo(roomID);
            id = pojo.getData().getRoom_id();
            return id;
        }catch (Exception e){
            return id;
        }

    }
}
