package github.zimoyin.core.favorites.info;

import com.alibaba.fastjson.JSONObject;
import github.zimoyin.core.cookie.Cookie;
import github.zimoyin.core.favorites.pojo.conter.FavoriteContentListJsonRoot;
import github.zimoyin.core.favorites.pojo.id.FavoriteIDList;
import github.zimoyin.core.utils.net.httpclient.HttpClientResult;
import github.zimoyin.core.utils.net.httpclient.HttpClientUtils;

import java.io.IOException;
import java.util.HashMap;

/**
 * 获取收藏夹全部内容id
 */
public class FavoriteContentIdtList {
    private static final String URL = "http://api.bilibili.com/x/v3/fav/resource/ids";
    private Cookie cookie;

    public FavoriteContentIdtList() {
    }

    public FavoriteContentIdtList(Cookie cookie) {
        this.cookie = cookie;
    }


    /**
     * 获取收藏夹全部内容id
     * @param page  收藏夹内容明细列表 原数据
     * @return
     */
    public FavoriteIDList getJsonPojo(String page){
        FavoriteIDList root = JSONObject.parseObject(page, FavoriteIDList.class);
        return root;
    }

    /**
     * 获取收藏夹全部内容id
     * @param media_id 目标收藏夹mlid（完整id）
     * @return
     * @throws IOException
     */
    public FavoriteIDList getJsonPojo(long media_id) throws IOException {
        String page = getPage(media_id, null);
        FavoriteIDList root = JSONObject.parseObject(page, FavoriteIDList.class);
        return root;
    }

    /**
     * 获取收藏夹全部内容id
     * @param media_id 目标收藏夹mlid（完整id）
     * @param platform 平台标识
     * @return
     * @throws IOException
     */
    public FavoriteContentListJsonRoot getJsonPojo(long media_id, String platform) throws IOException {
        String page = getPage(media_id, platform);
        FavoriteContentListJsonRoot root = JSONObject.parseObject(page, FavoriteContentListJsonRoot.class);
        return root;
    }

    /**
     * 获取收藏夹全部内容id
     * @param media_id 目标收藏夹mlid（完整id）
     * @param platform 平台标识
     * @return
     */
    public String getPage(long media_id, String platform) throws IOException {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("media_id", String.valueOf(media_id));
        if (platform != null && platform.length() > 0)params.put("platform", platform);


        HttpClientResult httpClientResult = HttpClientUtils.doGet(URL, cookie != null ? cookie.toHeaderCookie() : null, params);
        return httpClientResult.getContent();
    }
}
