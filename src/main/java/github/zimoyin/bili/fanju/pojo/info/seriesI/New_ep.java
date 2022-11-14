/**
  * Copyright 2022 json.cn 
  */
package github.zimoyin.bili.fanju.pojo.info.seriesI;

import lombok.Data;

/**
 * Auto-generated: 2022-07-31 20:29:51
 *
 * @author json.cn (i@json.cn)
 * @website http://www.json.cn/java2pojo/
 */
@Data
public class New_ep {

    private String cover;
    /**
     * 最新一话epid
     */
    private long id;
    private String index_show;
    /**
     * 更新备注
     */
    private String desc;

    /**
     * 是否最新发布
     * 0：否
     * 1：是
     */
    private int is_new;
    /**
     * 最新一话标题
     */
    private String title;
}