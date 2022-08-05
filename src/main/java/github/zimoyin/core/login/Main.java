package github.zimoyin.core.login;

import github.zimoyin.core.cookie.Cookie;
import github.zimoyin.core.login.login.LoginImpl;
import github.zimoyin.core.login.web.WEBQRLogin;
import github.zimoyin.core.utils.JsonSerializeUtil;

import java.io.*;

public class Main {
    public static void main(String[] args) throws Exception {

        Login login = new LoginImpl();
        Cookie cookie = login.login();
    }

    @Deprecated
    public void 登录并将Cookie保存到硬盘上() throws Exception {
        /**
         * 登录web版 WEBQRLogin
         */
//        WEBQRLogin webqrLogin  = new WEBQRLogin();
        Cookie cookie1 = Login.loginWeb();
        /**
         * 参数 -> 1：打开一个登录窗口，并用bilibili扫码登录
         * 参数 -> 0: 二维码打印在控制台
         */
//        Cookie login = webqrLogin.login(1);
        /**
         * 将cookie保存到硬盘
         */
        //方式1
//        JsonSerializeUtil.write(login.toJSONString(),"./cache/webcookie.json");
        //方式2
        Cookie.writeCookie(cookie1,"./cache/webcookie.json");
        /**
         * 将cookie还原成对象
         */
        Cookie cookie = Cookie.readCookie("./cache/webcookie.json");


    }


}
