/**
  * Copyright 2022 json.cn 
  */
package github.zimoyin.bili.video.info.pojo.info.data.staff;

import lombok.Data;

/**
 * Auto-generated: 2022-07-16 13:53:55
 *
 * @author json.cn (i@json.cn)
 * @website http://www.json.cn/java2pojo/
 * 合作成员认证
 */
@Data
public class Official {

    /**
     * 成员认证级别	<br>
     * 0：无<br>
     * 1 2 7：个人认证<br>
     * 3 4 5 6：机构认证<br>
     */
    private int role;
    /**
     * 成员认证名:无为空
     */
    private String title;
    /**
     * 成员认证备注:无为空
     */
    private String desc;
    /**
     * 成员认证类型	<br>
     * -1：无<br>
     * 0：有<br>
     */
    private int type;
}