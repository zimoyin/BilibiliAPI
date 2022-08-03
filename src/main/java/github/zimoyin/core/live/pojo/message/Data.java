/**
  * Copyright 2022 json.cn 
  */
package github.zimoyin.core.live.pojo.message;
import java.util.List;

/**
 * Auto-generated: 2022-08-03 12:0:40
 *
 * @author json.cn (i@json.cn)
 * @website http://www.json.cn/java2pojo/
 */
@lombok.Data
public class Data {

    private Contribution contribution;
    private int dmscore;
    private Fans_medal fans_medal;
    private List<Integer> identities;
    private int is_spread;
    /**
     * 1为进场，2为关注
     */
    private int msg_type;
    /**
     * 房间号
     */
    private long roomid;
    private long score;
    private String spread_desc;
    private String spread_info;
    private int tail_icon;
    /**
     * 时间戳
     */
    private long timestamp;
    /**
     * 触发时间
     */
    private long trigger_time;
    /**
     * 用户ID
     */
    private long uid;
    /**
     * 用户名称
     */
    private String uname;
    /**
     * 用户颜色
     */
    private String uname_color;



}