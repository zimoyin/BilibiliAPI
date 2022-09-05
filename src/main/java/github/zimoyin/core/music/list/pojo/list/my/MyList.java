/**
  * Copyright 2022 json.cn 
  */
package github.zimoyin.core.music.list.pojo.list.my;
import lombok.Data;

import java.util.List;

/**
 * Auto-generated: 2022-09-03 17:8:38
 *
 * @author json.cn (i@json.cn)
 * @website http://www.json.cn/java2pojo/
 */
@Data
public class MyList {

    /**
     * 音频收藏夹mlid
     */
    private long id;
    /**
     * 创建用户mid
     */
    private long uid;
    /**
     * 创建用户昵称
     */
    private String uname;
    /**
     * 歌单标题
     */
    private String title;
    /**
     * 收藏夹属性
     * 0：普通收藏夹
     * 1：默认收藏夹
     */
    private int type;
    /**
     * 是否公开
     * 0：不公开
     * 1：公开
     */
    private int published;
    /**
     * 歌单封面图片url
     */
    private String cover;
    /**
     * 歌单创建时间	时间戳
     */
    private long ctime;
    /**
     * 歌单中的音乐数量
     */
    private int song;
    /**
     * 歌单备注信息
     */
    private String desc;
    /**
     * 歌单中的音乐
     */
    private List<String> sids;
    /**
     * 音频收藏夹对应的歌单amid
     */
    private long menuId;
    /**
     * 歌单状态数信息
     */
    private Statistic statistic;
}