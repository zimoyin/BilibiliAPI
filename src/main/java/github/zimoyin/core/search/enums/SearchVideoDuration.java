package github.zimoyin.core.search.enums;

/**
 * 视频时长筛选(非必要)	仅用于搜索视频
 */
public enum SearchVideoDuration {
    /**
     * 全部时长
     */
    All("0"),
    /**
     * 10分钟以下：1
     */
    TenMinutes("1"),
    /**
     * 10-30分钟：2
     */
    HalfHour("2"),
    /**
     * 30-60分钟：3
     */
    AnHour("3"),
    /**
     * 大于1小时
     */
    MoreThan_OneHour("4")
    ;
    private String duration;

    SearchVideoDuration(String duration) {
        this.duration = duration;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
}
