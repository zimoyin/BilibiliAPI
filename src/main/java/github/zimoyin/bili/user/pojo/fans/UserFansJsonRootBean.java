/**
  * Copyright 2022 json.cn 
  */
package github.zimoyin.bili.user.pojo.fans;

import java.util.List;

/**
 * Auto-generated: 2022-07-31 17:24:59
 *
 * @author json.cn (i@json.cn)
 * @website http://www.json.cn/java2pojo/
 */
@lombok.Data
public class UserFansJsonRootBean {

    private int code;
    private String message;
    private int ttl;
    private Data data;


    @lombok.Data
    public static class Data{

        /**
         * 具体粉丝详细列表
         */
        private List<Item> list;
        private int re_version;
        /**
         * 粉丝总数
         */
        private int total;

    }
}