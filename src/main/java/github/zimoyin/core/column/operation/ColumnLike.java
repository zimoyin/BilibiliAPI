package github.zimoyin.core.column.operation;

import com.alibaba.fastjson.JSONObject;
import github.zimoyin.core.cookie.Cookie;
import github.zimoyin.core.pojo.Code;
import github.zimoyin.core.utils.net.httpclient.HttpClientResult;
import github.zimoyin.core.utils.net.httpclient.HttpClientUtils;

import java.io.IOException;
import java.util.HashMap;

/**
 * 点赞
 */
public class ColumnLike {
    private static final  String URL = "http://api.bilibili.com/x/article/like";
    private Cookie cookie;
    private String content;
    public ColumnLike(Cookie cookie) {
        this.cookie = cookie;
    }

    /**
     * 取消点赞
     * @param id cv id
     * @return
     * @throws IOException
     */
    public boolean notLike(long id) throws IOException {
        HashMap<String,String> params = new HashMap<String,String>();
        params.put("id", String.valueOf(id));
        params.put("type","2");
        params.put("csrf", cookie.getCsrf());

        HttpClientResult httpClientResult = HttpClientUtils.doGet(URL, cookie.toHeaderCookie(), params);
        String content = httpClientResult.getContent();
        return JSONObject.parseObject(content, Code.class).getCode() == 0;
    }

    /**
     * 点赞
     * @param id cv id
     * @return
     * @throws IOException
     */
    public boolean like(long id) throws IOException {
        HashMap<String,String> params = new HashMap<String,String>();
        params.put("id", String.valueOf(id));
        params.put("type","1");
        params.put("csrf", cookie.getCsrf());

        HttpClientResult httpClientResult = HttpClientUtils.doGet(URL, cookie.toHeaderCookie(), params);
        String content = httpClientResult.getContent();
        return JSONObject.parseObject(content, Code.class).getCode() == 0;
    }

    public String getContent() {
        return content;
    }
}
