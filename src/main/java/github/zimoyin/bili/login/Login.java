package github.zimoyin.bili.login;


import github.zimoyin.bili.cookie.Cookie;


/**
 * 登录接口，所有的登录都必须实现它。
 * 实现类：LoginImpl
 */
public interface Login {
    int ConsoleQR = 0;
    int WindowsQR = 1;

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
    Cookie login(int typeQR) throws Exception;
    Cookie login() throws Exception;
}
