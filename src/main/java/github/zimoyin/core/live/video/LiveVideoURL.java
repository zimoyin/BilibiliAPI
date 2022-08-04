package github.zimoyin.core.live.video;

import com.alibaba.fastjson.JSONObject;
import github.zimoyin.core.live.pojo.area.AreaListJsonRoot;
import github.zimoyin.core.live.pojo.url.LiveVideoURLJsonRoot;
import github.zimoyin.core.live.video.data.Quality;
import github.zimoyin.core.utils.net.httpclient.HttpClientResult;
import github.zimoyin.core.utils.net.httpclient.HttpClientUtils;
import lombok.Data;
import org.apache.http.HttpException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

/**
 * 获取直播视频流，该流会一直持续到直播结束 (可以把流扔到 player 播放)
 */
@Data
public class LiveVideoURL {
    /**
     * 代码写死在这里了，如果你想更改,请手动getURL后修改完成之后set进来（但是不推荐）
     * platform 直播流格式(默认为http-flv方式)：
     * 参数可为=>  h5：hls 方式(m3u8)
     * 参数可为=>  web：http-flv方式(flv)， 流一直持续到直播结束
     */
    private  String URL = "http://api.live.bilibili.com/room/v1/Room/playUrl?platform=web&cid=%s&quality=%s";


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
        String url = String.format(URL, roomID, qn);
        System.out.println(url);
        HttpClientResult result = HttpClientUtils.doGet(url);
        return result.getContent();
    }
}
