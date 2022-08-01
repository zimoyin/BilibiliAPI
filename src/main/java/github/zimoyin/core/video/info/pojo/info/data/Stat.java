/**
  * Copyright 2022 json.cn 
  */
package github.zimoyin.core.video.info.pojo.info.data;

import github.zimoyin.core.video.operation.VideoStatus;
import lombok.Data;

/**
 * Auto-generated: 2022-07-16 13:2:38
 *
 * @author json.cn (i@json.cn)
 * @website http://www.json.cn/java2pojo/
 * 视频状态数
 */
@Data
public class Stat {

    /**
     * 稿件avid
     */
    private long aid;
    /**
     * 播放数
     */
    private int view;
    /**
     * 弹幕数
     */
    private int danmaku;
    /**
     * 评论数
     */
    private int reply;
    /**
     * 收藏数
     */
    private int favorite;
    /**
     * 投币数
     */
    private long coin;
    /**
     * 分享数
     */
    private int share;
    /**
     * 当前排名
     */
    private int now_rank;
    /**
     * 历史最高排行
     */
    private int his_rank;
    /**
     * 获赞数
     */
    private int like;
    /**
     * 点踩数
     */
    private int dislike;
    /**
     * 视频评分
     */
    private String evaluation;
    /**
     * 警告/争议提示信息
     */
    private String argue_msg;

    @Override
    public String toString() {
        return "Stat{" +
                "aid=" + aid +
                ", 播放数=" + view +
                ", 弹幕数=" + danmaku +
                ", 评论数=" + reply +
                ", 收藏数=" + favorite +
                ", 投币数=" + coin +
                ", 分享数=" + share +
                ", 当前排名=" + now_rank +
                ", 历史最高排行=" + his_rank +
                ", 获赞数=" + like +
                ", 点踩数=" + dislike +
                ", 视频评分='" + evaluation + '\'' +
                ", 警告/争议提示信息='" + argue_msg + '\'' +
                '}';
    }
}