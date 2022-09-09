/**
  * Copyright 2022 json.cn 
  */
package github.zimoyin.core.live.pojo.info;

/**
 * Auto-generated: 2022-08-03 12:30:27
 *
 * @author json.cn (i@json.cn)
 * @website http://www.json.cn/java2pojo/
 */
@lombok.Data
public class LiveInfoJsonRootBean {

    /**
     * 0：成功
     * 60004：直播间不存在
     */
    private int code;
    /**
     * 错误信息
     * 默认为ok
     */
    private String msg;
    /**
     * 错误信息
     * 默认为ok
     */
    private String message;
    private Data data;



    @lombok.Data
    public class Data {

        /**
         * 直播间真实id
         */
        private long room_id;
        /**
         * 直播间id（短号）
         */
        private long short_id;
        /**
         * 主播用户mid
         */
        private long uid;
        /**
         * 是否p2p
         */
        private int need_p2p;
        /**
         * 	是否隐藏
         */
        private boolean is_hidden;
        /**
         * 是否锁定
         */
        private boolean is_locked;
        /**
         * 是否竖屏
         */
        private boolean is_portrait;
        /**
         * 直播状态
         * 0：未开播
         * 1：直播中
         * 2：轮播中
         */
        private int live_status;
        /**
         * 隐藏时间戳
         */
        private int hidden_till;
        /**
         * 锁定时间戳
         */
        private int lock_till;
        /**
         * 是否加密
         */
        private boolean encrypted;
        /**
         * 加密房间是否通过密码验证
         * encrypted=true时才有意义
         */
        private boolean pwd_verified;
        /**
         * 开播时间
         * 未开播时为-62170012800
         */
        private long live_time;
        private int room_shield;
        /**
         * 是否为特殊直播间
         * 0：普通直播间
         * 1：付费直播间
         */
        private int is_sp;
        /**
         * 特殊直播间标志
         * 0：普通直播间
         * 1：付费直播间
         * 2：拜年祭直播间
         */
        private int special_type;
    }
}