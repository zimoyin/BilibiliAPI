/**
  * Copyright 2022 json.cn 
  */
package github.zimoyin.bili.search.pojo.search.result;
import lombok.Data;

import java.util.List;

/**
 * Auto-generated: 2022-08-19 20:32:30
 *
 * @author json.cn (i@json.cn)
 * @website http://www.json.cn/java2pojo/
 */
@Data
public class ResultColumn {
    /**
     * 固定为article
     */
    private String type;
    /**
     * 投稿时间	时间戳
     */
    private long pub_time;
    /**
     * 获赞数
     */
    private int like;
    /**
     * 	标题
     */
    private String title;
    /**
     * 搜索结果排名值
     */
    private int rank_offset;
    /**
     * UP主mid
     */
    private long mid;
    /**
     * 封面图组
     */
    private List<String> image_urls;
    private List<String> covers;
    private int template_id;
    /**
     * 专栏分区
     */
    private int category_id;
    /**
     * 阅读数
     */
    private int view;
    /**
     * 评论数
     */
    private int reply;
    private int rank_index;
    /**
     * 文章预览
     */
    private String desc;
    /**
     * 结果排序量化值
     */
    private long rank_score;
    /**
     * 结果排序量化值
     */
    private long id;
    /**
     * 子分区名
     */
    private String category_name;
}