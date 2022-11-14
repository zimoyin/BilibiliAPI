package github.zimoyin.bili.column.info;


import com.alibaba.fastjson.JSONObject;
import github.zimoyin.bili.column.info.pojo.anthology.AnthologyInfoJsonRoot;
import github.zimoyin.bili.cookie.Cookie;
import github.zimoyin.bili.utils.net.httpclient.HttpClientResult;
import github.zimoyin.bili.utils.net.httpclient.HttpClientUtils;

import java.io.IOException;
import java.util.HashMap;

/**
 * 获取文集基本信息
 */
public class AnthologyInfo {
    private static final String URL = "http://api.bilibili.com/x/article/list/web/articles";
    private Cookie cookie;

    public AnthologyInfo() {
    }

    public AnthologyInfo(Cookie cookie) {
        this.cookie = cookie;
    }

    public AnthologyInfoJsonRoot getJsonPojo(long id) throws IOException {
        String page = getPage(id);
        AnthologyInfoJsonRoot root = JSONObject.parseObject(page, AnthologyInfoJsonRoot.class);
        return root;
    }

    /**
     *
     * @param id 文集rlid
     * @return
     */
    public String getPage(long id) throws IOException {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("id", String.valueOf(id));

        HttpClientResult httpClientResult = HttpClientUtils.doGet(URL, cookie != null ? cookie.toHeaderCookie() : null, params);
        return httpClientResult.getContent();
    }
}
