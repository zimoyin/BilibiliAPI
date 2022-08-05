package login;

import github.zimoyin.core.cookie.Cookie;
import github.zimoyin.core.cookie.GlobalCookie;

public class Login {
    public static void main(String[] args) throws Exception {
        LoginWindowsQR.launch(LoginWindowsQR.class, args);
        Cookie cookie = GlobalCookie.getInstance().getCookie();
        System.out.println(cookie);
    }
}
