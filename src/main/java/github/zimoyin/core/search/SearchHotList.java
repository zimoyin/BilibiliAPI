package github.zimoyin.core.search;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import github.zimoyin.core.utils.net.httpclient.HttpClientResult;
import github.zimoyin.core.utils.net.httpclient.HttpClientUtils;
import lombok.Data;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

/**
 * 搜索热门词列表
 */
@Data
public class SearchHotList {
    private static final String URL ="http://s.search.bilibili.com/main/hotword";

    /**
     * 返回统计时间
     * @return
     * @throws IOException
     */
    public long getTime() throws IOException {
        String page = getPage();
        String[] words = new String[20];
        return JSONObject.parseObject(page).getLong("timestamp");
    }


    /**
     * 返回热门词，数组按照热度已经排序好了
     * @return
     * @throws IOException
     */
    public String[] getWords() throws IOException {
        String page = getPage();
        String[] words = new String[20];
        JSONArray list = (JSONArray) JSONObject.parseObject(page).get("list");
        for (Object json : list) {
            JSONObject jsonObject = JSONObject.parseObject(json.toString());
            String words0 = jsonObject.get("keyword").toString();
            String pos = jsonObject.get("pos").toString();
            words[Integer.parseInt(pos)-1] = words0;
        }
        return words;
    }

    /**
     * 获取热门列表
     * @return
     * @throws IOException
     */
    public String getPage() throws IOException {
        HttpClientResult httpClientResult = HttpClientUtils.doGet(URL);
        String content = httpClientResult.getContent();

        return content;
    }
}
