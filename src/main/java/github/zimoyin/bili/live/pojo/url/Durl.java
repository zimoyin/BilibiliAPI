/**
  * Copyright 2022 json.cn 
  */
package github.zimoyin.bili.live.pojo.url;

import lombok.Data;

import java.net.URL;

/**
 * Auto-generated: 2022-08-03 20:18:38
 *
 * @author json.cn (i@json.cn)
 * @website http://www.json.cn/java2pojo/
 */
@Data
public class Durl {

    /**
     * 直播流url
     * flv或m3u8格式
     */
    private URL url;
    private int length;
    /**
     * 服务器线路序号
     */
    private int order;
    private int stream_type;
    private int p2p_type;
}