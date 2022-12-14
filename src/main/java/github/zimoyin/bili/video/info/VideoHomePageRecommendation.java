package github.zimoyin.bili.video.info;

import com.alibaba.fastjson.JSONObject;
import github.zimoyin.bili.cookie.Cookie;
import github.zimoyin.bili.utils.net.httpclient.HttpClientResult;
import github.zimoyin.bili.utils.net.httpclient.HttpClientUtils;
import github.zimoyin.bili.video.info.pojo.recommendation.VideoHomeRecommendationRootBean;

import java.io.IOException;
import java.util.HashMap;

/**
 * 视频首页视频推荐
 */
public class VideoHomePageRecommendation {
    private static final String URL ="https://api.bilibili.com/x/web-interface/index/top/feed/rcmd";
    private Cookie cookie;

    public VideoHomePageRecommendation(Cookie cookie) {
        this.cookie = cookie;
    }

    public VideoHomePageRecommendation() {
    }

    /**
     * 推荐视频
     * @return
     * @throws IOException
     */
    public VideoHomeRecommendationRootBean getJsonPojo() throws IOException {
        String page = getPage(10);
        VideoHomeRecommendationRootBean json = JSONObject.parseObject(page, VideoHomeRecommendationRootBean.class);
        return json;
    }

    /**
     * 推荐视频
     * @param ps 个数
     * @return
     * @throws IOException
     */
    public VideoHomeRecommendationRootBean getJsonPojo(int ps) throws IOException {
        String page = getPage(ps);
        VideoHomeRecommendationRootBean json = JSONObject.parseObject(page, VideoHomeRecommendationRootBean.class);
        return json;
    }

    /**
     * 推荐视频
     * @param ps 个数
     * @return
     * @throws IOException
     */
    public String getPage(int ps) throws IOException {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("ps", String.valueOf(ps));

        HttpClientResult httpClientResult = HttpClientUtils.doGet(URL,cookie != null ? cookie.toHeaderCookie() : null, params);
        return httpClientResult.getContent();
    }
}
