package github.zimoyin.bili.login;

import github.zimoyin.bili.cookie.Cookie;
import github.zimoyin.bili.login.login.LoginImpl;
import github.zimoyin.bili.utils.JsonSerializeUtil;

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
