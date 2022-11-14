package github.zimoyin.bili.music.info;

import com.alibaba.fastjson.JSONObject;
import github.zimoyin.bili.cookie.Cookie;
import github.zimoyin.bili.music.info.pojo.tag.MusicTagJsonRoot;
import github.zimoyin.bili.utils.net.httpclient.HttpClientResult;
import github.zimoyin.bili.utils.net.httpclient.HttpClientUtils;

import java.io.IOException;
import java.util.HashMap;

/**
 * 查询歌曲TAG
 */
public class MusicTag {
    private static final  String URL ="http://www.bilibili.com/audio/music-service-c/web/tag/song";
    private Cookie cookie;

    public MusicTag() {
    }

    public MusicTag(Cookie cookie) {
        this.cookie = cookie;
    }
    /**
     * 查询歌曲TAG
     * @param sid 音频auid
     * @return
     * @throws IOException
     */
    public MusicTagJsonRoot getJsonPojo(long sid) throws IOException {
        String page = getPage(sid);
        MusicTagJsonRoot root = JSONObject.parseObject(page, MusicTagJsonRoot.class);
        return root;
    }

    /**
     * 查询歌曲TAG
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
