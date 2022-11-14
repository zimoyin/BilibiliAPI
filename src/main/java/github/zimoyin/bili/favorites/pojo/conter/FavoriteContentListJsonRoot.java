/**
  * Copyright 2022 json.cn 
  */
package github.zimoyin.bili.favorites.pojo.conter;

import github.zimoyin.bili.favorites.pojo.info.FavoriteInfoJsonRoot;

import java.util.List;

/**
 * Auto-generated: 2022-08-26 19:59:57
 *
 * @author json.cn (i@json.cn)
 * @website http://www.json.cn/java2pojo/
 */
@lombok.Data
public class FavoriteContentListJsonRoot {

    private int code;
    private String message;
    private int ttl;
    private Data data;

    @lombok.Data
    public static class Data{

        /**
         * 收藏夹元数据
         */
        private FavoriteInfoJsonRoot.Data  info;
        /**
         * 收藏夹内容
         */
        private List<Medias> medias;
        private boolean has_more;
    }

}