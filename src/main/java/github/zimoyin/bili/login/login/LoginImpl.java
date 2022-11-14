package github.zimoyin.bili.login.login;

import github.zimoyin.bili.cookie.Cookie;
import github.zimoyin.bili.cookie.GlobalCookie;
import github.zimoyin.bili.login.Login;


public class LoginImpl implements Login {
    @Override
    public Cookie login(int i) throws Exception {
        if (i == Login.ConsoleQR) {
            new LoginConsoleQR().login();
        }else {
            LoginWindowsQR.launch(LoginWindowsQR.class);
        }
        Cookie cookie = GlobalCookie.getInstance().getCookie();
        return cookie;
    }

    @Override
    public Cookie login() throws Exception {
        return login(Login.WindowsQR);
    }
}
