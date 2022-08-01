package github.zimoyin.core.login;

import github.zimoyin.core.cookie.Cookie;
import github.zimoyin.core.login.web.WEBQRLogin;
import github.zimoyin.core.utils.JsonSerializeUtil;

import java.io.*;

public class Main {
    public static void main(String[] args) throws Exception {

        /**
         * 登录web版 WEBQRLogin
         */
        WEBQRLogin webqrLogin  = new WEBQRLogin();
//        TVQRLogin webqrLogin = new TVQRLogin();
        Cookie login = webqrLogin.login(1);
        System.out.println(11);
//        Cookie cookie = new Cookie();
//        cookie.setCookie(new URL("https://passport.biligame.com/crossDomain?DedeUserID=204700919&DedeUserID__ckMd5=427a3d38a2f2f73b&Expires=15551000&SESSDATA=1c53aedc%2C1660134028%2C530db%2A21&bili_jct=1e43a4d758ab742b0e8660fba6bf0456&gourl=http%3A%2F%2Fwww.bilibili.com"));


        JsonSerializeUtil.write(login.toJSONString(),"./cache/webcookie.json");

        new File("./cache").mkdirs();

        //序列化
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("./cache/webcookie.bin"));
        objectOutputStream.writeObject(login);

        //反序列化
        ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("./cache/webcookie.bin"));
        Cookie o = (Cookie) objectInputStream.readObject();

        System.out.println(login);

//        Login login = Login.loginWeb();
//        Cookie login1 = login.login(0);
//        System.out.println(login1.toString());
    }

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
