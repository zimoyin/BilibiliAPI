/**
 * Copyright 2022 json.cn
 */
package github.zimoyin.bili.message.pojo.group;

import lombok.Data;

/**
 * Auto-generated: 2022-08-17 10:52:23
 *
 * @author json.cn (i@json.cn)
 * @website http://www.json.cn/java2pojo/
 */
@Data
public class Last_msg {

    /**
     * 发生者ID
     */
    private long sender_uid;
    /**
     * 接收者ID
     */
    private int receiver_type;
    private long receiver_id;
    /**
     * 信息类型
     * 1:发送文字
     * 2:发送图片
     * 5:撤回消息
     */
    private int msg_type;
    /**
     * 信息：JSON格式，自己看着解析
     */
    private String content;
    private long msg_seqno;
    /**
     * 发送时间
     */
    private long timestamp;
    private String at_uids;
    /**
     * 信息的key
     */
    private long msg_key;
    /**
     * 信息状态：未读等
     */
    private int msg_status;
    private String notify_code;
    private int new_face_version;
}