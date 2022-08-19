package github.zimoyin.core.search.enums;

/**
 * order(非必要): 结果排序方式
 */
public enum SearchOrder {
    /**
     * 默认综合排序
     */
    TotalRank("totalrank"),
    /**
     * 点击排序
     */
    ClickRank("click"),
    /**
     * 发布时间（最近）
     */
    PublishDate("pubdate"),
    /**
     * 最多弹幕
     */
    Barrage("dm"),
    /**
     * 最多收藏
     */
    Stow("stow"),
    /**
     * 最多评论
     */
    Scores("scores"),
    /**
     * 最多喜欢 （仅用于专栏）
     */
    Column_Attention("attention"),

    /**
     * 人气直播(默认)
     */
    Live_Online("online"),
    /**
     * 最新开播
     */
    Live_LiveTime("live_time"),

    /**
     * 搜索结果为用户时: 默认排序
     */
    User_Total("0"),
    /**
     * 粉丝数
     */
    User_Fans("fans"),
    /**
     * 用户等级
     */
    User_Level("level")
    ;
    private String order;

    SearchOrder(String order) {
        this.order = order;
    }

    public String getOrder() {
        return order;
    }
}
