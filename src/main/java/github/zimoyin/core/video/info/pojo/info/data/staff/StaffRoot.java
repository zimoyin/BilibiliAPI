/**
  * Copyright 2022 json.cn 
  */
package github.zimoyin.core.video.info.pojo.info.data.staff;

import lombok.Data;

import java.net.URL;

/**
 * Auto-generated: 2022-07-16 13:53:55
 *
 * @author json.cn (i@json.cn)
 * @website http://www.json.cn/java2pojo/
 * 合作视频合作者成员信息
 */
@Data
public class StaffRoot {

    /**
     * 成员mid
     */
    private long mid;
    /**
     * 成员名称
     */
    private String title;
    /**
     * 成员昵称
     */
    private String name;
    /**
     * 成员头像url
     */
    private URL face;
    /**
     * 成员大会员状态
     */
    private Vip vip;
    /**
     * 成员认证信息
     */
    private Official official;
    /**
     * 成员粉丝数
     */
    private int follower;
    /**
     * 未知
     */
    private int label_style;
}