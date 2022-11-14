/**
  * Copyright 2022 json.cn 
  */
package github.zimoyin.bili.comment.pojo.table;

import github.zimoyin.bili.comment.pojo.area.page.*;
import github.zimoyin.bili.user.pojo.user.Nameplate;
import github.zimoyin.bili.user.pojo.user.Pendant;
import github.zimoyin.bili.user.pojo.user.Vip;
import github.zimoyin.bili.user.pojo.usercare.Level_info;
import github.zimoyin.bili.user.pojo.usercare.Official_verify;
import lombok.Data;

/**
 * Auto-generated: 2022-08-05 21:6:5
 *
 * @author json.cn (i@json.cn)
 * @website http://www.json.cn/java2pojo/
 */
@Data
public class Member {

    /**
     * 发送者 mid
     */
    private String mid;
    /**
     * 发送者昵称
     */
    private String uname;
    /**
     * 发送者性别	男 女 保密
     */
    private String sex;
    /**
     * 发送者签名
     */
    private String sign;
    /**
     * 	发送者头像 url
     */
    private String avatar;
    private String rank;
    private String DisplayRank;
    private int face_nft_new;
    private int is_senior_member;
    /**
     * 发送者等级
     */
    private Level_info level_info;
    /**
     * 发送者头像框信息
     */
    private Pendant pendant;
    /**
     * 发送者勋章信息
     */
    private Nameplate nameplate;
    /**
     * 发送者认证信息
     */
    private Official_verify official_verify;
    /**
     * 发送者会员信息
     */
    private Vip vip;
    /**
     * 发送者粉丝标签
     */
    private String fans_detail;
    /**
     * 是否关注该用户	需要登录(Cookie或APP)
     * 否则恒为0
     * 0：未关注
     * 1：已关注
     */
    private int following;
    /**
     * 是否关注该用户	需要登录(Cookie或APP)
     * 否则恒为0
     * 0：未关注
     * 1：已关注
     */
    private int is_followed;
    /**
     * 发送者评论条目装扮信息
     */
    private User_sailing user_sailing;
    /**
     * 是否为合作用户？
     */
    private boolean is_contractor;
    /**
     * 合作用户说明？
     */
    private String contract_desc;
    private String nft_interaction;
}