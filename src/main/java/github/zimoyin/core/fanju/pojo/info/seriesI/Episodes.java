/**
  * Copyright 2022 json.cn 
  */
package github.zimoyin.core.fanju.pojo.info.seriesI;

import lombok.Data;

/**
 * Auto-generated: 2022-07-31 20:29:51
 *
 * @author json.cn (i@json.cn)
 * @website http://www.json.cn/java2pojo/
 */
@Data
public class Episodes {

    /**
     * 单集稿件avid
     */
    private long aid;
    /**
     * 标签文字	例如会员、限免等
     */
    private String badge;
    private Badge_info badge_info;
    private int badge_type;
    /**
     * 单集稿件bvid
     */
    private String bvid;
    /**
     * 视频cid
     */
    private long cid;
    /**
     * 单集封面url
     */
    private String cover;
    /**
     * 分辨率信息
     */
    private Dimension dimension;

    private int duration;
    private String from;
    /**
     * 单集epid
     */
    private long id;
    private boolean is_view_hide;
    /**
     * 单集网页url
     */
    private String link;
    /**
     * 单集完整标题
     */
    private String long_title;
    /**
     * 发布时间	时间戳
     */
    private long pub_time;
    private int pv;
    private String release_date;
    private Rights rights;
    /**
     * 《{标题}》+第n话+｛单集完整标题｝
     */
    private String share_copy;
    /**
     * 单集网页url
     */
    private String share_url;
    /**
     * 单集网页url短链接
     */
    private String short_link;
    private Stat stat;
    private int status;
    /**
     * 单集副标题	观看次数文字
     */
    private String subtitle;
    /**
     * 单集标题
     */
    private String title;
    /**
     * 单集vid	vupload_+{cid}
     */
    private String vid;

    public long getEpId(){
        return id;
    }
}