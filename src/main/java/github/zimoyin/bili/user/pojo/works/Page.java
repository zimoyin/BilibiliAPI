/**
  * Copyright 2022 json.cn 
  */
package github.zimoyin.bili.user.pojo.works;

import lombok.Data;

/**
 * Auto-generated: 2022-07-19 13:12:49
 *
 * @author json.cn (i@json.cn)
 * @website http://www.json.cn/java2pojo/
 */
@Data
public class Page {

    /**
     * 当前页数
     */
    private int pn;
    /**
     * 可以容纳的作品数量
     */
    private int ps;
    /**
     * 作品数量
     */
    private int count;
}