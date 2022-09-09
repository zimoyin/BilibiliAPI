/**
  * Copyright 2022 json.cn 
  */
package github.zimoyin.core.user.pojo.state.album;

import lombok.Data;

/**
 * Auto-generated: 2022-07-23 19:33:28
 *
 * @author json.cn (i@json.cn)
 * @website http://www.json.cn/java2pojo/
 */
@Data
public class AlbumStatJsonRootBean {

    private int code;
    private String msg;
    private String message;
    private Data data;



    @lombok.Data
    public static class Data{

        /**
         * 相簿总数	总数为以下三者之和
         */
        private int all_count;
        /**
         * 发布绘画数
         */
        private int draw_count;
        /**
         * 发布摄影数
         */
        private int photo_count;
        /**
         * 发布日常（图片动态）数
         */
        private int daily_count;
    }

}