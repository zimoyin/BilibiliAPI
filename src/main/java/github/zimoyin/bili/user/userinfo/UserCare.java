package github.zimoyin.bili.user.userinfo;

import com.alibaba.fastjson.JSONObject;
import github.zimoyin.bili.cookie.Cookie;
import github.zimoyin.bili.user.pojo.usercare.UserCareRootBean;
import github.zimoyin.bili.utils.net.httpclient.HttpClientResult;
import github.zimoyin.bili.utils.net.httpclient.HttpClientUtils;
import org.apache.http.HttpException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

/**
 * 用户名片信息
 */
public class UserCare {
    private static final String URL = "http://api.bilibili.com/x/web-interface/card?mid=%s&bool=true";
    /**
     * 用户的唯一标识 允许通过SearchUser 来获取
     */
    private long mid;
    private Cookie cookie;
    private HashMap<String, String> headers = new HashMap<String, String>();

    public UserCare() {
    }

    public UserCare(long mid) {
        this.mid = mid;
    }

    /**
     *
     * @param mid 用户的唯一标识
     * @param cookie
     */
    public UserCare(long mid, Cookie cookie) {
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

    public UserCareRootBean getPojo(long mid) throws HttpException {
        String page = null;
        try {
            page = getPage(mid);
        } catch (Exception e) {
            throw new HttpException("访问URL失败",e);
        }
        if (page == null) return null;
        UserCareRootBean bean = JSONObject.parseObject(page, UserCareRootBean.class);
        return bean;
    }

    public UserCareRootBean getPojo() throws HttpException {
//        String page = null;
//        try {
//             page = getPage(mid);
//        } catch (Exception e) {
//            throw new HttpException("访问URL失败",e);
//        }
//        if (page == null) return null;
//        UserCareRootBean bean = JSONObject.parseObject(page, UserCareRootBean.class);
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
