package github.zimoyin.bili.video.url.pojo;

import lombok.Data;

import java.util.ArrayList;

/**
 * dash格式在这里
 */
@Data
public class Dash {
    /**
     * 视频秒数
     */
    public long duration;

    /**
     * 视频流信息
     *
     * @return
     */
    public ArrayList<Media> video;

    /**
     * 音频流信息
     */
    public ArrayList<Media> audio;

    @Override
    public String toString() {
        return "\n\t\t\tDash{" +
                "\n\t\t\t\tduration=" + duration +
                ", \n\t\t\t\tvideo=" + video +
                ", \n\t\t\t\taudio=" + audio + "\n\t\t\t" +
                '}' + "\n\t\t";
    }
}