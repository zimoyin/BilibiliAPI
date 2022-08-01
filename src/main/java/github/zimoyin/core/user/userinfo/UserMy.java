package github.zimoyin.core.user.userinfo;

import com.alibaba.fastjson.JSONObject;
import github.zimoyin.core.cookie.Cookie;
import github.zimoyin.core.user.pojo.my.UserMyRootBean;
import github.zimoyin.core.user.pojo.user.UserRootBean;
import github.zimoyin.core.utils.net.httpclient.HttpClientResult;
import github.zimoyin.core.utils.net.httpclient.HttpClientUtils;
import org.apache.http.HttpException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

public class UserMy {
    private static final String URL = "http://api.bilibili.com/x/space/myinfo";
    private Cookie cookie;
    private HashMap<String, String> headers = new HashMap<String, String>();

    public UserMy( Cookie cookie) {
        this.cookie = cookie;
        headers.clear();
        cookie.toHeaderCookie(headers);
    }



    public void setCookie(Cookie cookie) {
        this.cookie = cookie;
        headers.clear();
        cookie.toHeaderCookie(headers);
    }

    public UserMyRootBean getPojo() throws HttpException {
        String page = null;
        try {
            page = getPage();
        } catch (Exception e) {
            throw new HttpException("访问URL失败",e);
        }
        if (page == null) return null;
        UserMyRootBean bean = JSONObject.parseObject(page, UserMyRootBean.class);
        return bean;
    }


    /**
     * 获取用户的信息
     * @return
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws KeyStoreException
     * @throws URISyntaxException
     * @throws KeyManagementException
     */
    public String getPage() throws IOException, NoSuchAlgorithmException, KeyStoreException, URISyntaxException, KeyManagementException {
        HttpClientResult result = HttpClientUtils.doGet(URL, headers, null);
        return result.getContent();
    }

    public Cookie getCookie() {
        return cookie;
    }
}
