package github.zimoyin.core.comment.enums;


/**
 * 排序方式
 */
public enum Sort {
    TIME(0),LIKE(1),REPLY(2);
    private int sort;

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
