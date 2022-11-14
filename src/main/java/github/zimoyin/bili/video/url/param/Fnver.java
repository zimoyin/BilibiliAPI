package github.zimoyin.bili.video.url.param;

/**
 * fnver视频流版本标识
 */
public enum Fnver {
    Default(0);

    private int fnver;
    Fnver(int fnver) {
        this.fnver = fnver;
    }
    public int getValue() {
        return fnver;
    }
}
