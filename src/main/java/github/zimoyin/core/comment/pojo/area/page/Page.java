/**
  * Copyright 2022 json.cn 
  */
package github.zimoyin.core.comment.pojo.area.page;

import lombok.Data;

/**
 * Auto-generated: 2022-08-05 21:6:5
 *
 * @author json.cn (i@json.cn)
 * @website http://www.json.cn/java2pojo/
 */
@Data
public class Page {

    /**
     * 当前页码
     */
    private int num;
    /**
     * 每页项数
     */
    private int size;
    /**
     * 根评论条数
     */
    private int count;
    /**
     * 总计评论条数
     */
    private int acount;

}