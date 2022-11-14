package github.zimoyin.bili.comment.enums;


/**
 * 排序方式
 */
public enum Sort {
    TIME(0),LIKE(1),REPLY(2);
    private final int sort;

    Sort(int sort) {
        this.sort = sort;
    }

    public int getSort() {
        return sort;
    }

    @Override
    public String toString() {
        return String.valueOf(sort);
    }
}
