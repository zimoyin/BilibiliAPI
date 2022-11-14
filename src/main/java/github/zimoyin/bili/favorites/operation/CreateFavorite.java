package github.zimoyin.bili.favorites.operation;

import com.alibaba.fastjson.JSONObject;
import github.zimoyin.bili.cookie.Cookie;
import github.zimoyin.bili.pojo.Code;
import github.zimoyin.bili.utils.net.httpclient.HttpClientResult;
import github.zimoyin.bili.utils.net.httpclient.HttpClientUtils;

import java.io.IOException;
import java.util.HashMap;

/**
 * 新建个收藏夹
 */
public class CreateFavorite {
    private static final String URL = "http://api.bilibili.com/x/v3/fav/folder/add";
    private Cookie cookie;
    private String content;

    public CreateFavorite(Cookie cookie) {
        this.cookie = cookie;
    }

    /**
     * @param title 收藏夹标题
     * @return
     */
    public boolean create(String title) throws IOException {
        return create(title, null, true, null);
    }

    /**
     * @param title 收藏夹标题
     * @param intro 收藏夹简介
     * @return
     */
    public boolean create(String title, String intro) throws IOException {
        return create(title, intro, true, null);
    }

    /**
     * @param title   收藏夹标题
     * @param privacy 是否公开
     * @return
     */
    public boolean create(String title, boolean privacy) throws IOException {
        return create(title, null, privacy, null);
    }

    /**
     * @param title   收藏夹标题
     * @param intro   收藏夹简介
     * @param privacy 是否公开
     * @return
     */
    public boolean create(String title, String intro, boolean privacy) throws IOException {
        return create(title, intro, privacy, null);
    }


    /**
     * @param title   收藏夹标题
     * @param intro   收藏夹简介
     * @param privacy 是否公开
     * @param cover   封面图url(封面会被审核)
     * @return
     */
    public boolean create(String title, String intro, boolean privacy, String cover) throws IOException {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("title", title);
        if (intro != null) params.put("intro", intro);
        if (cover != null) params.put("cover", cover);
        params.put("privacy", String.valueOf(privacy?0:1));
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
