package github.zimoyin.bili.music.info;

import com.alibaba.fastjson.JSONObject;
import github.zimoyin.bili.cookie.Cookie;
import github.zimoyin.bili.music.info.pojo.info.MusicInfoJsonRoot;
import github.zimoyin.bili.utils.net.httpclient.HttpClientResult;
import github.zimoyin.bili.utils.net.httpclient.HttpClientUtils;

import java.io.IOException;
import java.util.HashMap;


/**
 * 查询歌曲基本信息
 */
public class MusicInfo {
    private static final  String URL ="http://www.bilibili.com/audio/music-service-c/web/song/info";
    private Cookie cookie;

    public MusicInfo() {
    }

    public MusicInfo(Cookie cookie) {
        this.cookie = cookie;
    }
    /**
     * 查询歌曲基本信息
     * @param sid 音频auid
     * @return
     * @throws IOException
     */
    public MusicInfoJsonRoot getJsonPojo(long sid) throws IOException {
        String page = getPage(sid);
        MusicInfoJsonRoot root = JSONObject.parseObject(page, MusicInfoJsonRoot.class);
        return root;
    }

    /**
     * 查询歌曲基本信息
     * @param sid 音频auid
     * @return
     * @throws IOException
     */
    public String getPage(long sid) throws IOException {
        HashMap<String,String> params = new HashMap<String,String>();
        params.put("sid", String.valueOf(sid));

        HttpClientResult httpClientResult = HttpClientUtils.doGet(URL, cookie != null ? cookie.toHeaderCookie() : null, params);
        return httpClientResult.getContent();
    }
}
