/**
  * Copyright 2022 json.cn 
  */
package github.zimoyin.core.favorites.pojo.userfavorites;

import lombok.Data;

/**
 * Auto-generated: 2022-08-26 14:56:55
 *
 * @author json.cn (i@json.cn)
 * @website http://www.json.cn/java2pojo/
 */
@Data
public class FavList {
    /**
     * 收藏夹mlid（完整id）	收藏夹原始id+创建者mid尾号2位
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
     * 属性位（？）
     */
    private int attr;
    /**
     * 收藏夹标题
     */
    private String title;
    /**
     * 目标id是否存在于该收藏夹
     * 存在于该收藏夹：1
     * 不存在于该收藏夹：0
     */
    private int fav_state;
    /**
     * 收藏夹内容数量
     */
    private int media_count;
}