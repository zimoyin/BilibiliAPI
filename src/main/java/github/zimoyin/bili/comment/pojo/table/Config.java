/**
  * Copyright 2022 json.cn 
  */
package github.zimoyin.bili.comment.pojo.table;

import lombok.Data;

/**
 * Auto-generated: 2022-08-05 21:6:5
 *
 * @author json.cn (i@json.cn)
 * @website http://www.json.cn/java2pojo/
 */
@Data
public class Config {

    /**
     * 是否显示话题
     */
    private int showtopic;
    /**
     * 是否显示楼层号
     */
    private int showfloor;
    private int showentry;
    /**
     * 是否显示管理置顶
     */
    private int showadmin;
    /**
     * 是否显示“UP 觉得很赞”标志
     */
    private boolean show_up_flag;
    /**
     * 是否只读评论区
     */
    private boolean read_only;
    /**
     * 是否显示删除记录
     */
    private boolean show_del_log;
}