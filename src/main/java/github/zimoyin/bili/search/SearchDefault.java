package github.zimoyin.bili.search;


import com.alibaba.fastjson.JSONObject;
import github.zimoyin.bili.utils.net.httpclient.HttpClientResult;
import github.zimoyin.bili.utils.net.httpclient.HttpClientUtils;
import lombok.Data;

import java.io.IOException;

/**
 * 返回搜索栏默认词条搜索的内容
 */
@Data
public class SearchDefault {
    private static final String URL ="http://api.bilibili.com/x/web-interface/search/default";
    private JSONObject json;
    /**
     * 搜索目标跳转url
     * @return
     */
    public String getURL() throws IOException {
        if (isEmpty()) return null;
        JSONObject data = (JSONObject) json.get("data");
        return data.get("url").toString();
    }


    /**
     * 搜索显示的文字
     * @return
     */
    public String getWord() throws IOException {
        if (isEmpty()) return null;
        JSONObject data = (JSONObject) json.get("data");
        return data.get("show_name").toString();
    }

    /**
     * 获取稿件的AV ID
     * @return
     */
    public String getAV() throws IOException {
        if (isEmpty()) return null;
        JSONObject data = (JSONObject) json.get("data");
        return data.get("goto_value").toString();
    }

    public boolean isEmpty() throws IOException {
        return json.getInteger("code") != 0;
    }
    /**
     * 返回搜索栏默认词条搜索的内容(详细数据)
     * @return
     * @throws IOException
     */
    public String getPage() throws IOException {
        HttpClientResult httpClientResult = HttpClientUtils.doGet(URL);
        String content = httpClientResult.getContent();
        json = JSONObject.parseObject(content);
        return content;
    }
}
