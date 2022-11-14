/**
  * Copyright 2022 json.cn 
  */
package github.zimoyin.bili.favorites.pojo.conter;

import github.zimoyin.bili.favorites.pojo.info.Cnt_info;
import github.zimoyin.bili.favorites.pojo.info.Upper;
import lombok.Data;

/**
 * Auto-generated: 2022-08-26 19:59:57
 *
 * @author json.cn (i@json.cn)
 * @website http://www.json.cn/java2pojo/
 */
@Data
public class Medias {

    /**
     * 内容id	视
     * 频稿件：视频稿件avid
     * 音频：音频auid
     * 视频合集：视频合集id
     */
    private long id;
    /**
     * 内容类型
     * 2：视频稿件
     * 12：音频
     * 21：视频合集
     */
    private int type;
    /**
     * 标题
     */
    private String title;
    /**
     * 封面url
     */
    private String cover;
    /**
     * 简介
     */
    private String intro;
    /**
     * 视频分P数
     */
    private int page;
    /**
     * 音频/视频时长
     */
    private int duration;
    /**
     * UP主信息
     */
    private Upper upper;
    /**
     * 属性位（？）
     */
    private int attr;
    /**
     * 状态数
     */
    private Cnt_info cnt_info;
    /**
     * 跳转uri
     */
    private String link;
    /**
     * 投稿时间	时间戳
     */
    private long ctime;
    /**
     * 发布时间	时间戳
     */
    private long pubtime;
    /**
     * 收藏时间	时间戳
     */
    private long fav_time;
    /**
     * 视频稿件bvid
     */
    private String bv_id;
    /**
     * 视频稿件bvid
     */
    private String bvid;
    private String season;
    private String ogv;
    private Ugc ugc;
}