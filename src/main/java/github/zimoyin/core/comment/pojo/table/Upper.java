/**
  * Copyright 2022 json.cn 
  */
package github.zimoyin.core.comment.pojo.table;

import github.zimoyin.core.comment.pojo.table.Replies;
import lombok.Data;

/**
 * Auto-generated: 2022-08-05 21:6:5
 *
 * @author json.cn (i@json.cn)
 * @website http://www.json.cn/java2pojo/
 */
@Data
public class Upper {

    /**
     * UP 主 mid
     */
    private long mid;
    /**
     * 置顶条目
     */
    private Replies top;
    /**
     * 投票评论？
     */
    private String vote;
}