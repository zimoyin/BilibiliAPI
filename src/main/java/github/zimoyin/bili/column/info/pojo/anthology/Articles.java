/**
  * Copyright 2022 json.cn 
  */
package github.zimoyin.bili.column.info.pojo.anthology;
import lombok.Data;

import java.util.List;

/**
 * Auto-generated: 2022-08-28 18:54:44
 *
 * @author json.cn (i@json.cn)
 * @website http://www.json.cn/java2pojo/
 */
@Data
public class Articles {

    /**
     * 	专栏cvid
     */
    private long id;
    /**
     * 文章标题
     */
    private String title;
    private int state;
    /**
     * 发布时间	秒时间戳
     */
    private long publish_time;
    /**
     * 文章字数
     */
    private int words;
    /**
     * 文章封面
     */
    private List<String> image_urls;
    /**
     * 文章标签
     */
    private Category category;
    /**
     * 文章标签列表
     */
    private List<Categories> categories;
    /**
     * 文章摘要
     */
    private String summary;
    /**
     * 文章状态数信息
     */
    private Stats stats;
    /**
     * 是否点赞
     * 0：未点赞
     * 1：已点赞
     * 需要登录(Cookie)
     * 未登录为0
     */
    private int like_state;
}