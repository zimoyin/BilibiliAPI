package github.zimoyin.core.comment.operation;

import github.zimoyin.core.comment.enums.CommentType;
import github.zimoyin.core.cookie.Cookie;
import lombok.Data;

@Data
public abstract class CommentOperation {
    public static final String URL_publish ="http://api.bilibili.com/x/v2/reply/add";
    public static final String URL_like ="http://api.bilibili.com/x/v2/reply/action";
    public static final String URL_dislike ="http://api.bilibili.com/x/v2/reply/hate";
    public static final String URL_delete ="http://api.bilibili.com/x/v2/reply/del";
    public static final String URL_top ="http://api.bilibili.com/x/v2/reply/top";
    public static final String URL_hit ="http://api.bilibili.com/x/v2/reply/report";

    protected  Cookie cookie;

    public CommentOperation(Cookie cookie) {
        this.cookie = cookie;
    }
}
