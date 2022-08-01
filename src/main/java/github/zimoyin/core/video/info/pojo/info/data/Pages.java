/**
  * Copyright 2022 json.cn 
  */
package github.zimoyin.core.video.info.pojo.info.data;

import lombok.Data;

import java.net.URL;

/**
 * Auto-generated: 2022-07-16 13:2:38
 *
 * @author json.cn (i@json.cn)
 * @website http://www.json.cn/java2pojo/
 * 视频分p钟具体一p的信息
 */
@Data
public class Pages {

    /**
     * 当前分P cid
     */
    private long cid;
    /**
     * 当前分P
     */
    private int page;
    /**
     * 视频来源:<br>
     * vupload：普通上传（B站）<br>
     * hunan：芒果TV<br>
     * qq：腾讯<br>
     */
    private String from;
    /**
     * 当前分P标题
     */
    private String part;
    /**
     * 当前分P持续时间:单位为秒
     */
    private int duration;
    /**
     * 站外视频vid	仅站外视频有效
     */
    private String vid;
    /**
     * 站外视频跳转url	仅站外视频有效
     */
    private URL weblink;
    /**
     * 当前分P分辨率	,部分较老视频无分辨率值
     */
    private Dimension dimension;
    private String first_frame;
}