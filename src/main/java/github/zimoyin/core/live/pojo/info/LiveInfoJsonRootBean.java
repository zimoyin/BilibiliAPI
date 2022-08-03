/**
  * Copyright 2022 json.cn 
  */
package github.zimoyin.core.live.pojo.info;

/**
 * Auto-generated: 2022-08-03 12:30:27
 *
 * @author json.cn (i@json.cn)
 * @website http://www.json.cn/java2pojo/
 */
@lombok.Data
public class LiveInfoJsonRootBean {

    /**
     * 0：成功
     * 60004：直播间不存在
     */
    private int code;
    /**
     * 错误信息
     * 默认为ok
     */
    private String msg;
    /**
     * 错误信息
     * 默认为ok
     */
    private String message;
    private Data data;
}