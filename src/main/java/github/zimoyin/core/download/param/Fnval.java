package github.zimoyin.core.download.param;

/**
 * fnval视频流格式标识
 */
public enum Fnval {
    /**
     * flv格式	仅H.264编码，部分老视频存在分段现象，与其他 VideoFormat 互斥
     */
    FLV(0),
    /**
     * mp4格式(注意分辨率大部分为360P)	仅H.264编码 与其他 VideoFormat 互斥
     */
    MP4(1),
    /**
     * dash格式	H.264编码或H.265编码，部分老视频的清晰度上限低于flv格式  与其他 VideoFormat 互斥
     */
    Dash(16),
    /**
     * 是否需求 av1 编码	必须为dash格式
     */
    Av1(2048),
    /**
     * 是否需求 av1 编码	已经与 Dash 进行了运算
     */
    Av1_Dash(2064),

    /**
     * 杜比视界	仅支持dash方式 需要fnver&512=512
     */
    DV_cookie_vip_video(512),
    /**
     * 杜比视界	仅支持dash方式 需要fnver&256=256
     */
    DV_cookie_vip_music(256);
    private int value = 0;
    Fnval(int value) {
        this.value = value;
    }
    public int getValue() {
        return value;
    }
}
