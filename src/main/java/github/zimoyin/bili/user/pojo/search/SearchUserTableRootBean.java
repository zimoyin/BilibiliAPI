/**
  * Copyright 2022 json.cn 
  */
package github.zimoyin.bili.user.pojo.search;

import lombok.Data;

import java.util.List;

/**
 * Auto-generated: 2022-07-19 12:23:43
 *
 * @author json.cn (i@json.cn)
 * @website http://www.json.cn/java2pojo/
 */
@Data
public class SearchUserTableRootBean {

    private int code;
    private String message;
    private int ttl;
    private Data data;



    @lombok.Data
    public static class Data{

        /**
         * 未知
         */
        private String seid;
        /**
         * 当前页数
         */
        private int page;
        /**
         * 允许查找的最大总页数
         */
        private int pagesize;
        /**
         * 返回多少个用户项
         */
        private int numResults;
        /**
         * 查找出来的总页数
         */
        private int numPages;
        /**
         * 未知
         */
        private String suggest_keyword;
        /**
         * 未知
         */
        private String rqt_type;
        /**
         * 未知
         */
        private Cost_time cost_time;
        /**
         * 未知
         */
//    private Exp_list exp_list;
        /**
         * 未知
         */
        private int egg_hit;
        /**
         * 查找结果
         */
        private List<Result> result;
        /**
         * 未知
         */
        private int show_column;
        /**
         * 未知
         */
        private int in_black_key;
        /**
         * 未知
         */
        private int in_white_key;
    }
}