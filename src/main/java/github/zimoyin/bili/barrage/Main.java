package github.zimoyin.bili.barrage;

import github.zimoyin.bili.barrage.barrage.video.HistoryBarrage;
import github.zimoyin.bili.barrage.barrage.video.HistoryBarrageAllHandle;
import github.zimoyin.bili.barrage.data.Barrage;
import github.zimoyin.bili.cookie.GlobalCookie;

import java.util.ArrayList;


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
