package github.zimoyin.core.favorites.operation;

import com.alibaba.fastjson.JSONObject;
import github.zimoyin.core.cookie.Cookie;
import github.zimoyin.core.favorites.FavoriteUtil;
import github.zimoyin.core.pojo.Code;
import github.zimoyin.core.utils.net.httpclient.HttpClientResult;
import github.zimoyin.core.utils.net.httpclient.HttpClientUtils;

import java.io.IOException;
import java.util.HashMap;

/**
 * 清理失效内容
 */
public class ClearInvalidFavorite {
    private static final String URL = "http://api.bilibili.com/x/v3/fav/resource/clean";
    private Cookie cookie;
    private long media_id;
    private String content;

    /**
     * @param media_id 收藏夹的id
     * @param cookie   cookie
     */
    public ClearInvalidFavorite(long media_id, Cookie cookie) {
        this.cookie = cookie;
        this.media_id = media_id;
    }

    /**
     * @param userName 用户的名称
     * @param title    收藏夹的标题
     * @param cookie   cookie
     */
    public ClearInvalidFavorite(String userName, String title, Cookie cookie) {
        this.cookie = cookie;
        try {
            this.media_id = new FavoriteUtil(cookie).getFavoriteID(userName, title);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @param mid 用户的id
     * @param title    收藏夹的标题
     * @param cookie   cookie
     */
    public ClearInvalidFavorite(long  mid, String title, Cookie cookie) {
        this.cookie = cookie;
        try {
            this.media_id = new FavoriteUtil(cookie).getFavoriteID(mid, title);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public boolean clear() throws IOException {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("media_id", String.valueOf(media_id));
        params.put("csrf", cookie.getCsrf());

        HttpClientResult httpClientResult = HttpClientUtils.doPost(URL, cookie.toHeaderCookie(), params);
        String content = httpClientResult.getContent();
        this.content = content;
        if (JSONObject.parseObject(content, Code.class).getCode() == 0) {
            return true;
        }
        return false;
    }

    /**
     * 执行结果
     * @return
     */
    public String getContent() {
        return content;
    }
}
