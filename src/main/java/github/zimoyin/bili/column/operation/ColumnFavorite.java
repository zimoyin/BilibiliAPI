package github.zimoyin.bili.column.operation;

import com.alibaba.fastjson.JSONObject;
import github.zimoyin.bili.cookie.Cookie;
import github.zimoyin.bili.pojo.Code;
import github.zimoyin.bili.utils.net.httpclient.HttpClientResult;
import github.zimoyin.bili.utils.net.httpclient.HttpClientUtils;

import java.io.IOException;
import java.util.HashMap;

/**
 * 收藏
 */
public class ColumnFavorite {

    private static final  String URL = "http://api.bilibili.com/x/article/favorites/add";
    private Cookie cookie;
    private String content;
    public ColumnFavorite(Cookie cookie) {
        this.cookie = cookie;
    }

    /**
     * 收藏
     * @param id cv id
     * @return
     * @throws IOException
     */
    public boolean favorite(long id) throws IOException {
        HashMap<String,String> params = new HashMap<String,String>();
        params.put("id", String.valueOf(id));
        params.put("type","2");
        params.put("csrf", cookie.getCsrf());

        HttpClientResult httpClientResult = HttpClientUtils.doGet(URL, cookie.toHeaderCookie(), params);
        String content = httpClientResult.getContent();
        return JSONObject.parseObject(content, Code.class).getCode() == 0;
    }
    public String getContent() {
        return content;
    }
}
