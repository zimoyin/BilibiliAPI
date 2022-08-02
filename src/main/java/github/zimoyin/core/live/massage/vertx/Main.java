package github.zimoyin.core.live.massage.vertx;

import github.zimoyin.core.live.massage.LiveMassage;
import io.vertx.core.Vertx;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        long id = 22472556;
//        DanmuInfoJsonRootBean pojo = new MassageStream().getJsonPojo(id);
//        pojo.setRoomID(id);
//        Vertx.vertx().deployVerticle(new TcpVerticle(pojo));
//        Vertx.vertx().deployVerticle(new TcpVerticle(id));

        LiveMassage liveMassage = new LiveMassage();
        liveMassage.run(id);
        Thread.sleep(8*1000);
        liveMassage.close();
    }
}
