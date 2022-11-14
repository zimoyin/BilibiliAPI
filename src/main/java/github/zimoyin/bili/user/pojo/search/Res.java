/**
  * Copyright 2022 json.cn 
  */
package github.zimoyin.bili.user.pojo.search;

import lombok.Data;

import java.net.URL;

/**
 * Auto-generated: 2022-07-19 12:23:43
 *
 * @author json.cn (i@json.cn)
 * @website http://www.json.cn/java2pojo/
 */
@Data
public class Res {

    /**
     * 视频的avid
     */
    private long aid;
    /**
     * 视频的bvid
     */
    private String bvid;
    /**
     * 视频标题
     */
    private String title;
    /**
     * 发布日期，时间戳（秒）
     */
    private long pubdate;
    /**
     * 视频url
     */
    private URL arcurl;
    /**
     * 封面URL
     */
    private String pic;
    /**
     * 观看人数
     */
    private String play;
    private int dm;
    /**
     * 投币数
     */
    private int coin;
    /**
     * 收藏
     */
    private int fav;
    /**
     * 简介
     */
    private String desc;
    /**
     * 视频时间长度（格式化后的  (小时：)分钟：秒）
     */
    private String duration;
    /**
     * 正在观看
     */
    private int is_pay;
    private int is_union_video;
}