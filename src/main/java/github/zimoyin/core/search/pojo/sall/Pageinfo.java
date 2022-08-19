/**
  * Copyright 2022 json.cn 
  */
package github.zimoyin.core.search.pojo.sall;

import lombok.Data;

/**
 * Auto-generated: 2022-08-19 16:47:20
 *
 * @author json.cn (i@json.cn)
 * @website http://www.json.cn/java2pojo/
 */
@Data
public class Pageinfo {

    /**
     * 	直播
     */
    private Live_room live_room;
    private Pgc pgc;
    private Operation_card operation_card;
    private Tv tv;
    /**
     * 视频数
     */
    private Movie movie;
    /**
     * 用户数
     */
    private Bili_user bili_user;
    private Live_master live_master;
    private Live_all live_all;
    /**
     * 话题数
     */
    private Topic topic;
    private Upuser upuser;
    private Live live;
    private Video video;
    private User user;
    private Bangumi bangumi;
    /**
     * 专栏数
     */
    private Activity activity;
    /**
     * 电影数
     */
    private Media_ft media_ft;
    private Article article;
    /**
     * 番剧数
     */
    private Media_bangumi media_bangumi;
    private Special special;
    private Live_user live_user;
}