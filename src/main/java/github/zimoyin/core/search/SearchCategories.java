package github.zimoyin.core.search;

import com.alibaba.fastjson.JSONObject;
import github.zimoyin.core.cookie.Cookie;
import github.zimoyin.core.cookie.GlobalCookie;
import github.zimoyin.core.cookie.WebCookie;
import github.zimoyin.core.exception.CookieNotFoundException;
import github.zimoyin.core.search.enums.*;
import github.zimoyin.core.search.pojo.search.Result;
import github.zimoyin.core.search.pojo.search.SearchCategoriesJsonRoot;
import github.zimoyin.core.search.pojo.search.result.ResultVideo;
import github.zimoyin.core.utils.net.httpclient.HttpClientResult;
import github.zimoyin.core.utils.net.httpclient.HttpClientUtils;
import lombok.Data;

import java.io.IOException;
import java.util.HashMap;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * 分类搜索（web端）
 * 注意：有cookie和无cookie返回内容不一致
 */
@Data
public class SearchCategories {
    //https://api.bilibili.com/x/web-interface/search/type
    private static final String URL = "http://api.bilibili.com/x/web-interface/search/type";
    private Cookie cookie;

    public SearchCategories() {
        cookie = new WebCookie();
        try {
            cookie.updateCookie();
        } catch (IOException e) {
            throw new RuntimeException("Cookie更新失败，请手动更新或设置Cookie",e);
        }
    }

    public SearchCategories(Cookie cookie) {
        this.cookie = cookie;
    }


    /**
     * 获取该词下载能搜到的所有东西
     *
     * @param word
     * @return
     * @throws IOException
     */
    public Result getResult(String word) throws IOException {
        Result result0 = new Result();
        for (SearchType value : SearchType.values()) {
            SearchCategoriesParams params = new SearchCategoriesParams(word, value);
            SearchCategoriesJsonRoot.Data data = getJsonPojo(params).getData();
            Result result = data.getResult();
            if (result.getLiveType() != null) result0.setLiveType(result.getLiveType());
            if (result.getColumnType() != null) result0.setColumnType(result.getColumnType());
            if (result.getMovieType() != null) result0.setMovieType(result.getMovieType());
            if (result.getTopicType() != null) result0.setTopicType(result.getTopicType());
            if (result.getLiveUserType() != null) result0.setLiveUserType(result.getLiveUserType());
            if (result.getPhotoType() != null) result0.setPhotoType(result.getPhotoType());
            if (result.getUserType() != null) result0.setUserType(result.getUserType());
            if (result.getVideoType() != null) result0.setVideoType(result.getVideoType());//
        }
        return result0;
    }

    /**
     * 分类搜索
     *
     * @param params 查询参数
     * @return
     * @throws IOException
     */
    public SearchCategoriesJsonRoot getJsonPojo(SearchCategoriesParams params) throws IOException {
        String page = getPage(params);
        return JSONObject.parseObject(page, SearchCategoriesJsonRoot.class);
    }

    /**
     * 分类搜索
     *
     * @param params 查询参数
     * @return
     * @throws IOException
     */
    public String getPage(SearchCategoriesParams params) throws IOException {
        if (params == null || params.getKeyword() == null) throw new IllegalArgumentException("params is empty");
        HttpClientResult result = HttpClientUtils.doGet(URL, cookie != null ? cookie.toHeaderCookie() : null, params.getParams());
        return result.getContent();
    }

    /**
     * 分类搜索参数封装
     */
    @Data
    public static class SearchCategoriesParams {
        /**
         * 需要搜索的关键词(必要)
         */
        private String keyword;
        /**
         * 搜索目标类型(必要)
         */
        private SearchType searchType = SearchType.Video;


        /**
         * 页码(非必要)
         */
        private int page = -1;
        /**
         * 返回条数，默认42,最大 50
         */
        private int page_size = -1;
        /**
         * 结果偏移数，默认0
         */
        private int dynamic_offset = -1;
        /**
         * 专栏及相簿分区筛选(非必要)
         */
        private SearchCategoryID searchCategoryID;
        /**
         * order(非必要): 结果排序方式
         */
        private SearchOrder searchOrder;
        /**
         * (非必要) 用户粉丝数及等级排序顺序
         * 仅用于搜索用户
         */
        private SearchSort searchSort;
        /**
         * 视频分区筛选(非必要)	仅用于搜索视频
         */
        private SearchTids searchTids;
        /**
         * (非必要) 用户分类筛选:仅用于搜索用户
         */
        private SearchUserType searchUserType;
        /**
         * 视频时长筛选(非必要)	仅用于搜索视频
         */
        private SearchVideoDuration searchVideoDuration;

        /**
         * 参数
         */
        private HashMap<String, String> params = new HashMap<String, String>();

        public SearchCategoriesParams(String keyword, SearchType searchType) {
            this.keyword = keyword;
            this.searchType = searchType;
        }

        public SearchCategoriesParams(String keyword) {
            this.keyword = keyword;
        }


        /**
         * 为所有的属性赋值默认类型
         */
        public void setAllVal() {
            this.searchType = SearchType.Video;
            this.page = 1;
            this.page_size = 42;
            this.dynamic_offset = 0;
            this.searchCategoryID = SearchCategoryID.Column_All;
            this.searchOrder = SearchOrder.TotalRank;
            this.searchSort = SearchSort.AscendingOrder;
            this.searchTids = SearchTids.All;
            this.searchUserType = SearchUserType.All;
            this.searchVideoDuration = SearchVideoDuration.All;
        }

        /**
         * 构建参数
         *
         * @return
         */
        public HashMap<String, String> getParams() {
            //必要参数
            params.put("search_type", searchType.getType());//搜索目标类型
            params.put("keyword", keyword);//需要搜索的关键词
            //非必要参数
            if (searchOrder != null) params.put("order", searchOrder.getOrder());//结果排序方式
            if (searchSort != null) params.put("order_sort", searchSort.getSort());//用户粉丝数及等级排序顺序
            if (searchUserType != null) params.put("user_type", searchUserType.getType());//用户分类筛选
            if (searchVideoDuration != null) params.put("duration", searchVideoDuration.getDuration());//视频时长筛选
            if (searchTids != null) params.put("tids", searchTids.getTid());//视频分区筛选
            if (searchCategoryID != null) params.put("category_id", searchCategoryID.getId());//专栏及相簿分区筛选
            if (this.page > -1) params.put("page", String.valueOf(this.page));//页码
            if (this.dynamic_offset > -1) params.put("dynamic_offset", String.valueOf(this.dynamic_offset));//结果偏移数，默认0
            if (this.page_size > -1 && this.page_size <= 50)
                params.put("page_size", String.valueOf(this.page_size));//返回条数，默认42


            return params;
        }
    }
}

