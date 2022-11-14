/**
  * Copyright 2022 json.cn 
  */
package github.zimoyin.bili.user.pojo.search;
import lombok.Data;

import java.util.List;

/**
 * Auto-generated: 2022-07-19 12:23:43
 *
 * @author json.cn (i@json.cn)
 * @website http://www.json.cn/java2pojo/
 */
@Data
public class Result {

    /**
     * 类型
     */
    private String type;
    /**
     * 用户的mid，对用户操作都应用这个
     */
    private long mid;
    /**
     * 用户名
     */
    private String uname;
    /**
     * 签名
     */
    private String usign;
    /**
     * 粉丝数
     */
    private int fans;
    /**
     * 视频数
     */
    private int videos;
    /**
     * 头像URL
     */
    private String upic;
    /**
     * 未知
     */
    private int face_nft;
    /**
     * 未知
     */
    private int face_nft_type;
    /**
     * 未知
     */
    private String verify_info;
    /**
     * b站登记
     */
    private int level;
    /**
     * 未知
     */
    private int gender;
    /**
     * 是否为UP
     */
    private int is_upuser;
    /**
     * 是否直播
     */
    private int is_live;
    /**
     * 直播间ID
     */
    private long room_id;
    /**
     * 视频等稿件信息
     */
    private List<Res> res;
    /**
     * 未知
     */
    private Official_verify official_verify;
    /**
     * 未知
     */
    private List<String> hit_columns;

}