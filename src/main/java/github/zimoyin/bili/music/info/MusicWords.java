package github.zimoyin.bili.music.info;

import com.alibaba.fastjson.JSONObject;
import github.zimoyin.bili.cookie.Cookie;
import github.zimoyin.bili.pojo.Code;
import github.zimoyin.bili.utils.JsonSerializeUtil;
import github.zimoyin.bili.utils.net.httpclient.HttpClientResult;
import github.zimoyin.bili.utils.net.httpclient.HttpClientUtils;

import java.io.IOException;
import java.util.HashMap;

/**
 * 获取音乐的歌词
 */
public class MusicWords {
    private static final String URL = "http://www.bilibili.com/audio/music-service-c/web/song/lyric";
    private Cookie cookie;

    public MusicWords() {
    }

    public MusicWords(Cookie cookie) {
        this.cookie = cookie;
    }

    /**
     * 获取音乐的歌词
     *
     * @param sid 音频auid
     * @return
     * @throws IOException
     */
    public String getWords(long sid) throws IOException {
        String page = getPage(sid);
        if (JSONObject.parseObject(page, Code.class).getCode() != 0) return null;
        return JsonSerializeUtil.getJsonPath().read(page, "data");
    }

    /**
     * 获取音乐的歌词
     *
     * @param sid 音频auid
     * @return
     * @throws IOException
     */
    public String getPage(long sid) throws IOException {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("sid", String.valueOf(sid));

        HttpClientResult httpClientResult = HttpClientUtils.doGet(URL, cookie != null ? cookie.toHeaderCookie() : null, params);
        return httpClientResult.getContent();
    }
}
