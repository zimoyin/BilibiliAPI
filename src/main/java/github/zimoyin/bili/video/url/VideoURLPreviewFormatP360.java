package github.zimoyin.bili.video.url;

import com.alibaba.fastjson.JSONObject;
import github.zimoyin.bili.exception.CodeException;
import github.zimoyin.bili.utils.IDConvert;
import github.zimoyin.bili.utils.net.httpclient.HttpClientResult;
import github.zimoyin.bili.utils.net.httpclient.HttpClientUtils;
import github.zimoyin.bili.video.url.param.ParamBuilder;
import github.zimoyin.bili.video.url.pojo.VideoURLJsonRoot;

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
     * @return 原始JSON
     * @throws Exception
     * @eg: HttpClientResult bv1ja411X7xW = sa.getPage(new VideoURLFormat.ID("BV1ja411X7xW"), new VideoURLFormat.QN(VideoURLFormat.QN.P306), new VideoURLFormat.Fnval(VideoURLFormat.Fnval.VideoFormat_dash));
     * @See: github.zimoyin.core.video.url.VideoURLFormat.ID
     */
    public HttpClientResult getPage(ParamBuilder param) throws IOException {
        HttpClientResult httpClientResult = HttpClientUtils.doGet(WEB_URL_preview, param.build());
        this.URI = httpClientResult.getURI();
        return httpClientResult;
    }


    /**
     * 获取访问URL的实体类
     *
     * @return JSON实体类
     * @link: github.zimoyin.core.video.url.data.ID
     */
    public VideoURLJsonRoot getJsonPOJO(ParamBuilder param) throws IOException, CodeException {
        HttpClientResult page = getPage(param);
        VideoURLJsonRoot videoURLJson = JSONObject.parseObject(page.getContent(), VideoURLJsonRoot.class);
        return videoURLJson;
    }


    /**
     * 返回视频的下载链接
     * @param bv
     * @return
     * @throws Exception
     */
    public ArrayList<URL> getURLs(String bv) throws IOException, CodeException, NoSuchAlgorithmException, KeyStoreException, URISyntaxException, KeyManagementException {
        long cid = Long.parseLong(IDConvert.BvToCID(bv));
        ParamBuilder builder = new ParamBuilder(bv, cid);
        return getJsonPOJO(builder).getURLs();
    }


    /**
     * 返回该类实际访问的URI，该URI不是视频的下载地址，而是视频下载信息集合的访问URI。URI由web_url与用户传入的val构建而成
     * @return
     */
    public java.net.URI getURI() {
        return URI;
    }
}




