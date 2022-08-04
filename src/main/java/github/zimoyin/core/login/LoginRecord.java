package github.zimoyin.core.login;


import com.alibaba.fastjson.JSONObject;
import github.zimoyin.core.cookie.Cookie;
import github.zimoyin.core.cookie.GlobalCookie;
import github.zimoyin.core.exception.CookieNotFoundException;
import github.zimoyin.core.login.pojo.record.LoginRecordJsonRootBean;
import github.zimoyin.core.utils.net.httpclient.HttpClientResult;
import github.zimoyin.core.utils.net.httpclient.HttpClientUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

/**
 * 登录记录
 */
public class LoginRecord {
    private static final String URL = "http://api.bilibili.com/x/safecenter/login_notice?mid=%s";

    private long mid;
    private Cookie cookie;
    public LoginRecord(long mid, Cookie cookie){
        this.mid = mid;
        this.cookie = cookie;
    }

    public LoginRecordJsonRootBean getJsonPojo() throws IOException, NoSuchAlgorithmException, KeyStoreException, URISyntaxException, KeyManagementException {
        String page = getPage();
        LoginRecordJsonRootBean b = JSONObject.parseObject(page, LoginRecordJsonRootBean.class);
        return b;
    }

    public String getPage() throws IOException, NoSuchAlgorithmException, KeyStoreException, URISyntaxException, KeyManagementException {
        String url = String.format(URL, mid);
        HttpClientResult result = HttpClientUtils.doGet(url,cookie.toHeaderCookie() ,null);
        return result.getContent();
    }
}
