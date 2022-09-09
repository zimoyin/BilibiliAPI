/**
  * Copyright 2022 json.cn 
  */
package github.zimoyin.core.user.pojo.state.contribution;

import lombok.Data;

/**
 * Auto-generated: 2022-07-23 19:35:58
 *
 * @author json.cn (i@json.cn)
 * @website http://www.json.cn/java2pojo/
 */
@Data
public class ContributionStatJsonRootBean {

    private int code;
    private String message;
    private int ttl;
    private Data data;
    @lombok.Data
    public class Data {

        /**
         * 投稿视频数
         */
        private int video;
        /**
         * 追番数:无视隐私设置
         */
        private int bangumi;
        /**
         * 频道数
         */
        private int cinema;
        /**
         * 收藏夹数	无视隐私设置
         */
        private Channel channel;
        /**
         * 关注TAG数	无视隐私设置
         */
        private Favourite favourite;
        /**
         * 投稿专栏数
         */
        private int tag;

        private int article;
        private int playlist;
        /**
         * 投稿相簿数
         */
        private int album;
        /**
         * 投稿音频数
         */
        private int audio;
        /**
         * 投稿课程数
         */
        private int pugv;
        private int season_num;
    }
}