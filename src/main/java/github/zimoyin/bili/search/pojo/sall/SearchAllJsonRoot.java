/**
  * Copyright 2022 json.cn 
  */
package github.zimoyin.bili.search.pojo.sall;

import java.util.List;

/**
 * Auto-generated: 2022-08-19 16:47:20
 *
 * @author json.cn (i@json.cn)
 * @website http://www.json.cn/java2pojo/
 */
@lombok.Data
public class SearchAllJsonRoot {

    private int code;
    private String message;
    private int ttl;
    private Data data;


    @lombok.Data
    public static class Data{

        /**
         * 搜索id
         */
        private String seid;
        /**
         * 页数
         */
        private int page;
        /**
         * 每页条数
         */
        private int pagesize;
        /**
         * 总条数
         */
        private int numResults;
        /**
         * 	分页数
         */
        private int numPages;
        private String suggest_keyword;
        /**
         * search
         */
        private String rqt_type;
        /**
         * 详细搜索用时
         */
        private Cost_time cost_time;
        private String exp_list;
        private int egg_hit;
        /**
         * 分类页数信息
         */
        private Pageinfo pageinfo;
        /**
         * 分类结果数目信息
         */
        private Pageinfo2 top_tlist;
        private int show_column;
        /**
         * 返回结果类型列表
         * 项	类型	    内容	                备注
         * 0	str	    activity
         * 1	str	    web_game	        游戏
         * 2	str	    card
         * 3	str	    media_bangumi	    番剧
         * 4	str	    media_ft	        电影
         * 5	str	    bili_user	        用户
         * 6	str	    user
         * 7	str	    star
         * 8	str	    video	            视频
         */
        private List<String> show_module_list;
        private int in_black_key;
        private int in_white_key;
        /**
         * 结果列表
         * data中的result数组：
         *
         * 项	类型	    内容
         * 0	obj	    -
         * 1	obj	    游戏结果
         * 2	obj	    -
         * 3	obj	    番剧结果
         * 4	obj	    电影结果
         * 5	obj	    用户结果
         * 6	obj	    -
         * 7	obj	    -
         * 8	obj	    视频结果
         */
        private List<Result> result;

    }
}