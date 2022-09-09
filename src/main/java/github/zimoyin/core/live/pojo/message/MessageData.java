/**
  * Copyright 2022 json.cn 
  */
package github.zimoyin.core.live.pojo.message;
import java.util.ArrayList;
import java.util.List;

/**
 * 直播信息
 * 注意直播信息是变化的不是固定的，所以有些值是无法是一直能被赋值的，请注意判断
 */
@lombok.Data
public class MessageData {

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

    /************************************************* 另一个命令包的字段 ***********************************************/
    /**
     * count 在线排名统计
     */
    private long count;
    /************************************************** 另一个命令包的字段 **********************************************/
    /**
     * 多少人看过
     */
    private long num;
    /************************************************** 另一个命令包的字段 **********************************************/
    /**
     * 子分区
     */
    private String area_name;

    /**
     * 在子分区中的热度排名
     */
    private String rank_desc;

    /**
     * 热度排名
     */
    private long rank;
    /************************************************** 另一个命令包的字段 **********************************************/
    /**
     * 粉丝数
     */
    private long fans;
    /************************************************** 另一个命令包的字段 **********************************************/
    /**
     * 礼物名称
     */
    private String giftName;
    private String giftType;
    /**
     * 礼物ID，请通过礼物表来进行对照
     */
    private int giftId;
    /**
     * 送礼方式
     */
    private String action;
    /************************************************** 另一个命令包的字段 **********************************************/
    /**
     * 停播列表
     */
    private ArrayList<Long> room_id_list;
    /************************************************** 另一个命令包的字段 **********************************************/
    /**
     * 高能用户列表
     */
    private ArrayList<UserJson> list;
}