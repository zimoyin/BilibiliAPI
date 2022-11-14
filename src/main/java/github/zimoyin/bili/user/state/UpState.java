package github.zimoyin.bili.user.state;

import com.alibaba.fastjson.JSONObject;
import github.zimoyin.bili.cookie.Cookie;
import github.zimoyin.bili.user.pojo.state.up.UpStatJsonRootBean;
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
 * up状态，点赞数，播放数之类的，没啥大用，如果想了解完全可以还有其他API
 */
public class UpState {
    private static final String URL ="http://api.bilibili.com/x/space/upstat?mid=%s";


    /**
     * 用户的唯一标识 允许通过SearchUser 来获取
     */
    private long mid;
    private Cookie cookie;
    private HashMap<String, String> headers = new HashMap<String, String>();

    public UpState() {
    }

    public UpState(long mid) {
        this.mid = mid;
    }

    /**
     *
     * @param mid 用户的唯一标识
     * @param cookie
     */
    public UpState(long mid, Cookie cookie) {
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

    public UpStatJsonRootBean getPojo(long mid) throws HttpException {
        String page = null;
        try {
            page = getPage(mid);
        } catch (Exception e) {
            throw new HttpException("访问URL失败",e);
        }
        if (page == null) return null;
        UpStatJsonRootBean bean = JSONObject.parseObject(page, UpStatJsonRootBean.class);
        return bean;
    }

    public UpStatJsonRootBean getPojo() throws HttpException {
        String page = null;
        try {
            page = getPage(mid);
        } catch (Exception e) {
            throw new HttpException("访问URL失败",e);
        }
        if (page == null) return null;
        UpStatJsonRootBean bean = JSONObject.parseObject(page, UpStatJsonRootBean.class);
        return bean;
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
