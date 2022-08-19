package github.zimoyin.core.search.enums;

/**
 * 视频分区筛选(非必要)	仅用于搜索视频
 */
public enum SearchTids {
    /**
     * (默认)全部分区
     */
    All("0"),
    ;
    /**
     * 如果要筛选分区，请将分区的ID传入到这里
     */
    private String tid;

    SearchTids(String tid) {
        this.tid = tid;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }
}
