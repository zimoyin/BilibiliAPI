/**
  * Copyright 2022 json.cn 
  */
package github.zimoyin.bili.user.pojo.fans;

import github.zimoyin.bili.user.pojo.user.Label;
import lombok.Data;

/**
 * Auto-generated: 2022-07-31 17:24:59
 *
 * @author json.cn (i@json.cn)
 * @website http://www.json.cn/java2pojo/
 */
@Data
public class FansVip {
    /**
     * 会员类型
     * 0：无
     * 1：月度大会员
     * 2：年度以上大会员
     */
    private int vipType;
    /**
     * 会员到期时间	时间戳 毫秒
     */
    private String vipDueDate;
    private String dueRemark;
    private int accessStatus;
    /**
     * 大会员状态
     * 0：无
     * 1：有
     */
    private int vipStatus;
    private String vipStatusWarn;
    private int themeType;
    private Label label;
    private int avatar_subscript;
    private String nickname_color;
    private String avatar_subscript_url;
}