/**
  * Copyright 2022 json.cn 
  */
package github.zimoyin.bili.comment.pojo.table;

import lombok.Data;

/**
 * Auto-generated: 2022-08-05 21:6:5
 *
 * @author json.cn (i@json.cn)
 * @website http://www.json.cn/java2pojo/
 */
@Data
public class Jump_url {
    /**
     * 标题
     */
    private String title;
    /**
     * 图标 url
     */
    private int state;
    private String prefix_icon;
    private String app_url_schema;
    private String app_name;
    private String app_package_name;
    private String click_report;
    private boolean is_half_screen;
    private String exposure_report;
    private Extra extra;
    private boolean underline;
    private boolean match_once;
    /**
     * 站内搜索链接
     */
    private String pc_url;
    private int icon_position;

}