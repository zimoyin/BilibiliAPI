/**
 * Copyright 2022 json.cn
 */
package github.zimoyin.core.music.list.pojo.list.my;

import github.zimoyin.core.music.list.pojo.list.origin.Audios;
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
    private long id=-1;
    /**
     * 创建用户mid
     */
    private long uid=-1;
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
    private int type=-1;
    /**
     * 是否公开
     * 0：不公开
     * 1：公开
     */
    private int published=-1;
    /**
     * 歌单封面图片url
     */
    private String cover;
    /**
     * 歌单创建时间	时间戳
     */
    private long ctime=-1;
    /**
     * 歌单中的音乐数量
     */
    private int song=-1;
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
    private long menuId=-1;
    /**
     * 歌单状态数信息
     */
    private Statistic statistic;


    private String intro;
    /**
     * 歌单是否公开
     * 0：公开
     * 1：私密
     */
    private int off=-1;
    /**
     * 当前时间
     */
    private long curtime=-1;
    /**
     * 歌单包含歌曲个数
     */
    private int snum=-1;
    private int attr=-1;
    private int isDefault=-1;
    private int collectionId=-1;


    public void setIntro(String intro) {
        this.intro = intro;
        this.desc = intro;
    }

    public void setOff(int off) {
        this.off = off;
        if (off == 0) {
            this.published = 1;
        } else if (off == 1) {
            this.published = 0;
        }
    }

    /**
     * 歌单中的音乐信息(部分),查询热门榜单才有的字段
     */
    private List<Audios> audios;
}