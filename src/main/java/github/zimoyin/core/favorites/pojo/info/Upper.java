/**
  * Copyright 2022 json.cn 
  */
package github.zimoyin.core.favorites.pojo.info;

import lombok.Data;

/**
 * Auto-generated: 2022-08-26 15:26:48
 *
 * @author json.cn (i@json.cn)
 * @website http://www.json.cn/java2pojo/
 */
@Data
public class Upper {
    /**
     * 创建者mid
     */
    private long mid;
    /**
     * 创建者昵称
     */
    private String name;
    /**
     * 创建者头像url
     */
    private String face;
    /**
     * 是否已关注创建者
     */
    private boolean followed;
    /**
     * 会员类别
     * 0：无
     * 1：月大会员
     * 2：年度及以上大会员
     */
    private int vip_type;
    /**
     * 会员开通状态
     * 0：无
     * 1：有
     */
    private int vip_statue;
}