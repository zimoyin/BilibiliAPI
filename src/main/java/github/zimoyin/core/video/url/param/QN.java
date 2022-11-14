package github.zimoyin.core.video.url.param;

/**
 * qn视频清晰度标识
 * 注：该值在dash模式且非下载模式时无效
 */
public enum QN {
    /**
     * 204P 极速	 仅mp4方式支持
     */
    P204(6),
    /**
     * 360P 流畅
     */
    P306(16),
    /**
     * 480P 清晰
     */
    P480(32),
    /**
     * 720P 高清	web端默认值
     */
    P720(64),
    /**
     * 720P60 高帧率	需要认证登录账号
     */
    P720_60_cookie(74),
    /**
     * 1080P 高清	TV端与APP端默认值
     */
    P1080_cookie(80),
    /**
     * 1080P+ 高码率	大多情况需求认证大会员账号
     */
    P1080_plus_cookie_vip(112),
    /**
     * 1080P60 高帧率	大多情况需求认证大会员账号
     */
    P1080_60_cookie_vip(116),
    /**
     * 4K 超清	需要fnver&128=128且fourk=1
     */
    P4k_cookie_vip(120),
    /**
     * HDR 真彩色	仅支持dash方式 需要fnver&64=64
     */
    HDR_cookie_vip(125),
    /**
     * 杜比视界	仅支持dash方式 需要fnver&512=512
     */
    DV_cookie_vip_all(126),
    /**
     * 8K 超高清	仅支持dash方式 需要fnver&1024=1024
     */
    P8k_cookie_vip(127);
    private int qn = 80;
    QN(int qn) {
        this.qn = qn;
    }
    public int getQn() {
        return qn;
    }
}
