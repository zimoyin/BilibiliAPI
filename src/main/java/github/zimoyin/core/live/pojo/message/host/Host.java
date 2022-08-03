/**
  * Copyright 2022 json.cn 
  */
package github.zimoyin.core.live.pojo.message.host;

import lombok.Data;

/**
 * Auto-generated: 2022-08-01 18:39:29
 *
 * @author json.cn (i@json.cn)
 * @website http://www.json.cn/java2pojo/
 */
@Data
public class Host {
    /**
     * 服务器域名
     */
    private String host;
    /**
     * tcp端口
     */
    private int port;
    /**
     * wss端口
     */
    private int wss_port;
    /**
     * ws端口
     */
    private int ws_port;
}