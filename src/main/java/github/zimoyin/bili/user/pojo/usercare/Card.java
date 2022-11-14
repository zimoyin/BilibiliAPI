/**
  * Copyright 2022 json.cn 
  */
package github.zimoyin.bili.user.pojo.usercare;
import github.zimoyin.bili.user.pojo.user.Pendant;
import github.zimoyin.bili.user.pojo.user.Vip;
import lombok.Data;

import java.util.List;

/**
 * Auto-generated: 2022-07-22 12:44:13
 *
 * @author json.cn (i@json.cn)
 * @website http://www.json.cn/java2pojo/
 */
@Data
public class Card {

    /**
     * 用户mid
     */
    private String mid;
    /**
     * 用户昵称
     */
    private String name;
    private boolean approve;
    /**
     * 用户性别	男 女 保密
     */
    private String sex;
    private String rank;
    /**
     * 用户头像链接
     */
    private String face;
    private int face_nft;
    private int face_nft_type;
    private String DisplayRank;
    private int regtime;
    private int spacesta;
    /**
     * 生日
     */
    private String birthday;
    private String place;
    private String description;
    private int article;
    private List<String> attentions;
    /**
     * 粉丝数
     */
    private int fans;
    /**
     * 关注数
     */
    private int friend;
    /**
     * 关注数
     */
    private int attention;
    /**
     * 签名
     */
    private String sign;
    /**
     * 等级
     */
    private Level_info level_info;
    /**
     * 挂件
     */
    private Pendant pendant;
    /**
     * 勋章
     */
    private Nameplate nameplate;
    /**
     * 认证信息
     */
    private github.zimoyin.bili.user.pojo.user.Official Official;
    /**
     * 认证信息2
     */
    private Official_verify official_verify;
    /**
     * 大会员状态
     */
    private Vip vip;
    private int is_senior_member;

}