package login;

import github.zimoyin.core.cookie.Cookie;
import github.zimoyin.core.cookie.WebCookie;
import github.zimoyin.core.exception.DownloadException;
import github.zimoyin.core.utils.JsonSerializeUtil;
import github.zimoyin.core.utils.net.httpclient.HttpClientResult;
import github.zimoyin.core.utils.net.httpclient.HttpClientUtils;
import javafx.application.Platform;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import static github.zimoyin.core.utils.JsonSerializeUtil.getJsonPath;

/**
 * 监听是否登录成功
 */
public class LoginListen {
    private static final String URL = "http://passport.bilibili.com/qrcode/getLoginInfo";
    /**
     * 超时时间，180s
     */
    private static final long TIME_OUT = 180;
    /**
     * 登录key
     */
    private String KEY;
    /**
     * 秘钥申请的时间戳，由服务器给出。
     * 程序会判断，如果时间超过服务器规定时间范围就抛出异常
     * 单位 s
     */
    private volatile long ts = -1;

    private Stage stage;

    public LoginListen(Stage stage, String KEY, long ts) {
        this.KEY = KEY;
        this.ts = ts;
        this.stage = stage;
    }

    public Cookie listen() {
        try {
            while (true) {
                //睡眠 2 s
                Thread.sleep(2000);
                //超时退出
                if (System.currentTimeMillis() / 1000 - ts > TIME_OUT) break;
                //判断窗口是否正常
                isStare();
                //获取登录信息
                String page = getPage();
                System.out.println(page);
                //是否登录成功
                boolean isLogin = isLogin(page);
                //进入下一循环
                if (!isLogin) continue;
                //获取Cookie
                Cookie cookie = getCookie(page);
                //返回cookie
                return cookie;
            }
        } catch (Exception e) {
            throw new DownloadException("登录失败", e);
        }
        return null;
    }

    private void isStare() {
        Platform.runLater(() -> stage.show());
    }


    /**
     * 访问URL
     *
     * @return
     * @throws IOException
     */
    public String getPage() throws IOException {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("oauthKey", this.KEY);

        HttpClientResult result = HttpClientUtils.doPost(URL, params);
        return result.getContent();
    }

    /**
     * 是否登录成功
     *
     * @return
     * @throws IOException
     */
    private boolean isLogin(String result) {
        String read = null;
        try {
            read = JsonSerializeUtil.getJsonPath().read(result, "/code/");
        } catch (Exception e) {
            return false;
        }
        if (read == null) return false;
        switch (read) {
            case "0":
                return true;
            case "-412":
                throw new DownloadException("登录失败，触发风控策略");
        }

        return false;
    }

    /**
     * 获取Cookie
     *
     * @param result
     * @return
     * @throws MalformedURLException
     */
    private Cookie getCookie(String result) throws MalformedURLException {
        ts = -1;
        String url = getJsonPath().read(result, "/data/url");
        WebCookie cookie = new WebCookie();
        cookie.setCookie(new URL(url));
        return cookie;
    }
}
