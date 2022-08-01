/**
 * Copyright 2022 json.cn
 */
package github.zimoyin.core.fanju.pojo.info.seriesI;

import lombok.Data;

import java.util.List;

/**
 * Auto-generated: 2022-07-31 20:29:51
 *
 * @author json.cn (i@json.cn)
 * @website http://www.json.cn/java2pojo/
 */
@Data
public class Result {

    /**
     * 参与的活动
     */
    private Activity activity;
    private String alias;

    private List<Areas> areas;
    /**
     * 网页背景图片url
     */
    private String bkg_cover;
    /**
     * 剧集封面图片url
     */
    private String cover;
    /**
     *正片剧集列表
     */
    private List<Episodes> episodes;

    /**
     * 简介
     */
    private String evaluate;
    private Freya freya;
    private String jp_title;
    /**
     * 简介页面url
     */
    private String link;
    /**
     * 剧集mdid
     */
    private long media_id;
    private int mode;
    /**
     * 更新信息
     */
    private New_ep new_ep;
    /**
     * 会员&付费信息	若无相关内容则无此项
     */
    private Payment payment;

    private Positive positive;
    /**
     * 发布信息
     */
    private Publish publish;
    /**
     * 评分信息	若无相关内容则无此项
     */
    private Rating rating;
    /**
     * 备案号
     */
    private String record;
    /**
     * 属性标志信息
     */
    private Rights rights;
    /**
     * 番剧ssid
     */
    private int season_id;
    /**
     * 剧集标题
     */
    private String season_title;
    /**
     * 同系列所有季信息
     */
    private List<Seasons> seasons;
    /**
     * 花絮、PV、番外等非正片内容
     */
    private List<Section> section;
    /**
     * 系列信息
     */
    private Series series;
    /**
     * 《{标题}》+{备注}
     */
    private String share_copy;
    /**
     * 备注
     */
    private String share_sub_title;
    /**
     * 番剧播放页面url
     */
    private String share_url;
    /**
     * 网页全屏标志
     */
    private Show show;

    private int show_season_type;
    /**
     * 方形封面图片url
     */
    private String square_cover;
    /**
     * 状态数
     */
    private Stat stat;
    private int status;
    /**
     * 剧集副标题
     */
    private String subtitle;
    /**
     * 剧集标题
     */
    private String title;
    /**
     * 总计正片集数
     * 未完结：大多为-1
     * 已完结：正整数
     */
    private int total;
    /**
     * 剧集类型
     * 1：番剧
     * 2：电影
     * 3：纪录片
     * 4：国创
     * 5：电视剧
     * 7：综艺
     */
    private int type;
    /**
     * UP主信息
     */
    private Up_info up_info;
    private User_status user_status;

    /**
     * 获取第n个剧情的信息
     * @param page
     * @return
     */
    public Episodes getPage(int page){
        List<Episodes> episodes = this.getEpisodes();
        Episodes episode = episodes.get(page);
        return  episode;
    }
}