package github.zimoyin.core.video.url.data;

import github.zimoyin.core.music.url.MusicURLFormat;
import lombok.ToString;

import java.util.HashMap;

/**
 * 视频清晰度
 */
@Deprecated
@ToString
public class QN {
    public static final int P204 = 6;//204P 极速	 仅mp4方式支持
    public static final int P306 = 16;//360P 流畅
    public static final int P480 = 32;//480P 清晰
    public static final int P720 = 64;//720P 高清	web端默认值

    public static final int P720_60_cookie = 74;//720P60 高帧率	需要认证登录账号
    public static final int P1080_cookie = 80;//1080P 高清	TV端与APP端默认值

    public static final int P1080_plus_cookie_vip = 112;//1080P+ 高码率	大多情况需求认证大会员账号
    public static final int P1080_60_cookie_vip = 116;//1080P60 高帧率	大多情况需求认证大会员账号
    public static final int P4k_cookie_vip = 120;//4K 超清	需要fnver&128=128且fourk=1

    public static final int HDR_cookie_vip = 125;//HDR 真彩色	仅支持dash方式 需要fnver&64=64

    public static final int DV_cookie_vip_all = 126;//杜比视界	仅支持dash方式 需要fnver&512=512
    public static final int DV_cookie_vip_video = 1261;//杜比视界	仅支持dash方式 需要fnver&512=512
    public static final int DV_cookie_vip_music = 1262;//杜比视界	仅支持dash方式 需要fnver&512=512

    public static final int P8k_cookie_vip = 127;//8K 超高清	仅支持dash方式 需要fnver&1024=1024


    public int qn;

    public QN(int qn) {
        this.qn = qn;
    }


    public HashMap<String, String> getVal(int qn,HashMap<String, String> val) {
        this.qn = qn;
        return getVal(val);
    }


    /**
     * 构建URL参数
     *
     * @return
     */
    public HashMap<String, String> getVal(HashMap<String, String> val) {
        val.put("qn", String.valueOf(qn));
        val.put("fnver", String.valueOf(0));

        if (qn == P4k_cookie_vip) {
            val.put("128", "128");
            val.put("fourk", "1");
//            val.put("fnval", "128");
            val.put("fnval", "120");
        }

        if (qn == HDR_cookie_vip) {
            val.put("64", "64");
            val.put("fnval", "64");
        }

        if (qn == DV_cookie_vip_all) {
            val.put("512", "512");
            val.put("fnval", "256|512");
        }

        if (qn == DV_cookie_vip_video) {
            val.put("512", "512");
            val.put("fnval", "512");
        }

        if (qn == DV_cookie_vip_music) {
            val.put("512", "512");
            val.put("fnval", "256");
        }

        if (qn == P8k_cookie_vip) {
            val.put("1024", "1024");
            val.put("fnval", "1024");
        }
        return val;
    }

    @Override
    public String toString() {
        return "QN{" +
                "qn=" + qn +
                '}';
    }
}