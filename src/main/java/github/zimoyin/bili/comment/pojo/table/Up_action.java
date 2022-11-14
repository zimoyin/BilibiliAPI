/**
  * Copyright 2022 json.cn 
  */
package github.zimoyin.bili.comment.pojo.table;

import lombok.Data;

/**
 * Auto-generated: 2022-08-05 21:6:5
 *
 * @author json.cn (i@json.cn)
 * @website http://www.json.cn/java2pojo/
 */
@Data
public class Up_action {

    /**
     * 是否UP主觉得很赞
     * false：否
     * true：是
     */
    private boolean like;
    /**
     * 	是否被UP主回复
     * 	false：否
     * true：是
     */
    private boolean reply;
}