/**
  * Copyright 2022 json.cn 
  */
package github.zimoyin.core.message.pojo.msg;
import java.util.List;

/**
 * Auto-generated: 2022-08-17 10:0:8
 *
 * @author json.cn (i@json.cn)
 * @website http://www.json.cn/java2pojo/
 */
@lombok.Data
public class Data {

    /**
     * 聊天记录列表
     */
    private List<Messages> messages;
    private int has_more;
    private long min_seqno;
    private long max_seqno;
    /**
     * 聊天表情列表
     */
    private List<E_infos> e_infos;
}