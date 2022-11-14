/**
  * Copyright 2022 json.cn 
  */
package github.zimoyin.bili.fanju.pojo.info.seriesI;

import lombok.Data;

/**
 * Auto-generated: 2022-07-31 20:29:51
 *
 * @author json.cn (i@json.cn)
 * @website http://www.json.cn/java2pojo/
 */
@Data
public class Up_info {

    /**
     * 头像图片url
     */
    private String avatar;
    private String avatar_subscript_url;
    /**
     * 粉丝数
     */
    private long follower;
    private int is_follow;
    /**
     * UP主mid
     */
    private long mid;
    private String nickname_color;
    private Pendant pendant;
    private int theme_type;
    /**
     * UP主昵称
     */
    private String uname;
    private int verify_type;
    private Vip_label vip_label;
    private int vip_status;
    private int vip_type;
}