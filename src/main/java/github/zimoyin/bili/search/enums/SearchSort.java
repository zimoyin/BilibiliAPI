package github.zimoyin.bili.search.enums;

/**
 * (非必要) 用户粉丝数及等级排序顺序
 * 仅用于搜索用户
 */
public enum SearchSort {
    /**
     * 升序(默认)：由低到高
     */
    AscendingOrder("1"),
    /**
     * 降序： 由高到低
     */
    DescendingOrder("0"),


    ;
    private String sort;

    SearchSort(String sort) {
        this.sort = sort;
    }

    public String getSort() {
        return sort;
    }
}
