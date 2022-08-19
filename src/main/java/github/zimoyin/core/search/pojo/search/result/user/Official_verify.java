/**
  * Copyright 2022 json.cn 
  */
package github.zimoyin.core.search.pojo.search.result.user;

import lombok.Data;

/**
 * Auto-generated: 2022-08-19 20:44:23
 *
 * @author json.cn (i@json.cn)
 * @website http://www.json.cn/java2pojo/
 */
@Data
public class Official_verify {

    /**
     * 是否认证
     * 127：无
     * 0：个人认证
     * 1：组织认证
     */
    private int type;
    /**
     * 认证名称
     */
    private String desc;
}