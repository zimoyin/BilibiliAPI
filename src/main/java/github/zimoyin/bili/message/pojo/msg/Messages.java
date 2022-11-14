/**
  * Copyright 2022 json.cn 
  */
package github.zimoyin.bili.message.pojo.msg;
import lombok.Data;

import java.util.List;

/**
 * Auto-generated: 2022-08-17 10:0:8
 *
 * @author json.cn (i@json.cn)
 * @website http://www.json.cn/java2pojo/
 */
@Data
public class Messages {

    /**
     * 发送者uid
     */
    private long sender_uid;
    /**
     * 1为用户，2为粉丝团
     */
    private int receiver_type;
    /**
     * 	接收者uid
     */
    private int receiver_id;
    /**
     * 消息类型
     * 1:文字消息
     * 2:图片消息
     * 5:撤回的消息
     * 12、13:通知
     */
    private int msg_type;
    /**
     * 消息内容	此处存在设计缺陷
     */
    private String content;
    private long msg_seqno;
    /**
     * 消息发送时间戳
     */
    private long timestamp;
    private List<Integer> at_uids;
    private long msg_key;
    /**
     * 消息状态
     */
    private int msg_status;
    private String notify_code;
    /**
     * 疑似只在粉丝团消息中出现
     */
    private int new_face_version;
}