/**
  * Copyright 2022 json.cn 
  */
package github.zimoyin.bili.search.pojo.search.result.user;
import lombok.Data;

import java.util.List;

/**
 * Auto-generated: 2022-08-19 20:44:23
 *
 * @author json.cn (i@json.cn)
 * @website http://www.json.cn/java2pojo/
 */
@Data
public class ResultUser {

    /**
     * 结果类型	固定为 bili_user
     */
    private String type;
    /**
     * 用户mid
     */
    private long mid;
    /**
     * 用户昵称
     */
    private String uname;
    /**
     * 用户签名
     */
    private String usign;
    /**
     * 用户粉丝数
     */
    private long fans;
    /**
     * 用户稿件数
     */
    private int videos;
    /**
     * 用户头像url
     */
    private String upic;
    private int face_nft;
    private int face_nft_type;
    private String verify_info;
    /**
     * 用户等级
     */
    private int level;
    /**
     * 用户性别
     * 1：男
     * 2：女
     * 3：私密
     */
    private int gender;
    /**
     * 是否为UP主
     * 0：否
     * 1：是
     */
    private int is_upuser;
    /**
     * 是否正在直播
     * 0：否
     * 1：是
     */
    private int is_live;
    /**
     * 用户直播间id
     */
    private long room_id;
    /**
     * 用户投稿内容
     */
    private List<Res> res;
    /**
     * 用户认证信息
     */
    private Official_verify official_verify;
    /**
     * 	关键字匹配类型
     */
    private List<String> hit_columns;
}