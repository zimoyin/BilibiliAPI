/**
  * Copyright 2022 json.cn 
  */
package github.zimoyin.core.fanju.pojo.info.seriesI;
import lombok.Data;

import java.util.Date;

/**
 * Auto-generated: 2022-07-31 20:29:51
 *
 * @author json.cn (i@json.cn)
 * @website http://www.json.cn/java2pojo/
 */
@Data
public class Publish {

    /**
     * 完结状态
     * 0：未完结
     * 1：已完结
     */
    private int is_finish;
    /**
     * 是否发布
     * 0：未发布
     * 1：已发布
     */
    private int is_started;
    /**
     * 发布时间	格式：YYYY-MM-DDD hh:mm:ss
     */
    private Date pub_time;
    /**
     * 发布时间文字介绍
     */
    private String pub_time_show;
    private int unknow_pub_date;
    private int weekday;

}