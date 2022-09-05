package github.zimoyin.core.music.info;

import github.zimoyin.core.utils.JsonSerializeUtil;
import github.zimoyin.core.utils.net.httpclient.HttpClientResult;
import github.zimoyin.core.utils.net.httpclient.HttpClientUtils;

import java.io.IOException;
import java.util.HashMap;

/**
 * 音频状态数
 */
public class MusicStatus {
    private static final String URL = "http://www.bilibili.com/audio/music-service-c/web/stat/song";

    /**
     * 获取播放量
     * @param sid
     * @return
     * @throws IOException
     */
    public String getPlayCount(long sid) throws IOException {
        String page = getPage(sid);
        String read = JsonSerializeUtil.getJsonPath().read(page, "data/play");
        return read;
    }
    /**
     * 收藏数
     * @param sid
     * @return
     * @throws IOException
     */
    public String getFavoriteCount(long sid) throws IOException {
        String page = getPage(sid);
        String read = JsonSerializeUtil.getJsonPath().read(page, "data/collect");
        return read;
    }
    /**
     * 评论数
     * @param sid
     * @return
     * @throws IOException
     */
    public String getCommentCount(long sid) throws IOException {
        String page = getPage(sid);
        String read = JsonSerializeUtil.getJsonPath().read(page, "data/comment");
        return read;
    }
    /**
     * 分享数
     * @param sid
     * @return
     * @throws IOException
     */
    public String getShareCount(long sid) throws IOException {
        String page = getPage(sid);
        String read = JsonSerializeUtil.getJsonPath().read(page, "data/share");
        return read;
    }
    /**
     * 音频状态数
     * @param sid 音频auid
     * @return
     * @throws IOException
     */
    public String getPage(long sid) throws IOException {
        HashMap<String,String> params = new HashMap<String,String>();
        params.put("sid", String.valueOf(sid));

        HttpClientResult httpClientResult = HttpClientUtils.doGet(URL, params);
        return httpClientResult.getContent();
    }
}
