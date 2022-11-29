package github.zimoyin.bili.live.massage;

import github.zimoyin.bili.barrage.data.Barrage;
import github.zimoyin.bili.cookie.GlobalCookie;
import github.zimoyin.bili.exception.CookieNotFoundException;
import github.zimoyin.bili.live.massage.data.Message;
import github.zimoyin.bili.live.pojo.message.MessageData;

import java.util.Date;

public class Main {
    public static void main(String[] args) throws CookieNotFoundException {
        LiveMessage liveMassage = new LiveMessage();
        liveMassage.setCookie(GlobalCookie.getInstance());
        //697
        liveMassage.run(21652717, new LiveMassageHandle() {
            @Override
            public void handle(Message message) {
                for (MessageData messageData : message.getGift()) {
                    System.out.println("gift: "+messageData.getGiftName());
                }
                for (Barrage barrage : message.getBarrages()) {
                    System.out.println(new Date(barrage.getSendTime())+"  "+barrage.getAuthor()+":  "+barrage.getText());
                }
            }
        });
    }
}
