/**
  * Copyright 2022 json.cn 
  */
package github.zimoyin.core.comment.pojo.area.lazy;

/**
 * Auto-generated: 2022-08-06 17:51:5
 *
 * @author json.cn (i@json.cn)
 * @website http://www.json.cn/java2pojo/
 */
@lombok.Data
public class CommentAreaLazyLoadJsonRoot {

    /**
     * 0：成功
     * -400：请求错误
     * -404：无此项
     * 12002：评论区已关闭
     * 12009：评论主体的type不合法
     */
    private int code;
    private String message;
    private int ttl;
    private Data data;
}