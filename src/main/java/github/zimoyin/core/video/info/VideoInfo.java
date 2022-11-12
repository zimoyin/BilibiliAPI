package github.zimoyin.core.video.info;

import com.alibaba.fastjson.JSONObject;
import github.zimoyin.core.cookie.Cookie;
import github.zimoyin.core.utils.IDConvert;
import github.zimoyin.core.utils.net.httpclient.HttpClientResult;
import github.zimoyin.core.utils.net.httpclient.HttpClientUtils;
import github.zimoyin.core.video.info.pojo.info.WEBVideoINFOJsonRootBean;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;


/**
 * WEB 获取普通视频详细信息，注意互动视频等特殊视频可以获取部分信息，但是要注意有些字段为null
 * GET http://api.bilibili.com/x/web-interface/view?bvid=%s
 * 参数 avid:av号 或
 * 参数 bvid:bv号
 * @API: https://github.com/SocialSisterYi/bilibili-API-collect
 */
public class VideoInfo {
    private static final String URL1 = "http://api.bilibili.com/x/web-interface/view?bvid=%s";//参数bv号
    private static final String URL2 = "http://api.bilibili.com/x/web-interface/view?avid=%s";//参数av号
    private Cookie cookie;
    HashMap<String, String> header = new HashMap<>();

    public VideoInfo() {
    }

    public VideoInfo(Cookie cookie) {
        this.cookie = cookie;
        header = cookie.toHeaderCookie(header);
    }

    /**
     * 获取视频详细详细
     * @param bv
     * @return
     * @throws IOException
     */
    public WEBVideoINFOJsonRootBean getInfo(String bv) throws IOException {
        HttpClientResult httpClientResult = HttpClientUtils.doGet(String.format(URL1,bv), header, new HashMap<>());
        String content = httpClientResult.getContent();
        WEBVideoINFOJsonRootBean webVideoINFOJsonRootBean = JSONObject.parseObject(content, WEBVideoINFOJsonRootBean.class);
        return webVideoINFOJsonRootBean;
    }

    /**
     * 获取视频详细详细
     * @param av
     * @return
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws KeyStoreException
     * @throws URISyntaxException
     * @throws KeyManagementException
     */
    public WEBVideoINFOJsonRootBean getInfo(long av) throws IOException, NoSuchAlgorithmException, KeyStoreException, URISyntaxException, KeyManagementException {
        String bv = IDConvert.AvToBv("AV" + av);
        return getInfo(bv);
    }

    /**
     * 获取视频的摘要详细
     * @param bv
     * @return
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws KeyStoreException
     * @throws URISyntaxException
     * @throws KeyManagementException
     */
    public VideoINFOSummary getSummary(String bv) throws IOException, NoSuchAlgorithmException, KeyStoreException, URISyntaxException, KeyManagementException {
        WEBVideoINFOJsonRootBean info = getInfo(bv);
        return new VideoINFOSummary(info);
    }

    /**
     * 获取视频的摘要详细
     * @param info
     * @return
     */
    public VideoINFOSummary getSummary(WEBVideoINFOJsonRootBean info){
        return new VideoINFOSummary(info);
    }

    public Cookie getCookie() {
        return cookie;
    }

    public void setCookie(Cookie cookie) {
        this.cookie = cookie;
    }


}
