package github.zimoyin.core.barrage;

import github.zimoyin.core.barrage.barrage.video.HistoryBarrage;
import github.zimoyin.core.barrage.barrage.video.HistoryBarrageAllHandle;
import github.zimoyin.core.barrage.barrage.video.RealTimeBarrage;
import github.zimoyin.core.barrage.data.Barrage;
import github.zimoyin.core.cookie.Cookie;
import github.zimoyin.core.cookie.GlobalCookie;
import github.zimoyin.core.utils.net.httpclient.HttpClientUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.function.Consumer;


public class Main {

    public static void main(String[] args) throws Exception {
        HistoryBarrage barrages1 = new HistoryBarrage(GlobalCookie.getInstance());
        ArrayList<Barrage> all = barrages1.getBarrageAll("BV1KZ4y1e774", 1000, new HistoryBarrageAllHandle() {
            @Override
            public void handle(String date, ArrayList<Barrage> barrages) {
                //遍历当然的弹幕列表
                barrages.forEach(barrage -> {
                    //打印弹幕内容
                    System.out.println("弹幕列表的日期:  "+date  +" 弹幕发送的日期: "+barrage.getSendTime()+" 今天的弹幕内容："+ barrage.getText());
                });
            }
        });
    }
}
