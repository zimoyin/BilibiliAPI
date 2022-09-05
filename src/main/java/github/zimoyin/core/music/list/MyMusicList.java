package github.zimoyin.core.music.list;

import com.alibaba.fastjson.JSONObject;
import github.zimoyin.core.cookie.Cookie;
import github.zimoyin.core.music.info.pojo.author.MusicAuthorJsonRoot;
import github.zimoyin.core.music.list.pojo.list.my.MyMusicListJsonRootBean;
import github.zimoyin.core.utils.net.httpclient.HttpClientResult;
import github.zimoyin.core.utils.net.httpclient.HttpClientUtils;

import java.io.IOException;
import java.util.HashMap;

/**
 * 查询自己创建的歌单
 */
public class MyMusicList {
    private static final String URL = "http://www.bilibili.com/audio/music-service-c/web/member/song";
    private Cookie cookie;

    public MyMusicList(Cookie cookie) {
        this.cookie = cookie;
//        this.cookie.put("DedeUserID","1");
    }

    /**
     * 查询自己创建的歌单
     * @param pn 音频auid
     * @return
     * @throws IOException
     */
    public MyMusicListJsonRootBean getJsonPojo(int pn) throws IOException {
        String page = getPage(pn);
        MyMusicListJsonRootBean root = JSONObject.parseObject(page, MyMusicListJsonRootBean.class);
        return root;
    }

    /**
     * 查询自己创建的歌单
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
     * 查询自己创建的歌单
     * @return
     * @throws IOException
     */
    public String getPage() throws IOException {
        return getPage(1);
    }
}
