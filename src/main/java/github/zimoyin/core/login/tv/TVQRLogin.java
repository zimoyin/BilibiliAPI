package github.zimoyin.core.login.tv;

import com.google.zxing.WriterException;

import github.zimoyin.core.login.Login;
import github.zimoyin.core.cookie.TVCookie;
import github.zimoyin.core.exception.LoginException;
import github.zimoyin.core.exception.QRcodeLoginTimeOutException;
import github.zimoyin.core.utils.JsonSerializeUtil;
import github.zimoyin.core.utils.net.httpclient.HttpClientResult;
import github.zimoyin.core.utils.net.httpclient.HttpClientUtils;
import github.zimoyin.core.utils.qrcode.QRCodeUtils;
import github.zimoyin.core.utils.qrcode.ZxingImageQR;


import javax.swing.*;
import java.io.IOException;
import java.util.HashMap;


/**
 * 使用二维码登录TV版(不推荐使用)
 * 不再维护给予APP的一切操作
 * @API: https://github.com/SocialSisterYi/bilibili-API-collect
 */
@Deprecated
public class TVQRLogin implements Login {
    /**
     * 两种方式，1去扫码
     * 2. 直接去浏览器访问（不一定成功）
     */
    private static final String TV_QR_URL = "http://passport.bilibili.com/x/passport-tv-login/qrcode/auth_code";

    //APP方式必要 app 秘钥不可变更
    private static final String APPKEY = "4409e2ce8ffd12b8";
    /**
     * POST 请求
     *
     * @参数 :  application/x-www-form-urlencoded
     * oauthKey (必要)
     * gourl 跳转url	（非必要）  默认为http://www.bilibili.com
     * <p>
     * 密钥超时为180秒
     * 验证登录成功后会进行设置以下cookie项：
     * DedeUserID DedeUserID__ckMd5 SESSDATA bili_jct
     */
    private static final String TV_URL_LOGIN = "http://passport.bilibili.com/x/passport-tv-login/qrcode/poll";
    /**
     * 超时时间，180s
     */
    private static final long TIME_OUT = 180 * 1000;
    //TV端必要 TV端id
    private volatile String local_id = "0";
    //APP方式必要 APP签名  注意当 local_id 与 ts 变更后sign需要重新生成
    private volatile String sign = "e134154ed6add881d28fbdf68653cd9c";
    //APP方式必要 当前时间戳
    private volatile long ts = -1;
    //当前时间
    private static long time = System.currentTimeMillis();
    /**
     * 登录key
     */
    private volatile String KEY;
    /**
     * 参数列表，用来构建请求体
     */
    private volatile HashMap<String, String> param = new HashMap<>();
    /**
     * 二维码窗口
     */
    private volatile JFrame frame;


    /**
     * 创建并显示GUI。
     */
    private void createAndShowGUI(String url) throws IOException, WriterException {
        // 确保一个漂亮的外观风格
        JFrame.setDefaultLookAndFeelDecorated(true);


        // 创建及设置窗口
        frame = new JFrame("Login");
        ImageIcon icon = new ImageIcon(QRCodeUtils.create(url).toByteArray());
        // 添加 "Hello World" 标签
        JLabel label = new JLabel();
        label.setIcon(icon);
        frame.getContentPane().add(label);

        // 显示窗口
        frame.pack();
        frame.setVisible(true);

        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

    }

    private HttpClientResult doPostQR() throws Exception {
        param.put("appkey", "4409e2ce8ffd12b8");
        param.put("local_id", "0");
        param.put("ts", (String.valueOf(0)));
        param.put("sign", "e134154ed6add881d28fbdf68653cd9c");
        HttpClientResult httpClientResult = HttpClientUtils.doPost(TV_QR_URL, param);
        return httpClientResult;
    }


    private void setParam() {

    }

