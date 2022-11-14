package github.zimoyin.bili.search.enums;

/**
 * 搜索目标类型(必要)
 */
public enum SearchType {
    /**
     * 视频
     */
    Video("video"),
    /**
     * 番剧
     */
    FanJu("media_bangumi"),
    /**
     * 影视
     */
    Movies("media_ft"),
    /**
     * 直播间及主播
     */
    Live("live"),
    /**
     * 直播间
     */
    LiveRoom("live_room"),
    /**
     * 主播
     */
    LiveUp("live_user"),
    /**
     * 专栏
     */
    Article("article"),
    /**
     * 话题
     */
    Topic("topic"),
    /**
     * 用户
     */
    User("bili_user"),
    /**
     * 相册
     */
    Photo("photo")
    ;
    private String type;

    SearchType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
