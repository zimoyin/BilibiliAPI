package github.zimoyin.core.comment.enums;

public enum Mode {
    /**
     * 热度排序
     */
    HOT(3),
    /**
     * 按热度+按时间
     */
    HOT_AND_TIME(1),
    /**
     * 仅按时间
     */
    TIME(2)
    ;
    private int value;

    Mode(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
