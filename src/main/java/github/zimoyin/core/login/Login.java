package github.zimoyin.core.login;


import github.zimoyin.core.cookie.Cookie;
import github.zimoyin.core.cookie.WebCookie;


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
}
