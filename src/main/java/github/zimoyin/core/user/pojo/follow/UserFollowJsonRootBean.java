/**
  * Copyright 2022 json.cn 
  */
package github.zimoyin.core.user.pojo.follow;

import github.zimoyin.core.user.pojo.fans.Item;
import lombok.Data;

import java.util.List;

/**
 * Auto-generated: 2022-07-31 18:24:3
 *
 * @author json.cn (i@json.cn)
 * @website http://www.json.cn/java2pojo/
 */
@lombok.Data
public class UserFollowJsonRootBean {

    private int code;
    private String message;
    private int ttl;
    private Data data;


    @lombok.Data
    public static class Data{

        /**
         * 关注列表
         */
        private List<Item> list;
        private int re_version;
        /**
         * 关注总数
         */
        private int total;
    }
}