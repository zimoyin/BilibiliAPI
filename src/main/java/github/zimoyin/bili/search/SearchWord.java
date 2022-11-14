package github.zimoyin.bili.search;

import com.alibaba.fastjson.JSONObject;
import github.zimoyin.bili.utils.net.httpclient.HttpClientResult;
import github.zimoyin.bili.utils.net.httpclient.HttpClientUtils;
import lombok.Data;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;

/**
 * 搜索建议关键词：根据已经给出的词来推测你想要搜索的词条
 * 注意：该API拥有三个值，其中只有这一个值是与拥有其他值是具有区别的，仅此返回的是两套JSON，故而此处不采用JSON 的pojo 进行接收
 * 这里只有一个值，是最为特殊的返回值类型
 * =======================================================
 * 参数                  参数值
 * term		            需要获得建议的输入内容
 * main_ver				固定为v1
 * highlight		    有此项开启关键词高亮标签
 * =======================================================
 */
@Data
public class SearchWord {
    private static final String URL = "http://s.search.bilibili.com/main/suggest?term=%s";

    /**
     * 根据关键词推测你要查找的词条
     * @param text 关键词
     * @return
     * @throws IOException
     */
    public ArrayList<String> getWords(String text) throws IOException {
        String page = getPage(text);
        ArrayList<String> words = new ArrayList<String>();
        JSONObject json = JSONObject.parseObject(page,JSONObject.class);
        Set<String> keySet = json.keySet();
        for (String key : keySet) {
            JSONObject value = (JSONObject) json.get(key);
            String text0 = value.get("value").toString();
            words.add(text0);
        }
        return words;
    }

    /**
     * 根据关键词推测你要查找的词条
     * @param text 关键词
     * @return
     * @throws IOException
     */
    public String getPage(String text) throws IOException {
        HttpClientResult httpClientResult = HttpClientUtils.doGet(String.format(URL, text));
        return httpClientResult.getContent();
    }
}
