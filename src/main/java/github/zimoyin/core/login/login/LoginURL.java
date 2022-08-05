package github.zimoyin.core.login.login;

import github.zimoyin.core.utils.net.httpclient.HttpClientResult;
import github.zimoyin.core.utils.net.httpclient.HttpClientUtils;
import lombok.Getter;

import java.util.HashMap;

import static github.zimoyin.core.utils.JsonSerializeUtil.getJsonPath;

@Getter
public class LoginURL {
    /**
     * 获取登录（URL）二维码的URL
     * 两种方式，
     * 1. 去扫码
     * 2. 直接去浏览器访问（不一定成功）
     */
    private static final String WEB_QR_URL = "http://passport.bilibili.com/qrcode/getLoginUrl";
    /**
     * 登录key
     */
    private  String KEY;
    /**
     * 参数列表，用来构建请求体
     */
    private HashMap<String, String> param = new HashMap<>();
    /**
     * 秘钥申请的时间戳，由服务器给出。
     * 程序会判断，如果时间超过服务器规定时间范围就抛出异常
     * 单位 s
     */
    private  long ts = -1;



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
}
