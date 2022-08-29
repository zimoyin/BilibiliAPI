package github.zimoyin.core.column.info;

import com.alibaba.fastjson.JSONObject;
import github.zimoyin.core.column.info.pojo.info.ColumnInfoJsonRoot;
import github.zimoyin.core.cookie.Cookie;
import github.zimoyin.core.utils.net.httpclient.HttpClientResult;
import github.zimoyin.core.utils.net.httpclient.HttpClientUtils;

import java.io.IOException;
import java.util.HashMap;

/**
 * 专栏基本信息
 */
public class ColumnInfo {
    private static final String URL = "http://api.bilibili.com/x/article/viewinfo";
    private Cookie cookie;

    public ColumnInfo() {
    }

    public ColumnInfo(Cookie cookie) {
        this.cookie = cookie;
    }


    /**
     * 专栏基本信息
     * @param cv cv号
     * @return
     * @throws IOException
     */
    public ColumnInfoJsonRoot getJsonPojo(long cv) throws IOException {
        String page = getPage(cv);
        ColumnInfoJsonRoot columInfoJsonRoot = JSONObject.parseObject(page, ColumnInfoJsonRoot.class);
        return columInfoJsonRoot;
    }

    /**
     * 专栏基本信息
     * @param cv cv号
     * @return
     */
    public String getPage(long cv) throws IOException {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("id", String.valueOf(cv));

        HttpClientResult result = HttpClientUtils.doGet(URL, cookie != null ? cookie.toHeaderCookie() : null, params);
        return result.getContent();
    }
}
