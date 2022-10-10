package github.zimoyin.core.live.massage;

import github.zimoyin.core.barrage.data.Barrage;
import github.zimoyin.core.cookie.GlobalCookie;
import github.zimoyin.core.exception.CookieNotFoundException;
import github.zimoyin.core.live.massage.data.Massage;

public class Main {
    public static void main(String[] args) throws CookieNotFoundException {
        LiveMessage liveMassage = new LiveMessage();
        liveMassage.setCookie(GlobalCookie.getInstance());
        liveMassage.run(697, new LiveMassageHandle() {
            @Override
            public void handle(Massage massage) {
                for (Barrage barrage : massage.getBarrages()) {
                    System.out.println(barrage.getAuthor()+":  "+barrage.getText());
                }
            }
        });
    }
}
