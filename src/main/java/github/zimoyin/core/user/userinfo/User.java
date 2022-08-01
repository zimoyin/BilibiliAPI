package github.zimoyin.core.user.userinfo;

import com.alibaba.fastjson.JSONObject;
import github.zimoyin.core.cookie.Cookie;
import github.zimoyin.core.user.pojo.user.UserRootBean;
import github.zimoyin.core.utils.net.httpclient.HttpClientResult;
import github.zimoyin.core.utils.net.httpclient.HttpClientUtils;
import lombok.Data;
import org.apache.http.HttpException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;


public class User {
    private static final String URL = "https://api.bilibili.com/x/space/acc/info?mid=%s";
    /**
     * 用户的唯一标识 允许通过SearchUser 来获取
     */
    private long mid;
    private Cookie cookie;
    private HashMap<String, String> headers = new HashMap<String, String>();

    public User() {
    }

    public User(long mid) {
        this.mid = mid;
    }

    /**
     *
     * @param mid 用户的唯一标识
     * @param cookie
     */
    public User(long mid, Cookie cookie) {
        this.mid = mid;
        this.cookie = cookie;
        headers.clear();
        cookie.toHeaderCookie(headers);
    }


    public void setCookie(Cookie cookie) {
        this.cookie = cookie;
        headers.clear();
        cookie.toHeaderCookie(headers);
    }

    public UserRootBean getPojo(long mid) throws HttpException {
        String page = null;
        try {
            page = getPage(mid);
        } catch (Exception e) {
            throw new HttpException("访问URL失败",e);
        }
        if (page == null) return null;
        UserRootBean bean = JSONObject.parseObject(page, UserRootBean.class);
        return bean;
    }

    public UserRootBean getPojo() throws HttpException {
//        String page = null;
//        try {
//             page = getPage(mid);
//        } catch (Exception e) {
//            throw new HttpException("访问URL失败",e);
//        }
//        if (page == null) return null;
//        UserRootBean bean = JSONObject.parseObject(page, UserRootBean.class);
//        return bean;

        return getPojo(mid);
    }

    /**
     * 获取用户的信息（注意要给mid字段进行赋值）
     * @return
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws KeyStoreException
     * @throws URISyntaxException
     * @throws KeyManagementException
     */
    public String getPage() throws IOException, NoSuchAlgorithmException, KeyStoreException, URISyntaxException, KeyManagementException {
        String url = String.format(URL, this.mid);
        HttpClientResult result = HttpClientUtils.doGet(url, headers, null);
        return result.getContent();
    }

    /**
     * 获取用户的信息
     * @param mid 用户的mid
     * @return
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws KeyStoreException
     * @throws URISyntaxException
     * @throws KeyManagementException
     */
    public String getPage(long mid) throws IOException, NoSuchAlgorithmException, KeyStoreException, URISyntaxException, KeyManagementException {
        String url = String.format(URL, mid);
        HttpClientResult result = HttpClientUtils.doGet(url, headers, null);
        return result.getContent();
    }

    /**
     * 用户的唯一标识 允许通过SearchUser 来获取
     * @return
     */
    public long getMid() {
        return mid;
    }

    /**
     * 用户的唯一标识 允许通过SearchUser 来获取
     * @return
     */
    public void setMid(long mid) {
        this.mid = mid;
    }

    public Cookie getCookie() {
        return cookie;
    }
}
