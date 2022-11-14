/**
  * Copyright 2022 json.cn 
  */
package github.zimoyin.bili.message.pojo.prnum;

/**
 * Auto-generated: 2022-08-17 9:34:37
 *
 * @author json.cn (i@json.cn)
 * @website http://www.json.cn/java2pojo/
 */
public class PrivateMessageNumberJsonRoot {

    private int code;
    private String message;
    private int ttl;
    private Data data;


    @lombok.Data
    public static class Data{

        /**
         * 未关注用户未读私信数
         */
        private int unfollow_unread;
        /**
         * 	已关注用户未读私信数
         */
        private int follow_unread;
        private int gt;

    }

}