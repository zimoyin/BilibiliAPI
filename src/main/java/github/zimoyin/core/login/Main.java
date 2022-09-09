package github.zimoyin.core.login;

import github.zimoyin.core.cookie.Cookie;
import github.zimoyin.core.login.login.LoginImpl;
import github.zimoyin.core.utils.JsonSerializeUtil;

import java.io.*;

public class Main {
    public static void main(String[] args) throws Exception {
        //推荐方法
        Login login = new LoginImpl();
        Cookie cookie = login.login();
        /**
         * 将cookie保存到硬盘
         */
        //方式1
        JsonSerializeUtil.write(cookie.toJSONString(),"./cache/webcookie.json");
        //方式2
        Cookie.writeCookie(cookie,"./cache/webcookie.json");
        /**
         * 将cookie还原成对象
         */
        Cookie cookie0 = Cookie.readCookie("./cache/webcookie.json");
    }


}
