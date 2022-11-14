/**
  * Copyright 2022 json.cn 
  */
package github.zimoyin.bili.collection.pojo.collection;

import lombok.Data;

import java.util.List;

/**
 * Auto-generated: 2022-08-29 22:30:20
 *
 * @author json.cn (i@json.cn)
 * @website http://www.json.cn/java2pojo/
 */
@Data
public class Meta {

    private int category;
    /**
     * 封面
     */
    private String cover;
    /**
     * 合集简介
     */
    private String description;
    /**
     * up的mid
     */
    private long mid;
    /**
     * 合集的名称
     */
    private String name;
    /**
     * 发布日期
     */
    private long ptime;
    /**
     * sid
     */
    private long season_id;
    /**
     * 合集稿件数量
     */
    private int total;

    private String creator;
    /**
     * 上传日期
     */
    private long ctime;
    private List<String> keywords;
    private long last_update_ts;
    private String raw_keywords;
    private int series_id;
    private int state;
}