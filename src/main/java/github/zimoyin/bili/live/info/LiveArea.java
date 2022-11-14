package github.zimoyin.bili.live.info;

import com.alibaba.fastjson.JSONObject;
import github.zimoyin.bili.live.pojo.area.AreaListJsonRoot;
import github.zimoyin.bili.utils.net.httpclient.HttpClientResult;
import github.zimoyin.bili.utils.net.httpclient.HttpClientUtils;
import org.apache.http.HttpException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

/**
 * 获取所有的直播间分区列表
 */
public class LiveArea {
    //    t
    private static final String URL = "http://api.live.bilibili.com/room/v1/Area/getLis";


    public AreaListJsonRoot getPojo() throws HttpException {
        try {
            AreaListJsonRoot areaListJsonRoot = JSONObject.parseObject(getPage(), AreaListJsonRoot.class);
            return areaListJsonRoot;
        } catch (Exception e) {
            throw new RuntimeException("访问URL失败",e);
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
    public String getPage() throws IOException, NoSuchAlgorithmException, KeyStoreException, URISyntaxException, KeyManagementException {
        HttpClientResult result = HttpClientUtils.doGet(URL);
        return result.getContent();
    }
}
