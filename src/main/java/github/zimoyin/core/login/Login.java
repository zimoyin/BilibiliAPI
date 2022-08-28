package github.zimoyin.core.login;


import github.zimoyin.core.cookie.Cookie;
import github.zimoyin.core.cookie.WebCookie;
import github.zimoyin.core.login.tv.TVQRLogin;
import github.zimoyin.core.login.web.WEBQRLogin;

/**
 * 登录接口，所有的登录都必须实现它。
 * 实现类：LoginImpl
 */
public interface Login {
    public static final int ConsoleQR = 0;
    public static final int WindowsQR = 1;

    /**
     * 序列化保存的路径
     */
    String cookiePath = "./cache/cookie";
    /**
     *
     * @param typeQR 打印二维码的方式
     *          0- 控制台打印
     *          1- 窗口打印
     * @return
     * @throws Exception
     */
    public Cookie login(int typeQR) throws Exception;
    public Cookie login() throws Exception;

    /**
     * 获取登录WEB端的实例
     * @return
     */
    @Deprecated
    public static Login getLoginWeb(){
       return new WEBQRLogin();
    }
    /**
     * 获取登录TV端的实例
     * @return
     */
    @Deprecated
    public static Login getLoginTV(){
        return new TVQRLogin();
    }

    /**
     * 登录WEB端，并把cookie序列化到本地
     */
    @Deprecated
    public static Cookie loginWeb() throws Exception {
        WebCookie login = new WEBQRLogin().login(1);
        login.serializable(cookiePath);
        return login;
    }

    /**
     * 登录WEB端，并把cookie序列化到本地
     * @param cookiePath cookie保存的路径
     */
    @Deprecated
    public static Cookie loginWeb(String cookiePath) throws Exception {
        WebCookie login = new WEBQRLogin().login(1);
        login.serializable(cookiePath);
        return login;
    }


    /**
     * 登录TV端，并把cookie序列化到本地
     */
    @Deprecated
    public static Cookie loginTV(String cookiePath) throws Exception {
        WebCookie login = new WEBQRLogin().login(1);
        login.serializable(cookiePath);
        return login;
    }

    /**
     * 登录TV端，并把cookie序列化到本地
     */
    @Deprecated
    public static Cookie loginTV() throws Exception {
        WebCookie login = new WEBQRLogin().login(1);
        login.serializable(cookiePath);
        return login;
    }
}
