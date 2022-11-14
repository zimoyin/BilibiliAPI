/**
  * Copyright 2022 json.cn 
  */
package github.zimoyin.bili.search.pojo.search.result.live;
import lombok.Data;

import java.util.List;
import java.util.Date;

/**
 * Auto-generated: 2022-08-19 19:58:27
 *
 * @author json.cn (i@json.cn)
 * @website http://www.json.cn/java2pojo/
 */
@Data
public class LiveRoom{
    /**
     * 结果类型	固定为live_room
     */
    private String type;
    private int area;
    /**
     * 主播粉丝数
     */
    private long attentions;
    /**
     * 子分区名
     */
    private String cate_name;
    /**
     * 关键帧截图url
     */
    private String cover;
    /**
     * 关键字匹配类型
     */
    private List<String> hit_columns;

    private int is_live_room_inline;
    private int live_status;
    /**
     * 开播时间	YYYY-MM-DD HH:MM:SS
     */
    private Date live_time;
    /**
     * 在线人数
     */
    private long online;
    private int rank_index;
    /**
     * 搜索结果排名值
     */
    private int rank_offset;
    /**
     * 结果排序量化值
     */
    private long rank_score;
    /**
     * 直播间id
     */
    private long roomid;
    private int short_id;
    /**
     * 直播间TAG	多个用,分隔
     */
    private String tags;
    /**
     * 直播间标题	关键字用xml标签<em class="keyword">标注
     */
    private String title;
    /**
     * 主播头像url
     */
    private String uface;
    /**
     * 主播mid
     */
    private long uid;
    /**
     * 	主播昵称
     */
    private String uname;
    /**
     * 直播间封面url
     */
    private String user_cover;
    private Watched_show watched_show;


}