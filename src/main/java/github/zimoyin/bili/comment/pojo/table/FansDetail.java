package github.zimoyin.bili.comment.pojo.table;

import lombok.Data;

@Data
public class FansDetail {
    /**
     * 用户 mid
     */
    private int uid;
    /**
     * 粉丝标签 id
     */
    private int medal_id;
    /**
     * 当前标签等级
     */
    private int level;
    /**
     * 粉丝标签名
     */
    private String medal_name;
}
