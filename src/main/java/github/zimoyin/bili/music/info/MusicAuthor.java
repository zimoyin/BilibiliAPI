package github.zimoyin.bili.music.info;

import com.alibaba.fastjson.JSONObject;
import github.zimoyin.bili.cookie.Cookie;
import github.zimoyin.bili.music.info.pojo.author.MusicAuthorJsonRoot;
import github.zimoyin.bili.utils.net.httpclient.HttpClientResult;
import github.zimoyin.bili.utils.net.httpclient.HttpClientUtils;

import java.io.IOException;
import java.util.HashMap;

/**
 * 查询歌曲创作成员列表
 */
public class MusicAuthor {
    private static final String URL = "http://www.bilibili.com/audio/music-service-c/web/member/song";
    private Cookie cookie;

    public MusicAuthor() {
    }

    public MusicAuthor(Cookie cookie) {
        this.cookie = cookie;
    }

    /**
     * 查询歌曲创作成员列表
     * @param sid 音频auid
     * @return
     * @throws IOException
     */
    public MusicAuthorJsonRoot getJsonPojo(long sid) throws IOException {
        String page = getPage(sid);
        MusicAuthorJsonRoot root = JSONObject.parseObject(page, MusicAuthorJsonRoot.class);
        return root;
    }

    /**
     * 查询歌曲创作成员列表
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
