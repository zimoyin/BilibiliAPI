/**
  * Copyright 2022 json.cn 
  */
package github.zimoyin.core.search.pojo.search.result.user;

import lombok.Data;

/**
 * Auto-generated: 2022-08-19 20:44:23
 *
 * @author json.cn (i@json.cn)
 * @website http://www.json.cn/java2pojo/
 */
@Data
public class Res {

    private long aid;
    private String bvid;
    private String title;
    /**
     * 视频投稿时间	时间戳
     */
    private long pubdate;
    /**
     * 视频页面重定向url
     */
    private String arcurl;
    /**
     * 视频封面图片url
     */
    private String pic;
    /**
     * 	播放量
     */
    private String play;
    /**
     * 弹幕量
     */
    private int dm;
    /**
     * 投币数
     */
    private int coin;
    /**
     * 收藏数
     */
    private int fav;
    /**
     * 视频简介
     */
    private String desc;
    /**
     * 视频时长	MM:SS
     */
    private String duration;
    private int is_pay;
    /**
     * 是否为合作视频	0：否
     * 1：是
     */
    private int is_union_video;
}