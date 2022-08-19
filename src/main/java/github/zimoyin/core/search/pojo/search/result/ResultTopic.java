package github.zimoyin.core.search.pojo.search.result;

import lombok.Data;

import java.util.List;

/**
 * Auto-generated: 2022-08-19 20:40:59
 *
 * @author json.cn (i@json.cn)
 * @website http://www.json.cn/java2pojo/
 */
@Data
public class ResultTopic {
    /**
     * 固定为topic
     */
    private String type;
    /**
     * 简介
     */
    private String description;
    /**
     * 发布时间	时间戳
     */
    private long pubdate;
    /**
     * 标题
     */
    private String title;
    private int favourite;
    /**
     * 关键字匹配类型
     */
    private List<String> hit_columns;
    private int review;
    /**
     * 	搜索结果排名值
     */
    private int rank_offset;
    /**
     * 话题封面url
     */
    private String cover;
    /**
     * 上传时间	时间戳
     */
    private long update;
    private int mid;
    private long click;
    private int tp_type;
    private String keyword;
    /**
     * 话题tp
     */
    private int tp_id;
    private int rank_index;
    /**
     * UP主昵称
     */
    private String author;
    /**
     * 话题页面重定向url
     */
    private String arcurl;
    /**
     * 结果排序量化值
     */
    private long rank_score;
}