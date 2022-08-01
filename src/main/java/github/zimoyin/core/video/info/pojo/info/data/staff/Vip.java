/**
  * Copyright 2022 json.cn 
  */
package github.zimoyin.core.video.info.pojo.info.data.staff;

import github.zimoyin.core.video.info.pojo.info.data.Label;
import lombok.Data;

/**
 * Auto-generated: 2022-07-16 13:53:55
 *
 * @author json.cn (i@json.cn)
 * @website http://www.json.cn/java2pojo/
 * 合作成员VIP对象
 */
@Data
public class Vip {

    /**
     * 成员会员类型	<br>
     * 0：无<br>
     * 1：月会员<br>
     * 2：年会员<br>
     */
    private int type;
    /**
     * 会员状态<br>
     * 0：无<br>
     * 1：有<br>
     */
    private int status;
    private long due_date;
    private int vip_pay_type;
    private int theme_type;
    private Label label;
    private int avatar_subscript;
    private String nickname_color;
    private int role;
    private String avatar_subscript_url;
    private int tv_vip_status;
    private int tv_vip_pay_type;
}