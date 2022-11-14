/**
  * Copyright 2022 json.cn 
  */
package github.zimoyin.bili.music.url.pojo.url;

import lombok.Data;

/**
 * Auto-generated: 2022-08-30 11:12:18
 *
 * @author json.cn (i@json.cn)
 * @website http://www.json.cn/java2pojo/
 */
@Data
public class Qualities {
    /**
     * 音质代码:数字越大越清晰
     */
    private int type;
    /**
     * 音质名称
     */
    private String desc;
    /**
     * 该音质的文件大小	单位为字节
     */
    private long size;
    /**
     * 比特率标签
     */
    private String bps;
    /**
     * 	音质标签
     */
    private String tag;
    /**
     * 是否需要会员权限
     * 0：不需要
     * 1：需要
     */
    private int require;
    /**
     * 会员权限标签
     */
    private String requiredesc;
}