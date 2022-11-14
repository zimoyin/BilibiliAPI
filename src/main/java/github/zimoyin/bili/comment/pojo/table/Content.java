/**
  * Copyright 2022 json.cn 
  */
package github.zimoyin.bili.comment.pojo.table;
import lombok.Data;

import java.util.HashMap;
import java.util.List;

/**
 * Auto-generated: 2022-08-05 21:6:5
 *
 * @author json.cn (i@json.cn)
 * @website http://www.json.cn/java2pojo/
 */
@Data
public class Content {

    /**
     * 评论内容
     */
    private String message;
    /**
     * 评论发送端
     * 1：web端
     * 2：安卓客户端
     * 3：ios 客户端
     * 4：wp 客户端
     */
    private int plat;
    /**
     * 评论发送平台设备
     */
    private String device;
    /**
     * at 到的用户信息
     */
    private List<String> members;
    /**
     * 需要渲染的表情转义	评论内容无表情则无此项
     */
    private HashMap<String,Emote> emote;

    /**
     * 需要高亮的超链转义(词条等)
     */
    private HashMap<String,Jump_url> jump_url;
    /**
     * 6	收起最大行数
     */
    private int max_line;
}