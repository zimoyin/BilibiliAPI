package github.zimoyin.bili.live.video;

import com.alibaba.fastjson.JSONObject;
import github.zimoyin.bili.live.pojo.url.LiveVideoURLJsonRoot;
import github.zimoyin.bili.live.video.data.Quality;
import github.zimoyin.bili.utils.net.httpclient.HttpClientResult;
import github.zimoyin.bili.utils.net.httpclient.HttpClientUtils;
import org.apache.http.HttpException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

/**
 * 获取直播视频流，该流会一直持续到直播结束 (可以把流扔到 player 播放)
 */
public class LiveVideoURL {
    /**
     * 代码写死在这里了，如果你想更改,请手动getURL后修改完成之后set进来（但是不推荐）
     * platform 直播流格式(默认为http-flv方式)：
     * 参数可为=>  h5：hls 方式(m3u8)
     * 参数可为=>  web：http-flv方式(flv)， 流一直持续到直播结束
     */
    public static final String URL_h5 = "http://api.live.bilibili.com/room/v1/Room/playUrl?platform=h5&cid=%s&quality=%s";
    public static final String URL_hls = "http://api.live.bilibili.com/room/v1/Room/playUrl?platform=hls&cid=%s&quality=%s";
    public static final String URL_web = "http://api.live.bilibili.com/room/v1/Room/playUrl?platform=web&cid=%s&quality=%s";
    public static final String URL_http_flv = "http://api.live.bilibili.com/room/v1/Room/playUrl?platform=http-flv&cid=%s&quality=%s";

    private String url = URL_http_flv;
    private String temURL;

    public LiveVideoURLJsonRoot getPojo(long roomID, Quality quality) throws HttpException {
        try {
            LiveVideoURLJsonRoot jsonRoot = JSONObject.parseObject(getPage(roomID, quality), LiveVideoURLJsonRoot.class);
            return jsonRoot;
        } catch (Exception e) {
            throw new RuntimeException("访问URL失败", e);
        }
    }


    /**
     * 用户对应的直播间状态
     *
     * @return
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws KeyStoreException
     * @throws URISyntaxException
     * @throws KeyManagementException
     */
    public String getPage(long roomID, Quality quality) throws IOException, NoSuchAlgorithmException, KeyStoreException, URISyntaxException, KeyManagementException {
        int qn = 80;
        switch (quality) {
            case ORIGINAL_PAINTING:
                qn = 4;
                break;
            case HIGH_DEFINITION:
                qn = 3;
                break;
            case FLUENT:
                qn = 2;
                break;
        }
        String url = String.format(this.url, roomID, qn);
//        System.out.println(url);
        this.temURL = url;
        HttpClientResult result = HttpClientUtils.doGet(url);
        return result.getContent();
    }


    /**
     * 返回URL
     *
     * @param roomID
     * @param quality
     * @return
     * @throws HttpException
     */
    public ArrayList<java.net.URL> getURL(long roomID, Quality quality) throws HttpException {
        LiveVideoURLJsonRoot pojo = getPojo(roomID, quality);
        if (pojo.getCode() != 0) return null;
        return pojo.getURL();
    }

    /**
     * 设置获取直播地址的URL
     * @param url
     */
    public void setURI(String url) {
        this.url = url;
    }


    /**
     * 设置获取直播地址的URL
     */
    public String getURI(){
        return this.temURL;
    }
}
