package github.zimoyin.bili.comment.enums;

@Deprecated
public abstract class EnumData {
    private int value;


    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
