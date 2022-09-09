/**
 * Copyright 2022 json.cn
 */
package github.zimoyin.core.column.info.pojo.anthology;

import java.util.List;

/**
 * Auto-generated: 2022-08-28 18:54:44
 *
 * @author json.cn (i@json.cn)
 * @website http://www.json.cn/java2pojo/
 */
@lombok.Data
public class AnthologyInfoJsonRoot {

    private int code;
    private String message;
    private int ttl;
    private Data data;

    @lombok.Data
    public class Data {
        /**
         * 文集概览
         */
        private AnthologyList list;
        /**
         * 文集内的文章列表
         */
        private List<Articles> articles;
        /**
         * 文集作者信息
         */
        private Author author;
        /**
         * 作用尚不明确
         * 结构与data.articles[]中相似
         */
        private Last last;
        /**
         * 是否关注文集作者
         * false：未关注
         * true：已关注
         * 需要登录(Cookie)
         * 未登录为false
         */
        private boolean attention;
    }

}