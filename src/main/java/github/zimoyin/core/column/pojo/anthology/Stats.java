/**
  * Copyright 2022 json.cn 
  */
package github.zimoyin.core.column.pojo.anthology;

import lombok.Data;

/**
 * Auto-generated: 2022-08-28 18:54:44
 *
 * @author json.cn (i@json.cn)
 * @website http://www.json.cn/java2pojo/
 */
@Data
public class Stats {
    /**
     * 阅读数
     */
    private int view;
    /**
     * 收藏数
     */
    private int favorite;
    /**
     * 点赞数
     */
    private int like;
    /**
     * 点踩数
     */
    private int dislike;
    /**
     * 评论数
     */
    private int reply;
    /**
     * 分享数
     */
    private int share;
    /**
     * 投币数
     */
    private int coin;
    /**
     * 动态转发数
     */
    private int dynamic;
}