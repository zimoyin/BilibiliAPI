package github.zimoyin.core.video.download;

import lombok.Data;

/**
 * dash 格式视频下载匹配模板，根据模板下载符合要求的视频
 */
@Data
public class DashTemplate {
    /**
     * 视频/音频文件格式，音频与视频格式应保持一直，并且音频使用的是视频的格式
     */
    private String fileType;
    /**
     * 视频编码类型:
     * avc1.640032
     * avc1.64001F
     * avc1.640028
     * avc1.64001E
     * hev1.1.6.L120.90
     * hev1.1.6.L150.90
     */
    private String videoCodecs;
    /**
     * 视频清晰度
     */
    private int qn;

    /**
     * 音频清晰度
     */
    private int audioQn;
    /**
     * 音频编码类型
     */
    private String audioCodecs;


}
