/**
  * Copyright 2022 json.cn 
  */
package github.zimoyin.core.message.pojo.group;

import lombok.Data;

/**
 * Auto-generated: 2022-08-17 10:52:23
 *
 * @author json.cn (i@json.cn)
 * @website http://www.json.cn/java2pojo/
 */
@Data
public class Session_list {
    /**
     * 会话ID与对面人的mid
     */
    private long talker_id;
    /**
     * 会话类型
     */
    private int session_type;
    private int at_seqno;
    /**
     * 置顶
     */
    private int top_ts;
    private String group_name;
    private String group_cover;
    /**
     * 是否关注
     * 0 ；未关注
     * 1: 关注
     */
    private int is_follow;
    private int is_dnd;
    private long ack_seqno;
    private long ack_ts;
    private long session_ts;
    private int unread_count;
    /**
     * 最后一次聊天信息
     */
    private Last_msg last_msg;
    private int group_type;
    private int can_fold;
    private int status;
    private long max_seqno;
    private int new_push_msg;
    private int setting;
    private int is_guardian;
    private int is_intercept;
    private int is_trust;
    private int system_msg_type;
    private int live_status;
    private int biz_msg_unread_count;
    private String user_label;
}