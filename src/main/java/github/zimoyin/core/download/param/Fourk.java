package github.zimoyin.core.download.param;

/**
 * 是否超清画质
 */
public enum Fourk {
    UltraHDQuality(1),
    LowDefinitionQuality(0),
    Default(1);
    private int value = 0;
    Fourk(int value) {
        this.value = value;
    }
    public int getValue() {
        return value;
    }
}
