package github.zimoyin.core.fanju.info;

import com.alibaba.fastjson.JSONObject;
import github.zimoyin.core.cookie.Cookie;
import github.zimoyin.core.cookie.GlobalCookie;
import github.zimoyin.core.exception.CookieNotFoundException;
import github.zimoyin.core.fanju.pojo.info.essential.FanJuEssentialINFOJsonRootBean;
import github.zimoyin.core.utils.net.httpclient.HttpClientResult;
import github.zimoyin.core.utils.net.httpclient.HttpClientUtils;
import org.apache.http.HttpException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

/**
 * 番剧的基本信息
 * 关于media_id 如何获取，目前请通过 SeriesINFO 获取
 */
public class FanJuEssentialINFO {
    private final String URL = "http://api.bilibili.com/pgc/review/user?media_id=%s";

    private Cookie cookie;
    private HashMap<String, String> headers = new HashMap<String, String>();

    public FanJuEssentialINFO() {
    }

    public void setCookie(Cookie cookie) {
        this.cookie = cookie;
        headers.clear();
        cookie.toHeaderCookie(headers);
    }

    /**
     * 获取剧集的基本信息
     * @param media_id
     * @return
     * @throws HttpException
     */
    public FanJuEssentialINFOJsonRootBean getPojo(long media_id) throws HttpException {
        String page = null;
        try {
            page = getPage(media_id);
        } catch (Exception e) {
            throw new HttpException("访问URL失败", e);
        }
        if (page == null) return null;
        FanJuEssentialINFOJsonRootBean bean = JSONObject.parseObject(page, FanJuEssentialINFOJsonRootBean.class);
        return bean;
    }

    /**
     * 获取用户的信息
     *
     * @param media_id 剧集的media_id
     * @return
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws KeyStoreException
     * @throws URISyntaxException
     * @throws KeyManagementException
     */
    public String getPage(long media_id) throws IOException, NoSuchAlgorithmException, KeyStoreException, URISyntaxException, KeyManagementException {
        String url = String.format(URL, media_id);
        HttpClientResult result = HttpClientUtils.doGet(url, headers, null);
        return result.getContent();
    }

    public Cookie getCookie() {
        return cookie;
    }
}
