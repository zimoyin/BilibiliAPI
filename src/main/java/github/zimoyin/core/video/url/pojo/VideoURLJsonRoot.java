package github.zimoyin.core.video.url.pojo;

import github.zimoyin.core.video.download.VideoDownload;

import java.net.URI;
import java.net.URL;
import java.util.ArrayList;

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
}

