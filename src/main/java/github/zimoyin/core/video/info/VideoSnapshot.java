package github.zimoyin.core.video.info;

import com.alibaba.fastjson.JSONObject;
import github.zimoyin.core.utils.net.httpclient.HttpClientUtils;
import github.zimoyin.core.video.info.pojo.snapshot.SnapshotRootBean;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;


/**
 * 获取视频快照，获取到的是一个图片拼图，需要按照服务器给出的参数进行裁剪（这里就不提供了）
 * @API: https://github.com/SocialSisterYi/bilibili-API-collect
 */
public class VideoSnapshot {
    private final String URL = "https://api.bilibili.com/x/player/videoshot?bvid=BV1RT411J72d%s&index=1";


    public String getPage(String bv) throws IOException, NoSuchAlgorithmException, KeyStoreException, URISyntaxException, KeyManagementException {
        return HttpClientUtils.doGet(String.format(URL,bv)).getContent();
    }

    public SnapshotRootBean getJsonPojo(String bv) throws IOException, NoSuchAlgorithmException, KeyStoreException, URISyntaxException, KeyManagementException {
        String page = getPage(bv);
        return JSONObject.parseObject(page, SnapshotRootBean.class);
    }
}
