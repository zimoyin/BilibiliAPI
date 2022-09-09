/**
  * Copyright 2022 json.cn 
  */
package github.zimoyin.core.user.pojo.state.up;

import lombok.Data;

/**
 * Auto-generated: 2022-07-23 19:24:56
 *
 * @author json.cn (i@json.cn)
 * @website http://www.json.cn/java2pojo/
 */
@Data
public class UpStatJsonRootBean {

    private int code;
    private String message;
    private int ttl;
    private Data data;


    @lombok.Data
    public class Data {

        /**
         * 视频播放量
         */
        private Archive archive;
        /**
         * 专栏阅读量
         */
        private Article article;
        /**
         * 获赞次数
         */
        private long likes;

    }

}