    /**
     * 用一个事件线程加载窗体来显示二维码
     */
    private void showQRWindownThread() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                // 显示应用 GUI
                SwingUtilities.invokeLater(() -> {
                    try {
                        String content = doPostQR().getContent();
                        String url = JsonSerializeUtil.getJsonPath().read(content, "/data/url");
                        String message = JsonSerializeUtil.getJsonPath().read(content, "message");
                        if (!"0".equalsIgnoreCase(JsonSerializeUtil.getJsonPath().read(content, "code"))) {
                            throw new LoginException(message);
                        }
                        KEY = JsonSerializeUtil.getJsonPath().read(content, "data/auth_code");
                        param.put("auth_code", KEY);
                        createAndShowGUI(url);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            }
        }).start();
    }

    /**
     * 用一个窗体来显示二维码
     */
    private void showQRWindown() throws Exception {
        // 显示应用 GUI
        String content = doPostQR().getContent();
        String url = JsonSerializeUtil.getJsonPath().read(content, "/data/url");
        String message = JsonSerializeUtil.getJsonPath().read(content, "message");
        if (!"0".equalsIgnoreCase(JsonSerializeUtil.getJsonPath().read(content, "code"))) {
            throw new LoginException(message);
        }
        KEY = JsonSerializeUtil.getJsonPath().read(content, "data/auth_code");
        param.put("auth_code", KEY);
        createAndShowGUI(url);
    }


    /**
     * 从控制台中打印二维码
     */
    private void showQRTerminal() throws Exception {
        String content = doPostQR().getContent();
        String url = JsonSerializeUtil.getJsonPath().read(content, "/data/url");
        String message = JsonSerializeUtil.getJsonPath().read(content, "message");
        if (!"0".equalsIgnoreCase(JsonSerializeUtil.getJsonPath().read(content, "code"))) {
            throw new LoginException(message);
        }
        KEY = JsonSerializeUtil.getJsonPath().read(content, "data/auth_code");
        param.put("auth_code", KEY);
        ZxingImageQR.print(url);
    }


    public TVCookie login() throws Exception {
        return login(0);
    }

    /**
     * @param showQRmethod 0 - 在控制台打印二维码
     *                     1 - 开启一个线程加载窗体显示二维码
     */
    public TVCookie login(int showQRmethod) throws Exception {
        //输出二维码
        switch (showQRmethod) {
            case 0:
                showQRTerminal();
                break;
            case 1:
                showQRWindownThread();
                break;
        }

        //监听登录
        return listenLogin();
    }

    /**
     * 监听登录
     * 循环监听是否登录成功，如果在规定时间内未能成功就跑出异常
     */
    private TVCookie listenLogin() throws Exception {
        TVCookie cookie = null;
        String message = "";
        while (ts <0 || System.currentTimeMillis()/1000 - ts <= TIME_OUT) {
            if (frame != null && !frame.isShowing()) frame.setVisible(true);
            Thread.sleep(800);

            HttpClientResult httpClientResult = HttpClientUtils.doPost(TV_URL_LOGIN, param);
            String content = httpClientResult.getContent();

//            try {
            message = JsonSerializeUtil.getJsonPath().read(content, "/message");
//            }catch (Exception e){
//                //这里吞掉异常，因为如果它发生异常就说明登录成功
//                message="0";//清空，执行后续操作
//            }
            //如果不为空保说明登录失败，就进入下一次循环，并把最近的登录信息存起来
            if (!message.trim().equalsIgnoreCase("0")) {
                continue;
            }
            String code = JsonSerializeUtil.getJsonPath().read(content, "/code");
            if ("0".equalsIgnoreCase(code)) {
                ts = -1;
                String mid = JsonSerializeUtil.getJsonPath().read(content, "/data/mid");
                String access_token = JsonSerializeUtil.getJsonPath().read(content, "/data/access_token");
                String refresh_token = JsonSerializeUtil.getJsonPath().read(content, "/data/refresh_token");
                String expires_in = JsonSerializeUtil.getJsonPath().read(content, "/data/expires_in");
                cookie = new TVCookie(mid, access_token, refresh_token, expires_in);
                if (frame != null) frame.setVisible(false);
                return cookie;
            }
        }
        if (frame != null) frame.setVisible(false);
        throw new LoginException("登录失败！原因：" + message, new QRcodeLoginTimeOutException());
    }
}
