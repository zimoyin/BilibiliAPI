package github.zimoyin.bili.video.info;


import com.alibaba.fastjson.JSONObject;
import github.zimoyin.bili.utils.net.httpclient.HttpClientUtils;
import github.zimoyin.bili.video.info.pojo.recommendation.VideoRecommendationRootBean;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

/**
 * 视频推荐
 * @API: https://github.com/SocialSisterYi/bilibili-API-collect
 */
public class VideoRecommendation {
    private final String URL="http://api.bilibili.com/x/web-interface/archive/related?bvid=%s";


    /**
     * 推荐同类型视频
     * @param bv
     * @return
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws KeyStoreException
     * @throws URISyntaxException
     * @throws KeyManagementException
     */
    public String getPage(String bv) throws IOException, NoSuchAlgorithmException, KeyStoreException, URISyntaxException, KeyManagementException {
        return HttpClientUtils.doGet(String.format(URL,bv)).getContent();
    }


    /**
     * 推荐同类型视频
     * @param bv
     * @return
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws KeyStoreException
     * @throws URISyntaxException
     * @throws KeyManagementException
     */
    public VideoRecommendationRootBean getJsonPojo(String bv) throws IOException, NoSuchAlgorithmException, KeyStoreException, URISyntaxException, KeyManagementException {
        return JSONObject.parseObject(getPage(bv), VideoRecommendationRootBean.class);
    }
}
