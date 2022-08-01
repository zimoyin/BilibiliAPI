package github.zimoyin.core.login.web;

import com.google.zxing.WriterException;
import github.zimoyin.core.login.Login;
import github.zimoyin.core.cookie.WebCookie;
import github.zimoyin.core.exception.LoginException;
import github.zimoyin.core.exception.QRcodeLoginTimeOutException;
import github.zimoyin.core.utils.net.httpclient.HttpClientResult;
import github.zimoyin.core.utils.net.httpclient.HttpClientUtils;
import github.zimoyin.core.utils.qrcode.QRCodeUtils;
import github.zimoyin.core.utils.qrcode.ZxingImageQR;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.regex.Pattern;

import static github.zimoyin.core.utils.JsonSerializeUtil.getJsonPath;


/**
 * 参考-githuib: https://github.com/SocialSisterYi/bilibili-API-collect/blob/master/login/login_action/QR.md
 *
 * @author zimo
 * @update 2022-02-11  zimo 创建类
 * @title 二维码登录
 * @Description: 通过扫码二维码来登录 WEB 端
 * @API: https://github.com/SocialSisterYi/bilibili-API-collect
 */
public class WEBQRLogin implements Login {
    /**
     * 获取登录（URL）二维码的URL
     * 两种方式，
     * 1. 去扫码
     * 2. 直接去浏览器访问（不一定成功）
     */
    private static final String WEB_QR_URL = "http://passport.bilibili.com/qrcode/getLoginUrl";
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
    private static final String WEB_URL_LOGIN = "http://passport.bilibili.com/qrcode/getLoginInfo";
    /**
     * 超时时间，180s
     */
    private static final long TIME_OUT = 180;
    /**
     * 登录key
     */
    private static String KEY;
    /**
     * 参数列表，用来构建请求体
     */
    private HashMap<String, String> param = new HashMap<>();
    /**
     * 秘钥申请的时间戳，由服务器给出。
     * 程序会判断，如果时间超过服务器规定时间范围就抛出异常
     * 单位 s
     */
    private volatile long ts = -1;

    /**
     * 尝试登陆的秒数 5分钟，如果在规定时间内，程序未能给ts 字段赋值，那么就退出登陆抛出异常
     */
    private long loginTime = -1;


    /**
     * 从控制台中打印二维码
     */
    private void showQRTerminal() throws Exception {
        String loginUrl = getLoginUrl();
        ZxingImageQR.print(loginUrl);
    }


    public WebCookie login() throws Exception {
        return login(0);
    }


    /**
     * 获取登录验证URL，并以此生成二维码。通过扫描登录
     * @return
     * @throws Exception
     */
    public String getLoginUrl() throws Exception {
        //获取一个登录验证URL得JSON信息字符串
        HttpClientResult httpClientResult = HttpClientUtils.doGet(WEB_QR_URL);
        String content = httpClientResult.getContent();
        //获取URL
        String url = getJsonPath().read(content, "/data/url");
        //获取URL扫码的限制时间
        ts = Long.parseLong(getJsonPath().read(content, "/ts"));
        //获取KEY，以此用来给服务器区分登录信息
        KEY = getJsonPath().read(content, "data/oauthKey");
        param.put("oauthKey", KEY);
        return url;
    }

    /**
     * 获取这个URL的图片，通常用来生成一个二维码图像
     * @param url
     * @return
     * @throws WriterException
     * @throws IOException
     */
    public byte[] getQRImage(String url) throws WriterException, IOException {
       return  QRCodeUtils.create(url).toByteArray();
    }

    /**
     * 窗口对象
     */
    JFrame frame;
    /**
     * @param showQRmethod 0 - 在控制台打印二维码
     *                     1 - 开启一个线程加载窗体显示二维码
     */
    public WebCookie login(int showQRmethod) throws Exception {
        loginTime = System.currentTimeMillis();
        //输出二维码
        switch (showQRmethod) {
            case 0:
                showQRTerminal();
                break;
            case 1:
                QRWindow qrWindow = new QRWindow();
                qrWindow.showQRWindownThread(getLoginUrl());
                long timeS = System.currentTimeMillis();
                while (frame == null){
                    frame = qrWindow.getFrame();
                    if ((System.currentTimeMillis() - timeS)/1000 >= 10){
                        throw new LoginException("无法加载出二维码窗口");
                    }
                }
                break;
        }
        //监听登录
        WebCookie webCookie = listenLogin();
        return webCookie;
    }

    /**
     * 监听登录
     * 循环监听是否登录成功，如果在规定时间内未能成功就跑出异常
     */
    private WebCookie listenLogin() throws Exception {

        WebCookie cookie = null;
        String message = "";
        while ((ts < 0 || System.currentTimeMillis() / 1000 - ts <= TIME_OUT) && System.currentTimeMillis() - loginTime <= 5 * 60 * 1000) {
            show();//一直显示窗口

            if (ts < 0) continue;
            Thread.sleep(800);

            //询问服务器是否登录成功
            HttpClientResult httpClientResult = HttpClientUtils.doPost(WEB_URL_LOGIN, param);
            String content = httpClientResult.getContent();
            //查看字段是否存在
            boolean isMatch = Pattern.matches("message", content);
            //import static github.zimoyin.core.utils.JsonSerializeUtil.getJsonPath; 可以直接调用其他类里面的方法
            if (isMatch) message = getJsonPath().read(content, "/message");
            //如果不为空保说明登录失败，就进入下一次循环，并把最近的登录信息存起来
            if (isMatch && !message.trim().isEmpty()) {
                continue;
            }
            //登录成功，获取登录状态，为true 就是成功了
            String status = getJsonPath().read(content, "/status");
            if ("true".equalsIgnoreCase(status)) {
                ts = -1;
                String url = getJsonPath().read(content, "/data/url");
                cookie = new WebCookie();
                cookie.setCookie(new URL(url));
                hide();//隐藏并卸载窗口
                return cookie;
            }
        }
        hide();
        ts = -1;
        throw new LoginException("登录失败！原因：" + message, new QRcodeLoginTimeOutException());
    }



    private void show(){
        if (frame != null){
            System.out.println(frame.isShowing());
        }
    }

    private void hide(){
        if (frame != null) {
            frame.setVisible(false);
            frame.dispose();
            frame = null;
        }
    }
}
