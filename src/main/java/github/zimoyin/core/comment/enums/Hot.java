package github.zimoyin.core.comment.enums;

/**
 * 是否显示热评
 */
public enum Hot {
    SHOW(0),NO_SHOW(1);
    private int value;

    Hot(int value) {
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
