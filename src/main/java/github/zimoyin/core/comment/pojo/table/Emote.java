/**
  * Copyright 2022 json.cn 
  */
package github.zimoyin.core.comment.pojo.table;

import lombok.Data;

/**
 * Auto-generated: 2022-08-05 21:6:5
 *
 * @author json.cn (i@json.cn)
 * @website http://www.json.cn/java2pojo/
 */
@Data
public class Emote {

    /**
     * 表情 id
     */
    private int id;
    /**
     * 表情包 id
     */
    private int package_id;
    private int state;
    /**
     * 	表情类型
     * 	1：免费
     * 2：会员专属
     * 3：购买所得
     * 4：颜文字
     */
    private int type;
    private int attr;
    /**
     * 表情创建时间	时间戳
     */
    private long mtime;
    /**
     * 表情转义符
     */
    private String text;
    /**
     * 表情图片 url
     */
    private String url;
    /**
     * 属性信息
     */
    private Meta meta;
    /**
     * 表情名称
     */
    private String jump_title;


}