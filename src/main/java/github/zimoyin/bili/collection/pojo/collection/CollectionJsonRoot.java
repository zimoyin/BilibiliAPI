/**
  * Copyright 2022 json.cn 
  */
package github.zimoyin.bili.collection.pojo.collection;

import java.util.List;

/**
 * Auto-generated: 2022-08-29 22:30:20
 *
 * @author json.cn (i@json.cn)
 * @website http://www.json.cn/java2pojo/
 */
@lombok.Data
public class CollectionJsonRoot {

    private int code;
    private String message;
    private int ttl;
    private Data data;

    @lombok.Data
    public static class Data{
        /**
         * 当前这一页的视频av id列表
         */
        private List<Long> aids;
        /**
         * 视频列表
         */
        private List<Archives> archives;
        /**
         * 合集信息
         */
        private Meta meta;
        /**
         * 当前页的数据信息
         */
        private Page page;
    }
}