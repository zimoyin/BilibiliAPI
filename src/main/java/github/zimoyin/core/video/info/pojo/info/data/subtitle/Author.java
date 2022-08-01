/**
  * Copyright 2022 json.cn 
  */
package github.zimoyin.core.video.info.pojo.info.data.subtitle;

import lombok.Data;

import java.net.URL;

/**
 * Auto-generated: 2022-07-16 13:44:12
 *
 * @author json.cn (i@json.cn)
 * @website http://www.json.cn/java2pojo/
 * CC字幕作者
 */
@Data
public class Author {

    /**
     * 字幕上传者mid
     */
    private int mid;
    /**
     * 字幕上传者昵称
     */
    private String name;
    /**
     * 字幕上传者性别:男 /女/ 保密
     */
    private String sex;
    /**
     * 字幕上传者头像url
     */
    private URL face;
    /**
     * 字幕上传者签名
     */
    private String sign;
    /**
     * 作用尚不明确
     */
    private int rank;
    /**
     * 作用尚不明确
     */
    private int birthday;
    /**
     * 作用尚不明确
     */
    private int is_fake_account;
    /**
     * 作用尚不明确
     */
    private int is_deleted;
    /**
     * 作用尚不明确
     */
    private int in_reg_audit;
    /**
     * 作用尚不明确
     */
    private int is_senior_member;
}