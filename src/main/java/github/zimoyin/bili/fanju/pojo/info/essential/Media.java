/**
  * Copyright 2022 json.cn 
  */
package github.zimoyin.bili.fanju.pojo.info.essential;
import lombok.Data;

import java.util.List;

/**
 * Auto-generated: 2022-07-31 20:6:22
 *
 * @author json.cn (i@json.cn)
 * @website http://www.json.cn/java2pojo/
 */
@Data
public class Media {

    /**
     * 地区
     */
    private List<Areas> areas;
    /**
     * 封面图片url
     */
    private String cover;
    /**
     * 剧集mdid
     */
    private long media_id;
    /**
     * 最新一话信息
     */
    private New_ep new_ep;
    /**
     * 评分信息
     */
    private Rating rating;
    /**
     * 剧集ssid
     */
    private int season_id;
    /**
     * 剧集详情页连接
     */
    private String share_url;
    /**
     * 标题
     */
    private String title;
    /**
     * 剧集类型	番剧、国创、电影等等
     */
    private String type_name;

}