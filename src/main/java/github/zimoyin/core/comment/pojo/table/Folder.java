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
public class Folder {

    /**
     * 是否有被折叠的二级评论
     */
    private boolean has_folded;
    /**
     * 评论是否被折叠
     */
    private boolean is_folded;
    /**
     * 相关规则页面 url
     */
    private String rule;
}