package github.zimoyin.core.video.url.pojo;

import github.zimoyin.core.video.download.VideoDownload;

import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * 视频URL信息
 */
@lombok.Data
public class VideoURLJsonRoot {
    /**
     * 返回值
     * 0：成功
     * -400：请求错误
     * -404：无视频
     */
    private int code;

    private String bv;

    /**
     * 错误信息	默认为0
     */
    private String message;

    private int ttl;


    private Data data;


    /**
     * 视频实际格式代码，由程序给出。
     */
    private int fnval;

    /**
     * 访问的哪个URL得到的该信息
     */
    @Deprecated
    private URI uri;

    /**
     * 返回视频格式，由服务器给出
     *
     * @return
     */
    public String getFormat() {
        return this.getData().getFormat();
    }

    @Override
    public String toString() {
        return "VideoURLJson{" +
                "\n\tcode=" + code +
                ", \n\tmessage='" + message + '\'' +
                ", \n\tttl=" + ttl +
                ", \n\t" + data + "\n" +
                '}';
    }


    /**
     * 获取所有视频的URL：将在版本稳定后删除
     *
     * @return
     */
    public ArrayList<URL> getURLs() {
        ArrayList<URL> urls = new ArrayList<>();
        if (this.data == null) throw new NullPointerException("您大概是没有权限下载该视频吧，无法获取到该视频的下载URL");
        if (this.data.durl != null) {
            for (Durl durl : this.data.durl) urls.add(durl.url);
        }
        if (this.data.dash != null) {
            for (Dash dash : this.data.dash) {
                for (Media audio : dash.audio) urls.add(audio.baseUrl);
                for (Media video : dash.video) urls.add(video.baseUrl);
            }
        }

        return urls;
    }


    /**
     * 获取视频下载器
     *
     * @return
     */
    @Deprecated
    public VideoDownload getVideoDownload() {
        return new VideoDownload(this.getBv());
    }



    /**
     * data对象，里面的字段只保留了部分
     */
    @lombok.Data
    public class Data {
        /**
         * 字段	类型	内容	备注
         * durl	array	视频分段	注：仅flv/mp4存在此项
         * dash	obj	dash音视频流信息	注：仅dash存在此项
         */

        /**
         * 用户请求的分辨率（服务器在Durl下会返回用户申请的分辨率，但是在Dash下会返回所有的分辨率）  值含义见上表
         */
        public int quality;

        /**
         * 视频格式：对于Durl是有效的，Dash只能用于参考，因为Dash会返回所有分辨率与格式的视频
         */
        public String format;

        /**
         * 视频长度，单位毫秒
         * 不同分辨率/格式可能有略微差异
         */
        public long timelength;

        /**
         * 视频支持的全部格式
         */
        public String accept_format;

        /**
         * 视频支持的分辨率列表
         */
        public String[] accept_description;

        /**
         * 支持格式的详细信息
         */
        public String[] support_formats;


        /**
         * 视频分段
         * 注：仅flv/mp4存在此项
         */
        public ArrayList<Durl> durl;


        /**
         * dash音视频流信息	注：仅dash存在此项
         */
        public ArrayList<Dash> dash;




        /**
         * flv与mp4格式的在这里
         * 仅flv方式具有分段
         */


        @Override
        public String toString() {
            return "Data{" +
                    "\n\t\tquality(视频分辨率代码)=" + quality +
                    ", \n\t\tformat(视频格式)='" + format + '\'' +
                    ", \n\t\ttimelength=(视频长度)" + ((double) timelength / 1000 / 60) + " min" +
                    ", \n\t\taccept_format(视频支持的全部格式)='" + accept_format + '\'' +
                    ", \n\t\taccept_description(视频支持的分辨率列表)=" + Arrays.toString(accept_description) +
                    ", \n\t\tsupport_formats(支持格式的详细信息)=" + Arrays.toString(support_formats) +
                    ", \n\t\tdurl=" + durl +
                    ", \n\t\tdash=" + dash +
                    "\n\t" +
                    '}';
        }
    }
}

