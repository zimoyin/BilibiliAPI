package github.zimoyin.bili.video.info;

import com.alibaba.fastjson.JSONObject;
import github.zimoyin.bili.cookie.Cookie;
import github.zimoyin.bili.utils.IDConvert;
import github.zimoyin.bili.utils.net.httpclient.HttpClientResult;
import github.zimoyin.bili.utils.net.httpclient.HttpClientUtils;
import github.zimoyin.bili.video.info.pojo.tag.VideoTagRootBean;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

/**
 * 视频标签
 * @API: https://github.com/SocialSisterYi/bilibili-API-collect
 */
public class VideoTAG {
    private final String URL="http://api.bilibili.com/x/tag/archive/tags?bvid=%s";
    private final String URL_like="http://api.bilibili.com/x/tag/archive/like2";
    private final String URL_dislike="http://api.bilibili.com/x/tag/archive/hate2";

    private Cookie cookie;
    private HashMap<String,String> header = new HashMap<>();
    public VideoTAG() {
    }

    public VideoTAG(Cookie cookie) {
        this.cookie = cookie;
        cookie.setCookieToHeader(header);
    }

    public Cookie getCookie() {
        return cookie;
    }

    public void setCookie(Cookie cookie) {
        this.cookie = cookie;
        cookie.setCookieToHeader(header);
    }


    /**
     * 获取视频的tag信息，以pojo对象返回
     * @param bv
     * @return
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws KeyStoreException
     * @throws URISyntaxException
     * @throws KeyManagementException
     */
    public VideoTagRootBean videoTagJsonPojo(String bv) throws IOException, NoSuchAlgorithmException, KeyStoreException, URISyntaxException, KeyManagementException {
        String result = videoTagPage(bv);
        VideoTagRootBean videoTagRootBean = JSONObject.parseObject(result, VideoTagRootBean.class);
        return videoTagRootBean;
    }

    /**
     * 获取视频的tag信息，返回原始信息
     * @param bv
     * @return
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws KeyStoreException
     * @throws URISyntaxException
     * @throws KeyManagementException
     */
    public String videoTagPage(String bv) throws IOException, NoSuchAlgorithmException, KeyStoreException, URISyntaxException, KeyManagementException {
        HttpClientResult result = HttpClientUtils.doGet(String.format(URL,bv), header, null);
        return result.getContent();
    }


    /**
     * 对TAG 点赞/取消点赞（重复操作为取消）
     * @param bv
     * @param tagId tag 的ID
     * @return
     * @throws IOException
     */
    public boolean like(String bv,String tagId) throws IOException {
        String result = likePage(bv, tagId);
        int code = JSONObject.parseObject(result).getInteger("code");
        return code == 0;
    }

    /**
     * 对TAG 点赞/取消点赞（重复操作为取消）
     * @param bv
     * @param tagId tag 的ID
     * @return
     * @throws IOException
     */
    public String likePage(String bv,String tagId) throws IOException {
        HashMap<String,String> parma = new HashMap<>();
        parma.put("aid", String.valueOf(IDConvert.BvToAvNumber(bv)));
        parma.put("tag_id", tagId);
        parma.put("csrf", cookie.getCsrf());
        HttpClientResult result = HttpClientUtils.doPost(URL_like, header, parma, null);
        return result.getContent();
    }




    /**
     * 对TAG 点踩/取消点踩（重复操作为取消）
     * @param bv
     * @param tagId tag 的ID
     * @return
     * @throws IOException
     */
    public boolean dislike(String bv,String tagId) throws IOException {
        String result = dislikePage(bv, tagId);
        int code = JSONObject.parseObject(result).getInteger("code");
        return code == 0;
    }

    /**
     * 对TAG 点踩/取消点踩（重复操作为取消）
     * @param bv
     * @param tagId tag 的ID
     * @return
     * @throws IOException
     */
    public String dislikePage(String bv,String tagId) throws IOException {
        HashMap<String,String> parma = new HashMap<>();
        parma.put("aid", String.valueOf(IDConvert.BvToAvNumber(bv)));
        parma.put("tag_id", tagId);
        parma.put("csrf", cookie.getCsrf());
        HttpClientResult result = HttpClientUtils.doPost(URL_dislike, header, parma, null);
        return result.getContent();
    }
}
