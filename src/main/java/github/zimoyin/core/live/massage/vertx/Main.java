package github.zimoyin.core.live.massage.vertx;

import github.zimoyin.core.barrage.data.Barrage;
import github.zimoyin.core.live.massage.LiveMassage;
import github.zimoyin.core.live.massage.LiveMassageHandle;
import github.zimoyin.core.live.massage.data.Massage;

import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        long id = 5050;
//        DanmuInfoJsonRootBean pojo = new MassageStream().getJsonPojo(id);
//        pojo.setRoomID(id);
//        Vertx.vertx().deployVerticle(new TcpVerticle(pojo));
//        Vertx.vertx().deployVerticle(new TcpVerticle(id));

        LiveMassage liveMassage = new LiveMassage();
        liveMassage.run(id, new LiveMassageHandle() {
            @Override
            public void handle(Massage massage) {
                ArrayList<Barrage> barrages = massage.getBarrages();
                for (Barrage barrage : barrages) {
//                    System.out.println(barrage);
                }
            }
        });
//        Thread.sleep(8*1000);
//        liveMassage.close();
    }
}
