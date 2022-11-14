package github.zimoyin.bili.comment.pojo.table;

import lombok.Data;

@Data
public class Notice {
    /**
     * 公告正文
     */
    private String str;
    /**
     * 公告 id
     */
    private long id;
    /**
     * 公告页面链接 url
     */
    private String link;
    /**
     * 公告标题
     */
    private String title;
}
