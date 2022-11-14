package github.zimoyin.bili.search.pojo.search.result;

import lombok.Data;

import java.util.List;

/**
 * 对象类型2-结果为番剧&影视
 */
@Data
public class ResultMovie{

    /**
     * 结果类型	media_bangumi：番剧, media_ft：影视
     */
    private String type;
    /**
     * 视频标题	关键字用xml标签<em class="keyword">标注
     */
    private String title;
    /**
     * 简介
     */
    private String desc;
    private String arcrank;
    /**
     * 剧集重定向url
     */
    private String pic;
    private boolean badgepay;


    /**
     * 关键字匹配类型
     */
    private List<String> hit_columns;

    private String view_type;
    private int is_pay;
    private String rec_tags;
    private List<String> new_rec_tags;



    /**
     * 对象2:结果排序量化值
     */
    private long rank_score;
    private int like;
    private String upic;
    /**
     * 角标有无	2：无，13：有
     */
    private String corner;
    /**
     * 对象2:剧集封面url
     */
    private String cover;
    private String url;
    private String rec_reason;
    private int danmaku;
    /**
     * 对象2:剧集mdid
     */
    private int media_id;
    /**
     * 对象2:剧集ssid
     */
    private int season_id;
    /**
     * 对象2:剧集类型
     * 1：番剧
     * 2：电影
     * 3：纪录片
     * 4：国创
     * 5：电视剧
     * 7：综艺
     */
    private int media_type;
    /**
     * 对象2:剧集原名	关键字用xml标签<em class="keyword">标注
     */
    private String org_title;
    /**
     * 对象2:地区
     */
    private String areas;
    /**
     * 对象2:风格
     */
    private String styles;
    /**
     * 对象2:声优
     */
    private String cv;
    /**
     * 制作组
     */
    private String staff;
    /**
     * 开播时间重写信息	优先级高于pubtime
     */
    private String fix_pubtime_str;
    /**
     * 评分信息
     */
    private MediaScore media_score;

    private int play_state;
    private int media_mode;
    /**
     * 剧集类型
     * 1：番剧
     * 2：电影
     * 3：纪录片
     * 4：国创
     * 5：电视剧
     * 7：综艺
     */
    private int season_type;
    /**
     * 是否追番	需要登录(SESSDATA)
     * 未登录则恒为0
     * 0：否
     * 1：是
     */
    private int is_follow;
    private boolean is_avid;
    /**
     * 开播时间	时间戳
     */
    private long pubtime;
    /**
     * 剧集ssid
     */
    private long pgc_season_id;
    /**
     * 结果匹配的分集数
     */
    private long ep_size;
    /**
     * 剧集重定向url
     */
    private String goto_url;

    /**
     * 观看按钮文字
     */
    private String button_text;
    /**
     * 角标内容
     */
    private String angle_title;
    /**
     * 角标颜色	0：红色,2：橙色
     */
    private String angle_color;
    /**
     * 关键字匹配分集标题的分集epid	多个用,分隔
     */
    private String hit_epids;
    /**
     * 剧集类型文字
     */
    private String season_type_name;
    /**
     * 分集选择按钮风格	horizontal：横排式, grid：按钮式
     */
    private String selection_style;
    /**
     * 剧集标志信息
     */
    private List<String> display_info;

    /**
     * 结果匹配的分集信息
     */
    private List<Ep> eps;

    /**
     * 剧集标志信息
     */
    private List<Badge> badges;


    @Data
    static class MediaScore {
        /**
         * 总计评分人数
         */
        private int user_count;
        /**
         * 评分
         */
        private int score;
    }


    @Data
    static class Ep{
        /**
         * 分集epid
         */
        private int id;
        /**
         * 分集封面url
         */
        private String cover;
        /**
         * 完整标题
         */
        private String title;
        /**
         * 分集重定向url
         */
        private String url;
        private String release_date;
        /**
         * 分集标志
         */
        private List<String> badges;

        /**
         * 单集标题
         */
        private String long_title;
        /**
         * 短标题
         */
        private String index_title;
    }

    @Data
    static class Badge{
        /**
         * 剧集标志	颜色码
         */
        private String text;
        /**
         * 文字颜色	颜色码
         */
        private String text_color;
        /**
         * 夜间文字颜色	颜色码
         */
        private String text_color_night;
        /**
         * 背景颜色	颜色码
         */
        private String bg_color;
        /**
         * 夜间背景颜色	颜色码
         */
        private String bg_color_night;
    }
}
