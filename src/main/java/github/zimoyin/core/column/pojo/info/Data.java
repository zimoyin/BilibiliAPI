/**
  * Copyright 2022 json.cn 
  */
package github.zimoyin.core.column.pojo.info;
import java.util.List;

/**
 * Auto-generated: 2022-08-27 14:7:18
 *
 * @author json.cn (i@json.cn)
 * @website http://www.json.cn/java2pojo/
 */
@lombok.Data
public class Data {

    /**
     * 是否点赞
     * 0：未点赞
     * 1：已点赞
     * 需要登录(Cookie)
     * 未登录为0
     */
    private int like;
    /**
     * 是否关注文章作者
     * false：未关注
     * true：已关注
     * 需要登录(Cookie)
     * 未登录为false
     */
    private boolean attention;
    /**
     * 是否收藏
     * false：未收藏
     * true：已收藏
     * 需要登录(Cookie)
     * 未登录为false
     */
    private boolean favorite;
    /**
     * 投币数
     */
    private int coin;
    /**
     * 状态数信息
     */
    private Stats stats;
    /**
     * 文章标题
     */
    private String title;
    /**
     * 文章头图url
     */
    private String banner_url;
    /**
     * 	文章作者mid
     */
    private long mid;
    /**
     * 文章作者昵称
     */
    private String author_name;
    private boolean is_author;
    /**
     * 动态封面:动态封面图片url
     */
    private List<String> image_urls;
    /**
     * 封面图片:文章封面图片url
     */
    private List<String> origin_image_urls;
    private boolean shareable;
    private boolean show_later_watch;
    private boolean show_small_window;
    /**
     * 是否收于文集
     * false：否
     * true：是
     */
    private boolean in_list;
    /**
     * 上一篇文章cvid	无为0
     */
    private long pre;
    /**
     * 下一篇文章cvid	无为0
     */
    private long next;
    /**
     * 分享方式列表
     * 项	类型	内容
     * 0	obj	分享项：qq
     * 1	obj	分享项：qq空间
     * 2	obj	分享项：微信
     * 3	obj	分享项：朋友圈
     * 4	obj	分享项：微博
     */
    private List<Share_channels> share_channels;
    private int type;
    private String video_url;
    /**
     * 属地
     */
    private String location;
    private boolean disable_share;
}