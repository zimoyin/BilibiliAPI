/**
 * Copyright 2022 json.cn
 */
package github.zimoyin.core.video.info.pojo.info;

import github.zimoyin.core.exception.CodeException;
import github.zimoyin.core.video.info.VideoOnlinePopulation;
import github.zimoyin.core.video.info.pojo.info.data.*;
import github.zimoyin.core.video.info.pojo.info.data.subtitle.Subtitle;
import github.zimoyin.core.video.info.pojo.info.data.Dimension;
import github.zimoyin.core.video.info.pojo.info.data.Pages;
import github.zimoyin.core.video.info.pojo.info.data.staff.StaffRoot;
import github.zimoyin.core.video.info.pojo.online.OnlinePopulationRootBean;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

/**
 * Auto-generated: 2022-07-16 13:2:38
 *
 * @author json.cn (i@json.cn)
 * @website http://www.json.cn/java2pojo/
 */

@lombok.Data
public class Data {

    /**
     * 稿件bvid
     */
    private String bvid;
    /**
     * 稿件avid
     */
    private long aid;
    /**
     * 稿件分P总数
     */
    private int videos;

    /**
     * 分区tid，参考表：https://github.com/SocialSisterYi/bilibili-API-collect/blob/master/video/video_zone.md
     */
    private int tid;
    /**
     * 分区名称
     */
    private String tname;
    /**
     * 视频类型
     * 1:原创
     * 2：转载
     */
    private int copyright;
    /**
     * 稿件封面图片url
     */
    private URL pic;
    /**
     * 稿件标题
     */
    private String title;
    /**
     * 稿件发布时间 时间戳
     */
    private long pubdate;
    /**
     * 用户投稿时间 时间戳
     */
    private long ctime;
    /**
     * 视频简介
     */
    private String desc;
    /**
     * 新版视频简介
     */
    private List<Desc_v2> desc_v2;
    /**
     * 视频状态 见state常量表<br>
     * 1	橙色通过<br>
     * 0	开放浏览<br>
     * -1	待审<br>
     * -2	被打回<br>
     * -3	网警锁定<br>
     * -4	被锁定	:视频撞车了<br>
     * -5	管理员锁定<br>
     * -6	修复待审<br>
     * -7	暂缓审核<br>
     * -8	补档待审<br>
     * -9	等待转码<br>
     * -10	延迟审核<br>
     * -11	视频源待修<br>
     * -12	转储失败<br>
     * -13	允许评论待审<br>
     * -14	临时回收站<br>
     * -15	分发中<br>
     * -16	转码失败<br>
     * -20	创建未提交<br>
     * -30	创建已提交<br>
     * -40	定时发布<br>
     * -100	用户删除	<br>
     */
    private int state;
    /**
     * 稿件总时长(所有分P) 单位为秒
     */
    private long duration;

    /**
     * 撞车视频跳转avid,仅撞车视频存在此字段
     */
    private long forward;

    /**
     * 稿件参与的活动id
     */
    private long mission_id;

    /**
     * 重定向url
     * 仅番剧或影视视频存在此字段
     * 用于番剧&影视的av/bv->ep
     */
    private URL redirect_url;

    /**
     * 视频属性标志
     */
    private Rights rights;
    /**
     * 视频UP主信息
     */
    private Owner owner;
    /**
     * 视频状态数(点赞、弹幕数量、分析之类的)
     */
    private Stat stat;
    /**
     * 视频同步发布的的动态的文字内容
     */
    private String dynamic;
    /**
     * 视频1P cid
     */
    private long cid;
    /**
     * 视频1P分辨率
     */
    private Dimension dimension;
    private String premiere;
    private int teenage_mode;
    private boolean is_chargeable_season;
    /**
     * true	作用尚不明确: 推测 为true代表为互动视频
     */
    private boolean no_cache;
    /**
     * 视频分P列表
     */
    private List<Pages> pages;
    /**
     * 视频CC字幕信息
     */
    private Subtitle subtitle;
    /**
     * 作用不明，推测为TRUE 为普通视频，为FALSE为互动视频
     */
    private boolean is_season_display;
    /**
     * 用户装扮信息
     */
    private User_garb user_garb;
    private Honor_reply honor_reply;

    /**
     * 合作成员列表	非合作视频无此项
     */
    private ArrayList<StaffRoot> staff;




    /****************************************** 其他接口的字段合并到这里   *************************************************/
    private String short_link;
    private String short_link_v2;
    private String first_frame;
    private String pub_location;
    private int season_type;
    private boolean is_ogv;
    private String ogv_info;
    private String rcmd_reason;
    /****************************************** 其他POJO传入   *************************************************/
    /**
     * 在线人数
     */
    private OnlinePopulationRootBean online;

    /**
     * 在线人数
     */
    public OnlinePopulationRootBean getOnline(){
        OnlinePopulationRootBean jsonPojo = null;
        try {
            jsonPojo = new VideoOnlinePopulation().getJsonPojo(this.getBvid());
        } catch (CodeException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (KeyStoreException e) {
            throw new RuntimeException(e);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        } catch (KeyManagementException e) {
            throw new RuntimeException(e);
        }
        this.online = jsonPojo;
        return online;
    }
}