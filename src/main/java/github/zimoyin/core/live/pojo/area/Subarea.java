/**
  * Copyright 2022 json.cn 
  */
package github.zimoyin.core.live.pojo.area;

import lombok.Data;

/**
 * Auto-generated: 2022-08-03 17:26:38
 *
 * @author json.cn (i@json.cn)
 * @website http://www.json.cn/java2pojo/
 */
@Data
public class Subarea {

    /**
     * 子分区id
     */
    private String id;
    /**
     * 父分区id
     */
    private String parent_id;
    /**
     * 旧分区id
     */
    private String old_area_id;
    /**
     * 子分区名
     */
    private String name;
    private String act_id;
    private String pk_status;
    /**
     * 是否为热门分区
     * 0：否
     * 1：是
     */
    private int hot_status;
    private String lock_status;
    /**
     * 子分区标志图片url
     */
    private String pic;
    private String complex_area_name;
    /**
     * 父分区名
     */
    private String parent_name;
    private int area_type;
}