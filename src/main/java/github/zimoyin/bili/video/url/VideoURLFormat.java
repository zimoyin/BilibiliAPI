package github.zimoyin.bili.video.url;

import com.alibaba.fastjson.JSONObject;
import github.zimoyin.bili.cookie.Cookie;
import github.zimoyin.bili.utils.net.httpclient.HttpClientResult;
import github.zimoyin.bili.utils.net.httpclient.HttpClientUtils;
import github.zimoyin.bili.video.url.param.ParamBuilder;
import github.zimoyin.bili.video.url.pojo.VideoURLJsonRoot;

import java.io.IOException;
import java.util.HashMap;


/**
 * 视频流（源）信息，可以获取视频的URL，但是不能直接下载，因为下载视频需要防盗链("referer"为"https://www.bilibili.com")并且部分类型视频需要cookie才能下载
 * @API: https://github.com/SocialSisterYi/bilibili-API-collect
 */
public class VideoURLFormat {
    /**
     * <h2>获取视频流URL（web端</h2>
     *
     * @DATA 2022-07-14
     * <p>获取720P及以上清晰度视频时需要登录（Cookie）</p>
     * <p>获取高帧率（1080P60）/高码率（1080P+）视频时需要有大会员的账号登录（Cookie）</p>
     * <p>获取会员专属视频时需要登录（Cookie）</p>
     * <p>获取的url有效时间为120min，超时失效需要重新获取</p>
     * <p>部分视频会有分段，需要特别注意</p>
     * <p>若视频有分P，仅为单P的视频的url，换P则需更换cid重新获取</p>
     */
    private static final String WEB_URL = "http://api.bilibili.com/x/player/playurl";
    private java.net.URI URI;


    /**
     * 登录的Cookie
     */
    private Cookie cookie;

    /**
     * 请求头
     */
    private HashMap<String, String> header = new HashMap<>();

    public VideoURLFormat() {
    }

    public VideoURLFormat(Cookie cookie) {
        this.cookie = cookie;
        if (cookie == null) throw new IllegalArgumentException("Cookie cannot be null");
        cookie.setCookieToHeader(header);
    }

    public void setCookie(Cookie cookie) {
        this.cookie = cookie;
        cookie.setCookieToHeader(header);
    }

    /**
     * 获取访问URL的实体类
     * @Returns: JSON实体类
     */
    public VideoURLJsonRoot getJsonPOJO(ParamBuilder builder) throws IOException {
        //获取URL信息 JSON POJO
        HttpClientResult page = getPage(builder);
        VideoURLJsonRoot videoURLJson = JSONObject.parseObject(page.getContent(), VideoURLJsonRoot.class);
        //赋值基本信息
        videoURLJson.setFnval(builder.getFnval());
        videoURLJson.setUri(getURI());
        videoURLJson.setBv(builder.getBvid());
        return videoURLJson;
    }


    /**
     * 获取原始信息:不推荐使用。建议使用getJsonPOJO方法
     *
     * @return 原始JSON
     * @throws Exception
     */
    public HttpClientResult getPage(ParamBuilder builder) throws IOException {
        HttpClientResult httpClientResult = HttpClientUtils.doGet(WEB_URL, header, builder.build());
        this.URI = httpClientResult.getURI();
        return httpClientResult;
    }


    /**
     * 返回该类实际访问的URI，该URI不是视频的下载地址，而是视频下载信息集合的访问URI。URI由web_url与用户传入的val构建而成<br>
     * uri只有当执行getPage之后才会获取到uri
     * @return
     */
    public java.net.URI getURI() {
        return URI;
    }
}




