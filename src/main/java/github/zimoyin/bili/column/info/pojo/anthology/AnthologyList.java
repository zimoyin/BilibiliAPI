/**
  * Copyright 2022 json.cn 
  */
package github.zimoyin.bili.column.info.pojo.anthology;

import lombok.Data;

/**
 * Auto-generated: 2022-08-28 18:54:44
 *
 * @author json.cn (i@json.cn)
 * @website http://www.json.cn/java2pojo/
 */
@Data
public class AnthologyList {
    /**
     * 	文集rlid
     */
    private long id;
    /**
     * 文集作者mid
     */
    private long mid;
    /**
     * 文集名称
     */
    private String name;
    /**
     * 文集封面图片url
     */
    private String image_url;
    /**
     * 文集更新时间	时间戳
     */
    private long update_time;
    /**
     * 文集创建时间	时间戳
     */
    private long ctime;
    /**
     * 文集发布时间	时间戳
     */
    private long publish_time;
    /**
     * 文集简介
     */
    private String summary;
    /**
     * 文集字数
     */
    private long words;
    /**
     * 文集阅读量
     */
    private long read;
    /**
     * 文集内文章数量
     */
    private int articles_count;
    private int state;
    private String reason;
    private String apply_time;
    private String check_time;
}