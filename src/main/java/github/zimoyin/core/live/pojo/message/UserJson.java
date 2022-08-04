/**
  * Copyright 2022 json.cn 
  */
package github.zimoyin.core.live.pojo.message;

import lombok.Data;

/**
 * 高能用户
 */
@Data
public class UserJson {
    /**
     * uid
     */
    private long uid;
    /**
     * 头像url
     */
    private String face;
    private String score;
    /**
     * 昵称
     */
    private String uname;
    /**
     * 排名
     */
    private int rank;
    private int guard_level;
}