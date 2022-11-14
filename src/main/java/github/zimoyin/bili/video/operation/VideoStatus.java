package github.zimoyin.bili.video.operation;

import com.alibaba.fastjson.JSONObject;
import github.zimoyin.bili.cookie.Cookie;
import github.zimoyin.bili.utils.net.httpclient.HttpClientResult;
import github.zimoyin.bili.utils.net.httpclient.HttpClientUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

/**
 * 判断是否收藏、分享、投币等
 * @API: https://github.com/SocialSisterYi/bilibili-API-collect
 */
public class VideoStatus {
    private final String URL_like = "http://api.bilibili.com/x/web-interface/archive/has/like?bvid=%s";
    private final String URL_coin = "http://api.bilibili.com/x/web-interface/archive/coins?bvid=%s";
    private final String URL_star = "http://api.bilibili.com/x/v2/fav/video/favoured?aid=%s";

    HashMap<String, String> header = new HashMap<>();
    private Cookie cookie;

    public VideoStatus(Cookie cookie) {
        this.cookie = cookie;
        cookie.setCookieToHeader(header);
        header.put("referer", "https://www.bilibili.com");
    }

    /**
     * 是否点赞
     *
     * @return
     */
    public boolean isLike(String bv) throws IOException, NoSuchAlgorithmException, KeyStoreException, URISyntaxException, KeyManagementException {
        String result = isLikePage(bv);
        JSONObject jsonObject = JSONObject.parseObject(result);
        int code = jsonObject.getInteger("code");
        if (code == 0) {
            int data = jsonObject.getInteger("data");

            return data == 1;
        }
        return false;
    }

    /**
     * 是否点赞
     *
     * @return
     */
    public String isLikePage(String bv) throws IOException, NoSuchAlgorithmException, KeyStoreException, URISyntaxException, KeyManagementException {
        String format = String.format(URL_like, bv);
        HttpClientResult result = HttpClientUtils.doGet(format, header, null);

        return result.getContent();
    }



    /**
     * 是否投币
     *
     * @return
     */
    public boolean isCoin(String bv) throws IOException, NoSuchAlgorithmException, KeyStoreException, URISyntaxException, KeyManagementException {
        String result = isCoinPage(bv);
        JSONObject jsonObject = JSONObject.parseObject(result);
        int code = jsonObject.getInteger("code");
        if (code == 0) {
            int multiply = jsonObject.getJSONObject("data").getInteger("multiply");

            return multiply >= 1;
        }
        return false;
    }

    /**
     * 是否投币
     *
     * @return
     */
    public String isCoinPage(String bv) throws IOException, NoSuchAlgorithmException, KeyStoreException, URISyntaxException, KeyManagementException {
        String format = String.format(URL_coin, bv);
        HttpClientResult result = HttpClientUtils.doGet(format, header, null);

        return result.getContent();
    }




    /**
     * 是否收藏
     *
     * @return
     */
    public boolean isStar(String bv) throws IOException, NoSuchAlgorithmException, KeyStoreException, URISyntaxException, KeyManagementException {
        String result = isStarPage(bv);
        JSONObject jsonObject = JSONObject.parseObject(result);
        int code = jsonObject.getInteger("code");
        if (code == 0) {
            Boolean share = jsonObject.getJSONObject("data").getBoolean("favoured");

            return share;
        }
        return false;
    }

    /**
     * 是否收藏
     *
     * @return
     */
    public String isStarPage(String bv) throws IOException, NoSuchAlgorithmException, KeyStoreException, URISyntaxException, KeyManagementException {
        String format = String.format(URL_star, bv);
        HttpClientResult result = HttpClientUtils.doGet(format, header, null);

        return result.getContent();
    }
}
