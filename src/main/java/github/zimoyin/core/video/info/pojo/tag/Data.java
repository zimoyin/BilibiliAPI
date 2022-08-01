/**
  * Copyright 2022 json.cn 
  */
package github.zimoyin.core.video.info.pojo.tag;

import java.net.URL;

/**
 * Auto-generated: 2022-07-18 18:48:58
 *
 * @author json.cn (i@json.cn)
 * @website http://www.json.cn/java2pojo/
 */
@lombok.Data
public class Data {

    /**
     * tag_id
     */
    private long tag_id;
    /**
     * TAG名称
     */
    private String tag_name;
    /**
     * TAG图片url
     */
    private URL cover;
    /**
     * TAG页面头图url
     */
    private URL head_cover;
    /**
     * TAG介绍
     */
    private String content;
    /**
     * TAG简介
     */
    private String short_content;
    /**
     * 未知
     */
    private int type;
    /**
     * 固定值：0
     */
    private int state;
    /**
     * 创建时间	时间戳
     */
    private long ctime;
    /**
     * 状态数
     */
    private Count count;
    /**
     * 是否关注
     * 0：未关注
     * 1：已关注
     * 需要登录(Cookie)
     * 未登录为0
     */
    private int is_atten;
    /**
     * 未知
     */
    private int likes;
    /**
     * 未知
     */
    private int hates;
    /**
     * 未知
     */
    private int attribute;
    /**
     * 是否已经点赞
     * 0：未点赞
     * 1：已点赞
     * 需要登录(Cookie)
     * 未登录为0
     */
    private int liked;
    /**
     * 是否已经点踩
     * 0：未点踩
     * 1：已点踩
     * 需要登录(Cookie)
     * 未登录为0
     */
    private int hated;
    /**
     * 未知
     */
    private int extra_attr;

}