package github.zimoyin.core.music.list;

import com.alibaba.fastjson.JSONObject;
import github.zimoyin.core.cookie.Cookie;
import github.zimoyin.core.music.list.pojo.list.my.MyMusicListDefJsonRootBean;
import github.zimoyin.core.music.list.pojo.list.my.MyMusicListJsonRootBean;
import github.zimoyin.core.utils.net.httpclient.HttpClientResult;
import github.zimoyin.core.utils.net.httpclient.HttpClientUtils;

import java.io.IOException;
import java.util.HashMap;

/**
 * 查询音频收藏夹（默认歌单）信息
 */
public class MyMusicListDef {
    private static final String URL = "http://www.bilibili.com/audio/music-service-c/web/collections/info";
    private Cookie cookie;

    public MyMusicListDef(Cookie cookie) {
        this.cookie = cookie;
//        this.cookie.put("DedeUserID","1");
    }

    /**
     * 查询音频收藏夹（默认歌单）信息
     *
     * @param sid 音频收藏夹mlid:	必须为默认收藏夹mlid
     * @return
     * @throws IOException
     */
    public MyMusicListDefJsonRootBean getJsonPojo(long sid) throws IOException {
        String page = getPage(sid);
        MyMusicListDefJsonRootBean root = JSONObject.parseObject(page, MyMusicListDefJsonRootBean.class);
        return root;
    }

    /**
     * 查询音频收藏夹（默认歌单）信息
     *
     * @param sid 音频收藏夹mlid:	必须为默认收藏夹mlid
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
