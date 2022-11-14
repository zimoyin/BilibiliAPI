package github.zimoyin.bili.search.enums;

/**
 * 专栏及相簿分区筛选(非必要)
 */
public enum SearchCategoryID {
    /**
     * 全部分区
     */
    Column_All("0"),
    /**
     * 游戏
     */
    Column_Game("1"),
    /**
     * 动画
     */
    Column_Animation("2"),
    /**
     * 影视
     */
    Column_Movie("28"),
    /**
     * 生活
     */
    Column_Life("3"),
    /**
     * 兴趣
     */
    Column_Interest("29"),
    /**
     * 轻小说
     */
    Column_LightNovel("16"),
    /**
     * 科技
     */
    Column_science("17"),


    /**
     * 相簿全部分区
     */
    Photo_All("0"),
    /**
     * 画友
     */
    Photo_PaintingFriend("1"),
    /**
     * 摄影
     */
    Photo_Photography("2")

    ;
    private String id;

    SearchCategoryID(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
