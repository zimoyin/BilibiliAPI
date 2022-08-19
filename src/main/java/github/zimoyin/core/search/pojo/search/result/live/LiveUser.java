/**
  * Copyright 2022 json.cn 
  */
package github.zimoyin.core.search.pojo.search.result.live;
import lombok.Data;

import java.util.List;

/**
 * Auto-generated: 2022-08-19 19:58:27
 *
 * @author json.cn (i@json.cn)
 * @website http://www.json.cn/java2pojo/
 * 对象类型4-结果为主播
 */
@Data
public class LiveUser {
    /**
     * 固定为live_user
     */
    private String type;
    private int area;
    private int area_v2_id;
    /**
     * 主播粉丝数
     */
    private long attentions;
    private String cate_name;
    /**
     * 关键字匹配类型
     */
    private List<String> hit_columns;
    /**
     * 是否开播	false：未开播
     * true：已开播
     */
    private boolean is_live;
    /**
     * 是否开播	0：未开播
     * 1：已开播
     */
    private int live_status;
    /**
     * 开播时间	YYYY-MM-DD HH:MM:SS
     * 如未开播为0000-00-00 00:00:00
     */
    private String live_time;
    private int rank_index;
    /**
     * 搜索结果排名值
     */
    private int rank_offset;
    /**
     * 结果排序量化值
     */
    private long rank_score;
    private long roomid;
    /**
     * 直播间TAG	多个用,分隔
     */
    private String tags;
    /**
     * 主播头像url
     */
    private String uface;
    /**
     * 主播mid
     */
    private long uid;
    /**
     * 主播昵称	关键字用xml标签<em class="keyword">标注
     */
    private String uname;

}