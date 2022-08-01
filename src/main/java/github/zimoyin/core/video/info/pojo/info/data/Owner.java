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
 * 视频UP主信息
 */
@Data
public class Owner {

    /**
     * UP主mid
     */
    private long mid;
    /**
     * UP主昵称
     */
    private String name;
    /**
     * UP主头像
     */
    private URL face;

}