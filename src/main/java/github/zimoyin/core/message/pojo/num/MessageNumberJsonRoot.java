/**
  * Copyright 2022 json.cn 
  */
package github.zimoyin.core.message.pojo.num;

/**
 * Auto-generated: 2022-08-17 9:34:37
 *
 * @author json.cn (i@json.cn)
 * @website http://www.json.cn/java2pojo/
 */
public class MessageNumberJsonRoot {

    private int code;
    private String message;
    private int ttl;
    private Data data;


    @lombok.Data
    public class Data {

        /**
         * AT 数
         */
        private int at;
        /**
         * 信息数
         */
        private int chat;
        /**
         * 点赞数
         */
        private int like;
        /**
         * 回复数
         */
        private int reply;
        /**
         * 系统信息数
         */
        private int sys_msg;
        /**
         * up信息助手信息
         */
        private int up;

    }

}