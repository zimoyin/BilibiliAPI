package github.zimoyin.bili.login.login;

import github.zimoyin.bili.cookie.Cookie;
import github.zimoyin.bili.cookie.GlobalCookie;
import github.zimoyin.bili.utils.qrcode.ZxingImageQR;

public class LoginConsoleQR {
    public void login() throws Exception {
        //获取URL
        LoginURL loginURL = new LoginURL();
        String url = loginURL.getLoginUrl();
        //打印二维码
        showQRTerminal(url);
        //监听是否登录
        long ts = loginURL.getTs();
        String key = loginURL.getKEY();
        LoginListen loginListen = new LoginListen(key, ts);
        Cookie cookie = loginListen.listen();
        //设置全局第二Cookie
        GlobalCookie.getInstance().setCookie(cookie);
    }




    /**
     * 从控制台中打印二维码
     */
    public void showQRTerminal(String url) throws Exception {
        ZxingImageQR.print(url);
    }
}
