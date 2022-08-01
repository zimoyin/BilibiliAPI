package github.zimoyin.core.video.url;

import com.alibaba.fastjson.JSONObject;
import github.zimoyin.core.exception.CodeException;
import github.zimoyin.core.exception.URLFormatException;
import github.zimoyin.core.utils.net.httpclient.HttpClientResult;
import github.zimoyin.core.utils.net.httpclient.HttpClientUtils;
import github.zimoyin.core.video.url.data.ID;
import github.zimoyin.core.video.url.pojo.VideoURLJsonRoot;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;


/**
 * 固定360p视频 mp4格式，没有防盗链
 * @API: zimo
 */
public class VideoURLPreviewFormatP360 {

    private static final String WEB_URL_preview = "https://api.bilibili.com/x/player/playurl?platform=html5&otype=json&qn=80&type=mp4&html5=1";
    private java.net.URI URI;

    /**
     * 待会要访问 WEB_URL 链接的参数
     */
    private static HashMap<String, String> val = new HashMap<>();



    /**
     * 请求头
     */
    private HashMap<String, String> header = new HashMap<>();

    public VideoURLPreviewFormatP360() {
    }


    /**
     * 获取原始信息
     *
     * @param id    视频的ID
     * @return 原始JSON
     * @throws Exception
     * @eg: HttpClientResult bv1ja411X7xW = sa.getPage(new VideoURLFormat.ID("BV1ja411X7xW"), new VideoURLFormat.QN(VideoURLFormat.QN.P306), new VideoURLFormat.Fnval(VideoURLFormat.Fnval.VideoFormat_dash));
     * @See: github.zimoyin.core.video.url.VideoURLFormat.ID
     */
    public HttpClientResult getPage(ID id) throws IOException, NoSuchAlgorithmException, KeyStoreException, URISyntaxException, KeyManagementException, URLFormatException, CodeException {
        id.getVal(val);
        HttpClientResult httpClientResult = HttpClientUtils.doGet(WEB_URL_preview, val);
        this.URI = httpClientResult.getURI();
        return httpClientResult;
    }


    /**
     * 获取访问URL的实体类
     *
     * @param bv    视频的ID，值请在ID中获取
     * @return JSON实体类
     * @link: github.zimoyin.core.video.url.data.ID
     */
    public VideoURLJsonRoot getJsonPOJO(String bv) throws URLFormatException, IOException, NoSuchAlgorithmException, KeyStoreException, URISyntaxException, KeyManagementException, CodeException {
        HttpClientResult page = getPage(new ID(bv));
        VideoURLJsonRoot videoURLJson = JSONObject.parseObject(page.getContent(), VideoURLJsonRoot.class);
        return videoURLJson;
    }


    /**
     * 返回视频的下载链接
     * @param bv
     * @return
     * @throws Exception
     */
    public ArrayList<URL> getURLs(String bv) throws URLFormatException, IOException, NoSuchAlgorithmException, KeyStoreException, URISyntaxException, KeyManagementException, CodeException {
        return getJsonPOJO(bv).getURLs();
    }


    /**
     * 返回该类实际访问的URI，该URI不是视频的下载地址，而是视频下载信息集合的访问URI。URI由web_url与用户传入的val构建而成
     * @return
     */
    public java.net.URI getURI() {
        return URI;
    }
}




