package github.zimoyin.bili.favorites.operation;

import com.alibaba.fastjson.JSONObject;
import github.zimoyin.bili.Incompleteness;
import github.zimoyin.bili.cookie.Cookie;
import github.zimoyin.bili.favorites.FavoriteUtil;
import github.zimoyin.bili.pojo.Code;
import github.zimoyin.bili.utils.net.httpclient.HttpClientResult;
import github.zimoyin.bili.utils.net.httpclient.HttpClientUtils;

import java.io.IOException;
import java.util.HashMap;

/**
 * 修改收藏文件夹
 */
@Incompleteness
public class ModifyFavorite {
    private static final String URL = "http://api.bilibili.com/x/v3/fav/folder/edit";
    private Cookie cookie;
    private long media_id;
    private String content;

    /**
     * @param media_id 收藏夹的id
     * @param cookie   cookie
     */
    public ModifyFavorite(long media_id, Cookie cookie) {
        this.cookie = cookie;
        this.media_id = media_id;
    }
    /**
     * @param mid 用户的id
     * @param title    收藏夹的标题
     * @param cookie   cookie
     */
    public ModifyFavorite(long  mid, String title, Cookie cookie) {
        this.cookie = cookie;
        try {
            this.media_id = new FavoriteUtil(cookie).getFavoriteID(mid, title);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @param userName 用户的名称
     * @param title    收藏夹的标题
     * @param cookie   cookie
     */
    public ModifyFavorite(String userName, String title, Cookie cookie) {
        this.cookie = cookie;
        try {
            this.media_id = new FavoriteUtil(cookie).getFavoriteID(userName, title);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @param title 收藏夹标题
     * @return
     */
    public boolean modify(String title) throws IOException {
        return modify(title, null, true, null);
    }

    /**
     * @param title 收藏夹标题
     * @param intro 收藏夹简介
     * @return
     */
    public boolean modify(String title, String intro) throws IOException {
        return modify(title, intro, true, null);
    }

    /**
     * @param title   收藏夹标题
     * @param privacy 是否公开
     * @return
     */
    public boolean modify(String title, boolean privacy) throws IOException {
        return modify(title, null, privacy, null);
    }

    /**
     * @param title   收藏夹标题
     * @param intro   收藏夹简介
     * @param privacy 是否公开
     * @return
     */
    public boolean modify(String title, String intro, boolean privacy) throws IOException {
        return modify(title, intro, privacy, null);
    }


    /**
     * @param title   收藏夹标题
     * @param intro   收藏夹简介
     * @param privacy 是否公开
     * @param cover   封面图url(封面会被审核)
     * @return
     */
    public boolean modify(String title, String intro, boolean privacy, String cover) throws IOException {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("media_id", String.valueOf(media_id));
        params.put("title", title);
        if (intro != null) params.put("intro", intro);
        if (cover != null) params.put("cover", cover);
        params.put("privacy", String.valueOf(privacy ? 0 : 1));
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
