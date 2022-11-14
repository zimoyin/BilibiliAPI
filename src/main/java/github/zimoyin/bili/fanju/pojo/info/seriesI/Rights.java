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
public class Rights {

    private int allow_demand;
    private int allow_dm;
    private int allow_download;
    private int area_limit;
    /**
     * 版权标志
     * bilibili：授权
     * dujia：独家
     */
    private String copyright;

}