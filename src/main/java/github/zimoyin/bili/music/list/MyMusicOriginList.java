package github.zimoyin.bili.music.list;

import com.alibaba.fastjson.JSONObject;
import github.zimoyin.bili.Incompleteness;
import github.zimoyin.bili.cookie.Cookie;
import github.zimoyin.bili.music.list.pojo.list.origin.OriginMusicJsonRoot;
import github.zimoyin.bili.utils.net.httpclient.HttpClientResult;
import github.zimoyin.bili.utils.net.httpclient.HttpClientUtils;

import java.io.IOException;
import java.util.HashMap;

/**
 * 查询热门榜单
 */
@Incompleteness
public class MyMusicOriginList {
    private static final String URL = "http://www.bilibili.com/audio/music-service-c/web/menu/rank";
    private Cookie cookie;

    public MyMusicOriginList(Cookie cookie) {
        this.cookie = cookie;
//        this.cookie.put("DedeUserID","1");
    }

    /**
     * 查询热门榜单
     * @param pn 音频auid
     * @return
     * @throws IOException
     */
    public OriginMusicJsonRoot getJsonPojo(int pn) throws IOException {
        String page = getPage(pn);
        OriginMusicJsonRoot root = JSONObject.parseObject(page, OriginMusicJsonRoot.class);
        return root;
    }

    /**
     * 查询热门榜单
     * @param pn 当前页数
     * @return
     * @throws IOException
     */
    public String getPage(int pn) throws IOException {
        HashMap<String,String> params = new HashMap<String,String>();
        params.put("pn", String.valueOf(pn));
        params.put("ps", String.valueOf(50));

        HttpClientResult httpClientResult = HttpClientUtils.doGet(URL, cookie != null ? cookie.toHeaderCookie() : null, params);
        return httpClientResult.getContent();
    }

    /**
     * 查询热门榜单
     * @return
     * @throws IOException
     */
    public String getPage() throws IOException {
        return getPage(1);
    }
}
