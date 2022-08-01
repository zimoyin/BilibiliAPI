package github.zimoyin.core.barrage;

import github.zimoyin.core.barrage.barrage.video.HistoryBarrage;
import github.zimoyin.core.barrage.barrage.video.HistoryBarrageAllHandle;
import github.zimoyin.core.barrage.barrage.video.RealTimeBarrage;
import github.zimoyin.core.barrage.data.Barrage;
import github.zimoyin.core.cookie.Cookie;
import github.zimoyin.core.utils.net.httpclient.HttpClientUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.function.Consumer;


public class Main {

    public static void main(String[] args) throws Exception {
//        RealTimeBarrage barrages = new RealTimeBarrage(Cookie.readCookie("./cache/webcookie.json"));
//        HashSet<Barrage> bv1dZ4y1Y7bt = barrages.getBarrageAll("BV1KZ4y1e774");
//        bv1dZ4y1Y7bt.forEach(new Consumer<Barrage>() {
//            @Override
//            public void accept(Barrage barrage) {
//                System.out.println(barrage);
//            }
//        });
//        System.out.println(bv1dZ4y1Y7bt.size());

//        HistoryBarrage barrages1 = new HistoryBarrage(Cookie.readCookie("./cache/webcookie.json"));
        FileWriter fileWriter = new FileWriter("./b.txt");
        HistoryBarrage barrages1 = new HistoryBarrage();
        ArrayList<Barrage> all = barrages1.getBarrageAll("BV1KZ4y1e774", 1000, new HistoryBarrageAllHandle() {
            @Override
            public void handle(String date, ArrayList<Barrage> barrages) {
                //遍历当然的弹幕列表
                barrages.forEach(barrage -> {
                    //打印弹幕内容
                    System.out.println("弹幕列表的日期:  "+date  +" 弹幕发送的日期: "+barrage.getSendTime()+" 今天的弹幕内容："+ barrage.getText());
                    try {
                        fileWriter.write("弹幕列表的日期: "+date  +" 弹幕发送的日期: "+barrage.getSendTime()+" 今天的弹幕内容："+ barrage.getText());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
            }
        });
        fileWriter.close();
    }
}
