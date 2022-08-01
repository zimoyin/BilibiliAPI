/**
  * Copyright 2022 json.cn 
  */
package github.zimoyin.core.video.info.pojo.tag;

import java.util.List;

/**
 * Auto-generated: 2022-07-18 18:48:58
 *
 * @author json.cn (i@json.cn)
 * @website http://www.json.cn/java2pojo/
 */
@lombok.Data
public class VideoTagRootBean {

    private int code;
    private String message;
    private int ttl;
    /**
     * n个标签数据
     */
    private List<Data> data;
}