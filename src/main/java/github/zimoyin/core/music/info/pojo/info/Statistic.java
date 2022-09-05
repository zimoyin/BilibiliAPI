/**
  * Copyright 2022 json.cn 
  */
package github.zimoyin.core.music.info.pojo.info;

import lombok.Data;

/**
 * Auto-generated: 2022-08-30 10:37:33
 *
 * @author json.cn (i@json.cn)
 * @website http://www.json.cn/java2pojo/
 */
@Data
public class Statistic {
    /**
     * 音频auid
     */
    private int sid;
    /**
     * 播放次数
     */
    private long play;
    /**
     * 收藏数
     */
    private int collect;
    /**
     * 评论数
     */
    private int comment;
    /**
     * 分享数
     */
    private int share;
}