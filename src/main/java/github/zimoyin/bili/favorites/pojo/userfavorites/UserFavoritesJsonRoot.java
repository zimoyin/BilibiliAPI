/**
  * Copyright 2022 json.cn 
  */
package github.zimoyin.bili.favorites.pojo.userfavorites;

import java.util.List;

/**
 * Auto-generated: 2022-08-26 14:56:55
 *
 * @author json.cn (i@json.cn)
 * @website http://www.json.cn/java2pojo/
 */
@lombok.Data
public class UserFavoritesJsonRoot {

    private int code;
    private String message;
    private int ttl;
    private Data data;
    @lombok.Data
    public static class Data{
        /**
         * 创建的收藏夹总数
         */
        private int count;
        /**
         * 创建的收藏夹列表
         */
        private List<FavList> list;
        private String season;
    }
}