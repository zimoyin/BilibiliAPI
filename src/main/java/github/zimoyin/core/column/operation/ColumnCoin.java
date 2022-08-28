package github.zimoyin.core.column.operation;

import com.alibaba.fastjson.JSONObject;
import github.zimoyin.core.cookie.Cookie;
import github.zimoyin.core.pojo.Code;
import github.zimoyin.core.utils.net.httpclient.HttpClientResult;
import github.zimoyin.core.utils.net.httpclient.HttpClientUtils;

import java.io.IOException;
import java.util.HashMap;

public class ColumnCoin {
    private static final  String URL = "http://api.bilibili.com/x/web-interface/coin/add";
    private Cookie cookie;
    private String content;
    public ColumnCoin(Cookie cookie) {
        this.cookie = cookie;
    }

    /**
     * 投币1个
     * @param id cv id
     * @param mid 作者的mid
     * @return
     * @throws IOException
     */
    public boolean coin(long id,long mid) throws IOException {
        HashMap<String,String> params = new HashMap<String,String>();
        params.put("aid", String.valueOf(id));
        params.put("type","2");
        params.put("avtype","2");
        params.put("upid","2");
        params.put("multiply","1");
        params.put("csrf", cookie.getCsrf());

        HttpClientResult httpClientResult = HttpClientUtils.doGet(URL, cookie.toHeaderCookie(), params);
        String content = httpClientResult.getContent();
        return JSONObject.parseObject(content, Code.class).getCode() == 0;
    }

    public String getContent() {
        return content;
    }
}
