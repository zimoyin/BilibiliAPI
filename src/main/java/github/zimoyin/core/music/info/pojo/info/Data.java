/**
  * Copyright 2022 json.cn 
  */
package github.zimoyin.core.music.info.pojo.info;
import github.zimoyin.core.user.pojo.user.Vip;

import java.util.List;

/**
 * Auto-generated: 2022-08-30 10:37:33
 *
 * @author json.cn (i@json.cn)
 * @website http://www.json.cn/java2pojo/
 */
@lombok.Data
public class Data {
    /**
     * 音频auid
     */
    private int id;
    /**
     * UP主mid
     */
    private long uid;
    /**
     * UP主昵称
     */
    private String uname;
    /**
     * 作者名
     */
    private String author;
    /**
     * 歌曲标题
     */
    private String title;
    /**
     * 封面图片url
     */
    private String cover;
    /**
     * 	歌曲简介
     */
    private String intro;
    /**
     * 	lrc歌词url
     */
    private String lyric;
    private int crtype;
    /**
     * 歌曲时间长度	单位为秒
     */
    private int duration;
    /**
     * 歌曲发布时间	时间戳
     */
    private long passtime;
    /**
     * 当前请求时间	时间戳
     */
    private long curtime;
    /**
     * 关联稿件avid
     */
    private long aid;
    /**
     * 关联稿件bvid
     */
    private String bvid;
    /**
     * 关联视频cid
     */
    private long cid;
    private int msid;
    private int attr;
    private int limit;
    private int activityId;
    private String limitdesc;

    private String ctime;
    /**
     * 状态数
     */
    private Statistic statistic;
    /**
     * UP主会员状态
     */
    private Vip  vipInfo;
    /**
     * 歌曲所在的收藏夹mlid	需要登录(SESSDATA)
     */
    private List<String> collectIds;
    /**
     * 投币数
     */
    private int coin_num;
}