/**
  * Copyright 2022 json.cn 
  */
package github.zimoyin.core.live.pojo.info;

/**
 * Auto-generated: 2022-08-01 18:39:29
 *
 * @author json.cn (i@json.cn)
 * @website http://www.json.cn/java2pojo/
 */
@lombok.Data
public class DanmuInfoJsonRootBean {

    /**
     * 65530 token 错误
     */
    private int code;
    private String message;
    private int ttl;
    private Data data;

    /**
     * 直播间ID，由API系统给出
     */
    private long roomID;
    /**
     * 用户ID
     */
    private long mid=0;

}