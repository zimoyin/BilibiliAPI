package github.zimoyin.bili.favorites.operation;

import com.alibaba.fastjson.JSONObject;
import github.zimoyin.bili.cookie.Cookie;
import github.zimoyin.bili.favorites.FavoriteUtil;
import github.zimoyin.bili.pojo.Code;
import github.zimoyin.bili.utils.net.httpclient.HttpClientResult;
import github.zimoyin.bili.utils.net.httpclient.HttpClientUtils;

import java.io.IOException;
import java.util.HashMap;

/**
 * 删除收藏文件夹
 */
public class DeleteFavorite {
    private static final String URL = "http://api.bilibili.com/x/v3/fav/folder/del";
    private Cookie cookie;
    private long media_id;
    private String content;

    /**
     * @param media_id 收藏夹的id
     * @param cookie   cookie
     */
    public DeleteFavorite(long media_id, Cookie cookie) {
        this.cookie = cookie;
        this.media_id = media_id;
    }

    /**
     * @param userName 用户的名称
     * @param title    收藏夹的标题
     * @param cookie   cookie
     */
    public DeleteFavorite(String userName, String title, Cookie cookie) {
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
    public DeleteFavorite(long  mid, String title, Cookie cookie) {
        this.cookie = cookie;
        try {
            this.media_id = new FavoriteUtil(cookie).getFavoriteID(mid, title);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public boolean delete() throws IOException {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("media_ids", String.valueOf(media_id));
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
