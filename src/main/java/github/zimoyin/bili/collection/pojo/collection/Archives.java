/**
  * Copyright 2022 json.cn 
  */
package github.zimoyin.bili.collection.pojo.collection;

import lombok.Data;

/**
 * Auto-generated: 2022-08-29 22:30:20
 *
 * @author json.cn (i@json.cn)
 * @website http://www.json.cn/java2pojo/
 */
@Data
public class Archives {
    /**
     * av id
     */
    private long aid;
    /**
     * bv id
     */
    private String bvid;
    /**
     * 稿件上传日期
     */
    private long ctime;
    /**
     * 视频长度 （s）
     */
    private int duration;
    /**
     *
     */
    private boolean interactive_video;
    /**
     * 封面
     */
    private String pic;
    /**
     * 发布日期
     */
    private long pubdate;
    /**
     * 视频的数据
     */
    private Stat stat;
    private int state;
    /**
     * 标题
     */
    private String title;
    private int ugc_pay;

}