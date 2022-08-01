package github.zimoyin.core.video.url.data;

import lombok.ToString;

import java.util.HashMap;

/**
 * 视频格式
 */
@ToString
public class Fnval {
    /**
     * flv格式	仅H.264编码，部分老视频存在分段现象，与其他 VideoFormat 互斥
     */
    public static final int VideoFormat_flv = 0;
    /**
     * mp4格式(注意分辨率大部分为360P)	仅H.264编码 与其他 VideoFormat 互斥
     */
    public static final int VideoFormat_mp4 = 1;
    /**
     * dash格式	H.264编码或H.265编码，部分老视频的清晰度上限低于flv格式  与其他 VideoFormat 互斥
     */
    public static final int VideoFormat_dash = 16;

    public int VideoFormat;

    public Fnval(int videoFormat) {
        VideoFormat = videoFormat;
    }

    public int getVideoFormat() {
        return VideoFormat;
    }

    public void setVideoFormat(int videoFormat) {
        VideoFormat = videoFormat;
    }

    public HashMap<String, String> getVal(HashMap<String, String> val) {
        //QN里面传来的fnval，如果有这里需要合并下
        String fnval = val.get("fnval");
        //是否存在需要组合的数组
        if (fnval != null) {
            if (fnval.split("|").length >= 2) {
                //组合功能需要使用OR(|)运算结合一下数值,例如请求dash格式且需要HDR的视频流，则fnval=16|64=80
                String[] split = fnval.split("|");
                int i = 0;
                for (String str : split) {
                    i += Integer.parseInt(str);
                }
                fnval = VideoFormat + fnval + "=" + i;
            } else {
                if (Integer.parseInt(fnval) + VideoFormat != 0) {
                    //组合功能需要使用OR(|)运算结合一下数值，例如请求dash格式且需要HDR的视频流，则fnval=16|64=80
                    fnval = String.valueOf(VideoFormat) + "|" + fnval + "=" + (VideoFormat + Integer.parseInt(fnval));
                }
            }
        } else {
            fnval = String.valueOf(VideoFormat);
        }
        val.put("fnval", fnval);
        return null;
    }


    @Override
    public String toString() {
        return "Fnval{" +
                "VideoFormat=" + VideoFormat +
                '}';
    }
}