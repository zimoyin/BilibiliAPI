/**
  * Copyright 2022 json.cn 
  */
package github.zimoyin.bili.user.pojo.usercare;

import lombok.Data;

/**
 * Auto-generated: 2022-07-22 12:44:13
 *
 * @author json.cn (i@json.cn)
 * @website http://www.json.cn/java2pojo/
 */
@Data
public class Official_verify {

    /**
     * 是否认证
     * -1：无
     * 0：认证
     */
    private int type;
    /**
     * 认证信息	无为空
     */
    private String desc;

}