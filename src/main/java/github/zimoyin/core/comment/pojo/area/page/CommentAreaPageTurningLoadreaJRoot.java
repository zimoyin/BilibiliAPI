/**
  * Copyright 2022 json.cn 
  */
package github.zimoyin.core.comment.pojo.area.page;

import github.zimoyin.core.comment.pojo.table.Replies;

import java.util.List;

/**
 * Auto-generated: 2022-08-05 21:6:5
 *
 * @author json.cn (i@json.cn)
 * @website http://www.json.cn/java2pojo/
 */
@lombok.Data
public class CommentAreaPageTurningLoadreaJRoot {

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

    /**
     * 获取评论列表
     * @return
     */
    public List<Replies> getComments(){
        return data.getReplies();
    }
}