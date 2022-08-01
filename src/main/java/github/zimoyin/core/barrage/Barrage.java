package github.zimoyin.core.barrage;

import github.zimoyin.core.Incompleteness;
import github.zimoyin.core.barrage.barrage.video.HistoryBarrage;
import github.zimoyin.core.barrage.barrage.video.RealTimeBarrage;
import github.zimoyin.core.barrage.barrage.video.RecentBarrage;
import github.zimoyin.core.barrage.barrage.video.SnapshotBarrage;
import github.zimoyin.core.cookie.Cookie;
import github.zimoyin.core.cookie.GlobalCookie;
import github.zimoyin.core.exception.CookieNotFoundException;

/**
 * 弹幕操作类
 * 还要包括删除，点赞等
 */
@Deprecated
@Incompleteness
public class Barrage {
    private Cookie cookie;
    private String bv;


    public Barrage() {
        try {
            cookie = GlobalCookie.getInstance().getCookie();
        } catch (CookieNotFoundException e) {
            throw new RuntimeException("无法获取到全局Cookie，请手动指定Cookie",e);
        }
    }

    public Barrage(Cookie cookie) {
        this.cookie = cookie;
    }

    public Barrage(Cookie cookie, String bv) {
        this.cookie = cookie;
        this.bv = bv;
    }

    public Cookie getCookie() {
        return cookie;
    }

    public void setCookie(Cookie cookie) {
        this.cookie = cookie;
    }

    public String getBv() {
        return bv;
    }

    public void setBv(String bv) {
        this.bv = bv;
    }

    /**
     * 获取历史弹幕实例
     * @return
     */
    public HistoryBarrage getHistoryBarrage(){
        return new HistoryBarrage(cookie);
    }

    /**
     * 获取实时弹幕实例
     * @return
     */
    public RealTimeBarrage getRealTimeBarrage(){
        return new RealTimeBarrage(cookie);
    }


    /**
     * 获取快照弹幕实例
     * @return
     */
    public SnapshotBarrage getSnapshotBarrage(){
        return new SnapshotBarrage();
    }


    /**
     * 获取近期弹幕实例
     * @return
     */
    public RecentBarrage getRecentBarrage(){
        return new RecentBarrage();
    }
}
