package github.zimoyin.core.live.massage;

import github.zimoyin.core.live.massage.vertx.TcpVerticle;
import io.vertx.core.Vertx;

import java.util.ArrayList;

public class LiveMassage {
    private ArrayList<LiveMassageHandle> handles = new ArrayList<LiveMassageHandle>();
    private TcpVerticle tcpVerticle;
    public void run(long roomid){
        tcpVerticle = new TcpVerticle(roomid);
        tcpVerticle.setHandles(handles);
        Vertx.vertx().deployVerticle(tcpVerticle);
    }

    public void run(long roomid,LiveMassageHandle handle){
        tcpVerticle = new TcpVerticle(roomid);
        tcpVerticle.setHandles( handles);
        Vertx.vertx().deployVerticle(tcpVerticle);
        handles.add(handle);
    }

    public  void setListen(LiveMassageHandle handle){
        handles.add(handle);
    }

    public void removeListener(LiveMassageHandle handle){
        handles.remove(handle);
    }

    public void close(){
        tcpVerticle.getVertx().close();
    }
}
