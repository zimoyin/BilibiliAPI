/**
  * Copyright 2022 json.cn 
  */
package github.zimoyin.core.live.pojo.barrage;
import java.util.Date;
import java.util.List;

/**
 * Auto-generated: 2022-08-04 18:55:21
 *
 * @author json.cn (i@json.cn)
 * @website http://www.json.cn/java2pojo/
 */
public class Barrage {

    /**
     * 弹幕信息
     */
    private String text;
    private int dm_type;
    /**
     * 发送该条弹幕的用户id
     */
    private long uid;
    /**
     * 用户昵称
     */
    private String nickname;
    /**
     * 用户昵称颜色(可能通过颜色区分上舰)
     */
    private String uname_color;
    /**
     * 日期
     */
    private Date timeline;
    /**
     * 是否是房间管理员
     */
    private int isadmin;
    private int vip;
    private int svip;
    /**
     * 该用户佩戴的徽章信息[徽章等级,徽章名称，还有其他的]
     */
    private List<Integer> medal;
    private List<String> title;
    /**
     * 用户等级数据
     */
    private List<Integer> user_level;
    /**
     * 高能用户排名，10000开外不统计
     */
    private int rank;
    private int teamid;
    private String rnd;
    private String user_title;
    private int guard_level;
    private int bubble;
    private String bubble_color;
    private int lpl;
    private String yeah_space_url;
    private String jump_to_url;
    private Check_info check_info;
    private Voice_dm_info voice_dm_info;
    private Emoticon emoticon;

}