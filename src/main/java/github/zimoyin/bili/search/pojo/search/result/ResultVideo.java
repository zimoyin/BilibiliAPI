package github.zimoyin.bili.search.pojo.search.result;

import github.zimoyin.bili.search.pojo.search.PageInfo;
import lombok.Data;

import java.util.List;

/**
 * 对象类型1-结果为视频
 */
@Data
public class ResultVideo extends PageInfo {
    /**
     * 结果类型	固定为 video
     */
    private String type;
    /**
     * 结果	为稿件avid
     */
    private long id;
    /**
     * UP主昵称
     */
    private String author;
    /**
     * UP主mid
     */
    private long mid;
    /**
     * 视频分区tid
     */
    private String typeid;
    /**
     * 视频子分区名
     */
    private String typename;
    /**
     * 视频重定向url
     */
    private String arcurl;
    /**
     * 稿件avid
     */
    private long aid;
    /**
     * 稿件bvid
     */
    private String bvid;
    /**
     * 视频标题	关键字用xml标签<em class="keyword">标注
     */
    private String title;
    /**
     * 视频简介
     */
    private String description;
    private String arcrank;
    /**
     * 视频封面url
     */
    private String pic;
    /**
     * 视频播放量
     */
    private long play;
    /**
     * 视频弹幕量
     */
    private int video_review;
    /**
     * 视频收藏数
     */
    private int favorites;
    /**
     * 视频TAG	每项TAG用,分隔
     */
    private String tag;
    /**
     * 视频评论数
     */
    private int review;
    /**
     * 视频投稿时间	时间戳
     */
    private long pubdate;
    /**
     * 视频发布时间	时间戳
     */
    private long senddate;
    /**
     * 视频时长	HH:MM
     */
    private String duration;
    private boolean badgepay;
    /**
     * 关键字匹配类型
     */
    private List<String> hit_columns;
    private String view_type;
    private int is_pay;
    /**
     * 对象1：是否为合作视频	0：否,1：是
     */
    private int is_union_video;
    private String rec_tags;
    private List<String> new_rec_tags;
    private int like;
    private String upic;
    private String url;
    private String rec_reason;
    private int danmaku;
}
