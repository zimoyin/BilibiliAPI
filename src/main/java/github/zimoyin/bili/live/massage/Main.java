package github.zimoyin.bili.live.massage;

import github.zimoyin.bili.barrage.data.Barrage;
import github.zimoyin.bili.cookie.GlobalCookie;
import github.zimoyin.bili.exception.CookieNotFoundException;
import github.zimoyin.bili.live.massage.data.Massage;

import java.util.Date;

public class Main {
    public static void main(String[] args) throws CookieNotFoundException {
        LiveMessage liveMassage = new LiveMessage();
        liveMassage.setCookie(GlobalCookie.getInstance());
        liveMassage.run(697, new LiveMassageHandle() {
            @Override
            public void handle(Massage massage) {
                for (Barrage barrage : massage.getBarrages()) {
                    System.out.println(new Date(barrage.getSendTime())+"  "+barrage.getAuthor()+":  "+barrage.getText());
                }
            }
        });
    }
}
