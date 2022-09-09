/**
  * Copyright 2022 json.cn 
  */
package github.zimoyin.core.live.pojo.statev2;

import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Auto-generated: 2022-08-03 16:57:25
 *
 * @author json.cn (i@json.cn)
 * @website http://www.json.cn/java2pojo/
 */
@lombok.Data
public class LiveStateV2JsonRootBean {

    private int code;
    private String msg;
    private String message;
    /**
     * 数据
     * key:用户的mid
     * value: data
     */
    private HashMap<Long, Data> data;


    @lombok.Data
    public class Data {

        /**
         * 直播间标题
         */
        private String title;
        /**
         * 直播间房间号	直播间实际房间号
         */
        private long room_id;
        /**
         * 主播mid
         */
        private long uid;
        /**
         * 直播间在线人数
         */
        private int online;
        /**
         * 直播持续时长
         */
        private long live_time;
        /**
         * 直播间开播状态
         * 0：未开播
         * 1：正在直播
         * 2：轮播中
         */
        private int live_status;
        /**
         * 直播间房间号
         * 直播间短房间号，常见于签约主播
         */
        private int short_id;
        /**
         * 直播间分区id
         */
        private int area;
        /**
         * 直播间分区名
         */
        private String area_name;
        /**
         * 直播间新版分区id
         */
        private int area_v2_id;
        /**
         * 直播间新版分区名
         */
        private String area_v2_name;
        /**
         * 直播间父分区名
         */
        private String area_v2_parent_name;

        /**
         * 直播间父分区id
         */
        private int area_v2_parent_id;
        /**
         * 主播用户名
         */
        private String uname;
        /**
         * 主播头像url
         */
        private String face;
        /**
         * 直播间标签
         */
        private String tag_name;
        /**
         * 直播间自定标签
         */
        private String tags;
        /**
         * 直播间封面url
         */
        private String cover_from_user;
        /**
         * 直播间关键帧url
         */
        private String keyframe;
        /**
         * 直播间封禁信息
         */
        private String lock_till;
        /**
         * 直播间隐藏信息
         */
        private String hidden_till;
        /**
         * 直播类型
         * 0:普通直播
         * 1：手机直播
         */
        private int broadcast_type;

    }
}