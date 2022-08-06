package github.zimoyin.core.comment.pojo.table;

import lombok.Data;

@Data
public class Meta {
    /**
     * 尺寸
     * 1:小
     * 2:大
     */
    private int size;
    /**
     * 简写名
     */
    private String alias;
}
