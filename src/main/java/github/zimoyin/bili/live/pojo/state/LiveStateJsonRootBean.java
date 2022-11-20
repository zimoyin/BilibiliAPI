/**
  * Copyright 2022 json.cn 
  */
package github.zimoyin.bili.live.pojo.state;

/**
 * Auto-generated: 2022-08-03 12:40:43
 *
 * @author json.cn (i@json.cn)
 * @website http://www.json.cn/java2pojo/
 */
@lombok.Data
public class LiveStateJsonRootBean {

    private int code;
    private String message;
    private int ttl;
    private Data data;


    @lombok.Data
    public static class Data{

        /**
         * 直播间状态
         * 0：无房间
         * 1：有房间
         */
        private int roomStatus;
        /**
         * 轮播状态
         * 0：未轮播
         * 1：轮播
         */
        private int roundStatus;
        /**
         * 直播状态
         * 0：未开播
         * 1：直播中
         */
        @Deprecated
        private int liveStatus;
        /**
         * 直播状态
         * 0：未开播
         * 1：直播中
         */
        private int live_status;
        /**
         * 直播间网页url
         */
        private String url;
        /**
         * 直播间标题
         */
        private String title;
        /**
         * 直播间封面url
         */
        private String cover;
        /**
         * 直播间人气	值为上次直播时刷新
         */
        private long online;
        /**
         * 直播间id（短号）
         */
        private int roomid;
        private int broadcast_type;
        private int online_hidden;
    }

}