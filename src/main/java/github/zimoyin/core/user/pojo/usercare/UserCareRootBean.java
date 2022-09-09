/**
  * Copyright 2022 json.cn 
  */
package github.zimoyin.core.user.pojo.usercare;

import lombok.Data;

/**
 * Auto-generated: 2022-07-22 12:44:13
 *
 * @author json.cn (i@json.cn)
 * @website http://www.json.cn/java2pojo/
 */
@Data
public class UserCareRootBean {

    private int code;
    private String message;
    private int ttl;
    private Data data;



    @lombok.Data
    public static class Data{

        /**
         * 卡片信息
         */
        private Card card;
        /**
         * 是否关注了该用户
         */
        private boolean following;
        private int archive_count;
        private int article_count;
        /**
         * 用户的跟随者数量
         */
        private int follower;
        /**
         * 获赞数
         */
        private int like_num;
    }
}