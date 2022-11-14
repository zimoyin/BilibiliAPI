package github.zimoyin.bili.video.url.pojo;

import lombok.Data;

import java.net.URL;
import java.util.ArrayList;


@Data
public class Durl {


    /**
     * 视频分段序号	某些视频会分为多个片段（从1顺序增长）
     */
    public int order;

    /**
     * 视频长度	单位为毫秒
     */
    public long length;

    /**
     * 视频大小 byte
     */
    public long size;

    /**
     * URL
     */
    public URL url;


    /**
     * 备用视频流
     */
    public ArrayList<URL> backup_url;

    @Override
    public String toString() {
        return "\n\t\t\tDurl{" +
                "\n\t\t\t\torder=" + order +
                ", \n\t\t\t\tlength=" + length +
                ", \n\t\t\t\tsize=" + size +
                ", \n\t\t\t\turl=" + url +
                ", \n\t\t\t\tbackup_url=" + backup_url + "\n\t\t\t" +
                '}' + "\n\t\t";
    }
}