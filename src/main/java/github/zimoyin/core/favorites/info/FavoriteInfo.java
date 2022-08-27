package github.zimoyin.core.favorites.info;

import com.alibaba.fastjson.JSONObject;
import github.zimoyin.core.cookie.Cookie;
import github.zimoyin.core.favorites.pojo.info.FavoriteInfoJsonRoot;
import github.zimoyin.core.utils.net.httpclient.HttpClientResult;
import github.zimoyin.core.utils.net.httpclient.HttpClientUtils;

import java.io.IOException;
import java.util.HashMap;

/**
 * 获取收藏夹（元数据）信息
 */
public class FavoriteInfo {
    private static final String URL ="http://api.bilibili.com/x/v3/fav/folder/info";
    private Cookie cookie;

    public FavoriteInfo() {
    }

    public FavoriteInfo(Cookie cookie) {
        this.cookie = cookie;
    }

    /**
     * 获取收藏夹元数据信息
     * @param media_id 目标收藏夹id（完整id）
     */
    public FavoriteInfoJsonRoot getJsonPojo(long media_id) throws IOException {
        String page = getPage(media_id);
        FavoriteInfoJsonRoot favoriteInfoJsonRoot = JSONObject.parseObject(page, FavoriteInfoJsonRoot.class);
        return favoriteInfoJsonRoot;
    }

    /**
     * 获取收藏夹元数据信息
     * @param media_id 目标收藏夹id（完整id）
     */
    public String getPage(long media_id) throws IOException {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("media_id", String.valueOf(media_id));

        HttpClientResult httpClientResult = HttpClientUtils.doGet(URL, cookie != null ? cookie.toHeaderCookie() : null, params);
        return httpClientResult.getContent();
    }
}
