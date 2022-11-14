/**
  * Copyright 2022 json.cn 
  */
package github.zimoyin.bili.user.pojo.usercare;

import lombok.Data;

/**
 * Auto-generated: 2022-07-22 12:44:13
 *
 * @author json.cn (i@json.cn)
 * @website http://www.json.cn/java2pojo/
 */
@Data
public class Level_info {

    /**
     * 当前等级	0-6级
     */
    private int current_level;
    private int current_min;
    private int current_exp;
    private int next_exp;
}