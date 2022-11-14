/**
  * Copyright 2022 json.cn 
  */
package github.zimoyin.bili.comment.pojo.area.lazy;
import lombok.Data;

/**
 * Auto-generated: 2022-08-06 17:51:5
 *
 * @author json.cn (i@json.cn)
 * @website http://www.json.cn/java2pojo/
 */
@Data
public class Cursor {

    /**
     * 全部评论条数
     */
    private int all_count;
    /**
     * 是否为第一页
     * false：否
     * true：是
     */
    private boolean is_begin;
    /**
     * 上页页码
     */
    private int prev;
    /**
     * 下页页码
     */
    private int next;
    /**
     * 是否为最后页
     * false：否
     * true：是
     */
    private boolean is_end;
    /**
     * 排序方式
     */
    private int mode;
    private int show_type;
    /**
     * 支持的排序方式
     */
    private String support_mode;
    /**
     * 评论区类型名
     */
    private String name;


    /******************************************** 对话树 ****************************************************/
    /**
     * 对话树：本页最低对话楼层
     */
    private int min_floor;
    /**
     * 对话树：本页最高对话楼层
     */
    private int max_floor;
    /**
     * 对话树：本页项数
     */
    private int size;
}