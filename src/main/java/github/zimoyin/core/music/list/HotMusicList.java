package github.zimoyin.core.music.list;

import com.alibaba.fastjson.JSONObject;
import github.zimoyin.core.Incompleteness;
import github.zimoyin.core.cookie.Cookie;
import github.zimoyin.core.music.list.pojo.list.hot.HotMusicJsonRoot;
import github.zimoyin.core.music.list.pojo.list.my.MyMusicListDefJsonRootBean;
import github.zimoyin.core.utils.net.httpclient.HttpClientResult;
import github.zimoyin.core.utils.net.httpclient.HttpClientUtils;

import java.io.IOException;
import java.util.HashMap;

/**
 * 查询热门歌单
 */
@Incompleteness
public class HotMusicList {
    private static final String URL = "http://www.bilibili.com/audio/music-service-c/web/menu/hit";


    /**
     * 查询音频收藏夹（默认歌单）信息
     *
     * @param pn 页码
     * @return
     * @throws IOException
     */
    public HotMusicJsonRoot getJsonPojo(int pn) throws IOException {
        String page = getPage(pn, 100);
        HotMusicJsonRoot root = JSONObject.parseObject(page, HotMusicJsonRoot.class);
        return root;
    }

    /**
     * 查询音频收藏夹（默认歌单）信息
     *
     * @param pn 页码
     * @param ps 每页项数
     * @return
     * @throws IOException
     */
    public HotMusicJsonRoot getJsonPojo(int pn,int ps) throws IOException {
        String page = getPage(pn, ps);
        HotMusicJsonRoot root = JSONObject.parseObject(page, HotMusicJsonRoot.class);
        return root;
    }




    /**
     * 查询音频收藏夹（默认歌单）信息
     *
     * @param pn 页码
     * @param ps 每页项数
     * @return
     * @throws IOException
     */
    public String getPage(int pn,int ps) throws IOException {
        HashMap<String, String> params = new HashMap<String, String>();
        if (pn > -1)params.put("pn", String.valueOf(pn));
        else params.put("pn","1");
        if (ps > -1)params.put("ps", String.valueOf(ps));
        else params.put("ps","30");

        HttpClientResult httpClientResult = HttpClientUtils.doGet(URL, params);
        return httpClientResult.getContent();
    }

}
