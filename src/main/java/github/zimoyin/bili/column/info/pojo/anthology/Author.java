/**
  * Copyright 2022 json.cn 
  */
package github.zimoyin.bili.column.info.pojo.anthology;

import github.zimoyin.bili.user.pojo.user.Nameplate;
import github.zimoyin.bili.user.pojo.user.Official;
import github.zimoyin.bili.user.pojo.user.Pendant;
import github.zimoyin.bili.user.pojo.user.Vip;
import lombok.Data;

/**
 * Auto-generated: 2022-08-28 18:54:44
 *
 * @author json.cn (i@json.cn)
 * @website http://www.json.cn/java2pojo/
 */
@Data
public class Author {
    /**
     * 作者mid
     */
    private long mid;
    /**
     * 作者昵称
     */
    private String name;
    /**
     * 作者头像url
     */
    private String face;

    /**
     * 头像框
     */
    private Pendant pendant;
    /**
     * 作者认证信息
     */
    private Official official_verify;
    /**
     * 作者勋章
     */
    private Nameplate nameplate;
    /**
     * 作者大会员状态
     */
    private Vip vip;
}