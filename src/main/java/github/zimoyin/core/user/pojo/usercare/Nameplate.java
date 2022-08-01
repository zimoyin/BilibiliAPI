/**
  * Copyright 2022 json.cn 
  */
package github.zimoyin.core.user.pojo.usercare;

import lombok.Data;

/**
 * Auto-generated: 2022-07-22 12:44:13
 *
 * @author json.cn (i@json.cn)
 * @website http://www.json.cn/java2pojo/
 */
@Data
public class Nameplate {

    /**
     * 勋章id
     */
    private int nid;
    /**
     * 勋章名称
     */
    private String name;
    /**
     * 挂件图片url 正常
     */
    private String image;
    /**
     * 勋章图片url 小
     */
    private String image_small;
    /**
     * 勋章等级
     */
    private String level;
    /**
     * 勋章条件
     */
    private String condition;
}