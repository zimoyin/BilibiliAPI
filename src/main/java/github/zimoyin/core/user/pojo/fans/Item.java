/**
  * Copyright 2022 json.cn 
  */
package github.zimoyin.core.user.pojo.fans;

import github.zimoyin.core.user.pojo.user.Official;
import lombok.Data;

/**
 * Auto-generated: 2022-07-31 17:24:59
 *
 * @author json.cn (i@json.cn)
 * @website http://www.json.cn/java2pojo/
 */
@Data
public class Item {

    /**
     * 用户mid
     */
    private long mid;
    /**
     * 关注属性
     * 0：未关注
     * 2：已关注
     * 6：已互粉
     */
    private int attribute;
    /**
     * 成为粉丝时间	时间戳
     * 互关后刷新
     */
    private long mtime;
    private String tag;
    private int special;
    private Contract_info contract_info;
    /**
     * 用户昵称
     */
    private String uname;
    /**
     * 用户头像url
     */
    private String face;
    /**
     * 用户签名
     */
    private String sign;
    private int face_nft;
    /**
     * 认证信息
     */
    private Official official_verify;
    /**
     * 会员信息
     */
    private FansVip vip;
}