package github.zimoyin.bili.comment.pojo.area.lazy;

import lombok.Data;

/**
 * 对话树：对话楼层信息
 */
@Data
public class Dialog {
    /**
     * 二级评论最低对话楼层
     */
    private int min_floor;
    /**
     * 二级评论最高对话楼层
     */
    private int max_floor;
}
