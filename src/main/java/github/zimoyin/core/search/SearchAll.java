package github.zimoyin.core.search;

import com.alibaba.fastjson.JSONObject;
import github.zimoyin.core.cookie.Cookie;
import github.zimoyin.core.search.pojo.sall.SearchAllJsonRoot;
import github.zimoyin.core.utils.net.httpclient.HttpClientResult;
import github.zimoyin.core.utils.net.httpclient.HttpClientUtils;
import lombok.Data;

import java.io.IOException;

/**
 * 综合搜索
 * 返回和关键字相关的20条信息
 * 综合搜索为默认搜索方式，主要用于优先搜索用户、影视、番剧、游戏、话题等，
 * 并加载第一页的20项相关视频，还用于展示各个类型的结果数目，便于进一步分类搜索
 */
@Data
public class SearchAll {
    private static final String URL = "http://api.bilibili.com/x/web-interface/search/all/v2?keyword=%s";
    private Cookie cookie;

    public SearchAll() {
    }

    public SearchAll(Cookie cookie) {
        this.cookie = cookie;
    }

    /**
     * 返回和关键字相关的20条信息
     * @param keyword
     * @return
     * @throws IOException
     */
    public SearchAllJsonRoot getJsonPojo(String keyword) throws IOException {
        String page = getPage(keyword);
        SearchAllJsonRoot searchAllJsonRoot = JSONObject.parseObject(page, SearchAllJsonRoot.class);
        return searchAllJsonRoot;
    }


    /**
     * 返回和关键字相关的20条信息
     * @param keyword
     * @return
     * @throws IOException
     */
    public String getPage(String keyword) throws IOException {
        HttpClientResult httpClientResult = HttpClientUtils.doGet(String.format(URL, keyword),
                cookie != null ? cookie.toHeaderCookie() : null,null);
        return httpClientResult.getContent();
    }
}
