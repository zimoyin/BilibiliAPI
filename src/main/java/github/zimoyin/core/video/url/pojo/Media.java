package github.zimoyin.core.video.url.pojo;

import lombok.Data;

import java.net.URL;
import java.util.ArrayList;

/**
 * video 和 audio 的公共字段
 * 每个video和audio项是分离的m4s格式，只有把他们合并在一起才是完整的视频，否则视频只有画面没有音频
 * 每个video或audio项都是独立的，除了flv格式是分段的以外，他们都是独立
 * video和audio是服务器返回的所有画质视频的URL，只需要在video和audio分别下载一个并合并即可（除了特殊的flv格式）
 */
@Data
public class Media {
    /**
     * 视频画质代码
     */
    public int id;
    /**
     * 默认视频/音频流url
     * 有效时间为120min
     */
    public URL baseUrl;
    /**
     * 默认视频/音频流url
     * 有效时间为120min
     */
    public URL base_url;

    /**
     * 备份默认视频/音频流URL
     */
    public ArrayList<URL> backupUrl;
    /**
     * 备份默认视频/音频流URL
     */
    public ArrayList<URL> backup_url;

    /**
     * 视频/音频格式类型
     */
    public String mimeType;

    /**
     * 编码/音频类型
     */
    public String codecs;

    /**
     * 视频宽度
     */
    public int width;

    /**
     * 视频高的
     */
    public int height;
    /**
     * 视频帧率
     */
    public String frameRate;

    /**
     * 视频: 7或12
     * 音频: 0
     * 7=AVC，12=HEVC
     * 出处是 FLV 格式里 VideoTag 中的 CodecId 域；
     * FLV 标准不支持 HEVC，但 codecId 值为 12 是各厂共识
     */
    public int codecid;

    /**
     * url 对应 m4s 文件中，头部的位置
     */
    public SegmentBase SegmentBase;


    /**
     * 常规 MP4 文件的索引信息放在 moov box 中，其中包含每一帧 (不止是关键帧) 的一些信息。在 DASH 方式下，关键帧信息移到了 sidx box 里，其他的则分散到了各个 moof (movie fragment) box 中。
     * 对这里的文件结构感兴趣的，可以参考标准文档 ISO/IEC 14496-12，如果不想那么深入的话可以百度「MP4 文件结构」。
     */
    @Data
    public class SegmentBase {
        /**
         * <init-first>-<init-last>
         * 如 0-821 表示开头 820 个字节
         * 如：0-821	ftyp (file type) box 加上 moov box 在 m4s 文件中的范围（单位为 bytes）
         */
        public String initialization;
        /**
         * <sidx-first>-<sidx-last>
         * 如：822-1309
         * sidx (segment index) box 在 m4s 文件中的范围（单位为 bytes）
         * sidx 的核心是一个数组，记录了各关键帧的时间戳及其在文件中的位置，
         * 其作用是索引 (拖进度条)
         */
        public String index_range;
    }
}