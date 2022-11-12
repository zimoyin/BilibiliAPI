package github.zimoyin.core.video.url;

import com.alibaba.fastjson.JSONObject;
import github.zimoyin.core.cookie.Cookie;
import github.zimoyin.core.exception.CodeException;
import github.zimoyin.core.exception.URLFormatException;
import github.zimoyin.core.utils.net.httpclient.HttpClientResult;
import github.zimoyin.core.utils.net.httpclient.HttpClientUtils;
import github.zimoyin.core.video.download.VideoDownloadSetting;
import github.zimoyin.core.video.url.data.Fnval;
import github.zimoyin.core.video.url.data.ID;
import github.zimoyin.core.video.url.data.QN;
import github.zimoyin.core.video.url.pojo.VideoURLJsonRoot;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;


/**
 * 视频流（源）信息，可以获取视频的URL，但是不能直接下载，因为下载视频需要防盗链("referer"为"https://www.bilibili.com")并且部分类型视频需要cookie才能下载
 * @API: https://github.com/SocialSisterYi/bilibili-API-collect
 */
@Deprecated
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
     * 待会要访问 WEB_URL 链接的参数
     */
    private static HashMap<String, String> val = new HashMap<>();

    /**
     * 获取访问URL的实体类
     * @return JSON实体类
     */
    public VideoURLJsonRoot getJsonPOJO(VideoDownloadSetting setting) throws IOException, CodeException, URLFormatException, NoSuchAlgorithmException, KeyStoreException, URISyntaxException, KeyManagementException {
        setting.build();
        return getJsonPOJO(setting.getId(),setting.getQn(),setting.getFnval());
    }




    /**
     * 获取访问URL的实体类
     *
     * @param bv    视频的ID，值请在ID中获取
     * @param cid   视频的CID 对于多p视频，默认的cid只能获取到第一p的视频
     * @param qn    视频清晰度，值请在QN中获取
     * @param fnval 视频格式，值请在Fnval中获取
     * @return JSON实体类
     * @link: github.zimoyin.core.video.url.data.ID
     * @link: github.zimoyin.core.video.url.data.QN
     * @link: github.zimoyin.core.video.url.data.Fnval
     */
    public VideoURLJsonRoot getJsonPOJO(String bv,String cid, int qn, int fnval) throws IOException, CodeException, URLFormatException, NoSuchAlgorithmException, KeyStoreException, URISyntaxException, KeyManagementException {
        ID id = new ID(bv);
        id.setCid(cid);
        return getJsonPOJO(id,new QN(qn),new Fnval(fnval));
    }


    /**
     * 获取访问URL的实体类
     *
     * @param bv    视频的ID，值请在ID中获取
     * @param qn    视频清晰度，值请在QN中获取
     * @param fnval 视频格式，值请在Fnval中获取
     * @return JSON实体类
     * @link: github.zimoyin.core.video.url.data.ID
     * @link: github.zimoyin.core.video.url.data.QN
     * @link: github.zimoyin.core.video.url.data.Fnval
     */
    public VideoURLJsonRoot getJsonPOJO(String bv, int qn, int fnval) throws IOException, CodeException, URLFormatException, NoSuchAlgorithmException, KeyStoreException, URISyntaxException, KeyManagementException {
        return getJsonPOJO(new ID(bv),new QN(qn),new Fnval(fnval));
    }


    /**
     * 获取访问URL的实体类
     *
     * @Params: id – 视频的ID qn – 视频清晰度 fnval – 视频格式
     * @Returns: JSON实体类
     * @Throws:
     * @Exception
     * @eg: HttpClientResult bv1ja411X7xW = sa.getPage(new ID("BV1ja411X7xW"), new QN(VideoURLFormat.QN.P306), new Fnval(VideoURLFormat.Fnval.VideoFormat_dash));
     * @See: github.zimoyin.core.video.url.data.ID
     * @See: github.zimoyin.core.video.url.data.QN
     * @See: github.zimoyin.core.video.url.data.Fnval
     */
    public VideoURLJsonRoot getJsonPOJO(ID id, QN qn, Fnval fnval) throws IOException, CodeException, URLFormatException, NoSuchAlgorithmException, KeyStoreException, URISyntaxException, KeyManagementException {
        //获取URL信息 JSON POJO
        HttpClientResult page = getPage(id, qn, fnval);
        VideoURLJsonRoot videoURLJson = JSONObject.parseObject(page.getContent(), VideoURLJsonRoot.class);
        //赋值基本信息
        videoURLJson.setFnval(fnval.getVideoFormat());
        videoURLJson.setUri(getURI());
        videoURLJson.setBv(id.getBv());
        return videoURLJson;
    }


    /**
     * 获取原始信息:不推荐使用。建议使用getJsonPOJO方法
     *
     * @param bv    视频的ID，值请在ID中获取
     * @param qn    视频清晰度，值请在QN中获取
     * @param fnval 视频格式，值请在Fnval中获取
     * @return 原始JSON
     * @throws Exception
     * @link: github.zimoyin.core.video.url.data.ID
     * @link: github.zimoyin.core.video.url.data.QN
     * @link: github.zimoyin.core.video.url.data.Fnval
     */
    public HttpClientResult getPage(String bv, int qn, int fnval) throws CodeException, URLFormatException, IOException, NoSuchAlgorithmException, KeyStoreException, URISyntaxException, KeyManagementException {
        return getPage(new ID(bv), new QN(qn), new Fnval(fnval));
    }


    /**
     * 获取原始信息:不推荐使用。建议使用getJsonPOJO方法
     *
     * @param bv    视频的ID，值请在ID中获取
     * @param cid   视频的CID 对于多p视频，默认的cid只能获取到第一p的视频
     * @param qn    视频清晰度，值请在QN中获取
     * @param fnval 视频格式，值请在Fnval中获取
     * @return 原始JSON
     * @throws Exception
     * @link: github.zimoyin.core.video.url.data.ID
     * @link: github.zimoyin.core.video.url.data.QN
     * @link: github.zimoyin.core.video.url.data.Fnval
     */
    public HttpClientResult getPage(String bv, String cid,int qn, int fnval) throws CodeException, URLFormatException, IOException, NoSuchAlgorithmException, KeyStoreException, URISyntaxException, KeyManagementException {
        ID id = new ID(bv);
        id.setCid(cid);
        return getPage(id, new QN(qn), new Fnval(fnval));
    }

    /**
     * 获取原始信息:不推荐使用。建议使用getJsonPOJO方法
     *
     * @param id    视频的ID
     * @param qn    视频清晰度
     * @param fnval 视频格式
     * @return 原始JSON
     * @throws Exception
     * @eg: HttpClientResult bv1ja411X7xW = sa.getPage(new VideoURLFormat.ID("BV1ja411X7xW"), new VideoURLFormat.QN(VideoURLFormat.QN.P306), new VideoURLFormat.Fnval(VideoURLFormat.Fnval.VideoFormat_dash));
     * @See: github.zimoyin.core.video.url.VideoURLFormat.ID
     * @See: github.zimoyin.core.video.url.VideoURLFormat.QN
     * @See: github.zimoyin.core.video.url.VideoURLFormat.Fnval
     */
    public HttpClientResult getPage(ID id, QN qn, Fnval fnval) throws IOException, NoSuchAlgorithmException, KeyStoreException, URISyntaxException, KeyManagementException, CodeException, URLFormatException {
        val.clear();
        id.getVal(val);
        qn.getVal(val);
        fnval.getVal(val);
        HttpClientResult httpClientResult = HttpClientUtils.doGet(WEB_URL, header, val);
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




