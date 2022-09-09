/**
  * Copyright 2022 json.cn 
  */
package github.zimoyin.core.favorites.pojo.info;

/**
 * Auto-generated: 2022-08-26 15:26:48
 *
 * @author json.cn (i@json.cn)
 * @website http://www.json.cn/java2pojo/
 */
@lombok.Data
public class FavoriteInfoJsonRoot {

    private int code;
    private String message;
    private int ttl;
    private Data data;

    @lombok.Data
    public static class Data{

        /**
         * '收藏夹mlid（完整id）	收藏夹原始id+创建者mid尾号2位
         */
        private long id;
        /**
         * 收藏夹原始id
         */
        private long fid;
        /**
         * 创建者mid
         */
        private long mid;
        /**
         * 0：正常(具体收藏标注)
         * 1：失效(具体收藏标注)
         *
         * 1:默认收藏夹
         * 22：公开的视频收藏夹
         * 23：私有的视频收藏夹
         */
        private int attr;
        /**
         * 收藏夹标题
         */
        private String title;
        /**
         * 收藏夹封面图片url
         */
        private String cover;
        /**
         * 创建者信息
         */
        private Upper upper;
        /**
         * 封面图类别（？）
         */
        private int cover_type;
        /**
         * 收藏夹状态数
         */
        private Cnt_info cnt_info;
        /**
         * 类型（？）	一般是11
         */
        private int type;
        /**
         * 备注
         */
        private String intro;
        /**
         * 创建时间	时间戳
         */
        private long ctime;
        /**
         * 收藏时间	时间戳
         */
        private long mtime;
        /**
         * 	状态（？）	一般为0
         */
        private int state;
        /**
         * 收藏夹收藏状态
         * 已收藏收藏夹：1
         * 未收藏收藏夹：0
         * 需要登录
         */
        private int fav_state;
        /**
         * 点赞状态
         * 已点赞：1
         * 未点赞：0
         * 需要登录
         */
        private int like_state;
        /**
         * 收藏夹内容数量
         */
        private int media_count;
    }
}