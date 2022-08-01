/**
  * Copyright 2022 json.cn 
  */
package github.zimoyin.core.user.pojo.works;

import github.zimoyin.core.video.info.pojo.info.data.subtitle.Subtitle;
import lombok.Data;

import java.net.URL;

/**
 * Auto-generated: 2022-07-19 13:12:49
 *
 * @author json.cn (i@json.cn)
 * @website http://www.json.cn/java2pojo/
 */
@Data
public class Vlist {

    private int comment;
    private int typeid;
    /**
     * 观看人数
     */
    private int play;
    /**
     * 封面
     */
    private URL pic;
    /**
     * 视频CC字幕信息
     */
    private Subtitle subtitle;
    /**
     * 简介
     */
    private String description;
    /**
     * 视频类型
     * 1:原创
     * 2：转载
     */
    private String copyright;
    /**
     * 标题
     */
    private String title;
    private int review;
    /**
     * 作者
     */
    private String author;
    /**
     * mid
     */
    private long mid;

    private long aid;
    private String bvid;

    private long created;
    /**
     * 视频时长  格式化后的（分钟：秒）
     */
    private String length;
    private int video_review;

    private boolean hide_click;
    /**
     * 正在观看
     */
    private int is_pay;
    private int is_union_video;
    private int is_steins_gate;
    private int is_live_playback;
}