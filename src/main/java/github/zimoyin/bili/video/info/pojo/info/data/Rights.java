/**
  * Copyright 2022 json.cn 
  */
package github.zimoyin.bili.video.info.pojo.info.data;

import lombok.Data;

/**
 * Auto-generated: 2022-07-16 13:2:38
 *
 * @author json.cn (i@json.cn)
 * @website http://www.json.cn/java2pojo/
 * 视频属性标志
 */
@Data
public class Rights {

    private int bp;
    /**
     * 是否支持充电
     */
    private int elec;
    /**
     * 是否允许下载
     */
    private int download;
    /**
     * 是否电影
     */
    private int movie;
    /**
     * 是否PGC付费
     */
    private int pay;
    /**
     * 是否有高码率
     */
    private int hd5;
    /**
     * 是否显示“禁止转载“标志
     */
    private int no_reprint;
    /**
     * 是否自动播放
     */
    private int autoplay;
    /**
     * 是否UGC付费
     */
    private int ugc_pay;
    /**
     * 是否为联合投稿
     */
    private int is_cooperation;
    /**
     * 作用尚不明确
     */
    private int ugc_pay_preview;
    /**
     * 作用尚不明确
     */
    private int no_background;
    private int clean_mode;
    /**
     * 是否为互动视频
     */
    private int is_stein_gate;
    private int is_360;
    private int no_share;
    private int arc_pay;
    private int free_watch;
    private int pay_free_watch;

}