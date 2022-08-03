package github.zimoyin.core.live.manage;

import github.zimoyin.core.Incompleteness;
import github.zimoyin.core.cookie.Cookie;
import github.zimoyin.core.cookie.GlobalCookie;
import github.zimoyin.core.exception.CookieNotFoundException;
import github.zimoyin.core.utils.net.httpclient.HttpClientUtils;

import java.io.IOException;
import java.util.HashMap;

/**
 * 更新直播间标题
 * 开始直播
 * 关闭直播
 * 详细返回值列表：https://github.com/SocialSisterYi/bilibili-API-collect/blob/master/live/manage.md
 */
@Incompleteness
public class LiveManage {
    private static final String URL_TITLE = "http://api.live.bilibili.com/room/v1/Room/update";
    private static final String URL_OPEN= "http://api.live.bilibili.com/room/v1/Room/startLive";
    private static final String URL_CLOSE= "http://api.live.bilibili.com/room/v1/Room/stopLive";

    private Cookie cookie;
    private String room_id;
    private HashMap<String, String> headers = new HashMap<String, String>();

    public LiveManage() {
    }

    /**
     * @param cookie
     */
    public LiveManage(Cookie cookie) {
        this.cookie = cookie;
        headers.clear();
        cookie.toHeaderCookie(headers);
    }


    public void setCookie(Cookie cookie) {
        this.cookie = cookie;
        headers.clear();
        cookie.toHeaderCookie(headers);
    }

    public Cookie getCookie() {
        return cookie;
    }


    /**
     * 关闭直播
     * @return
     * @throws IOException
     */
    public String closeLive() throws IOException {
        return closeLive(room_id);
    }


    /**
     * 关闭直播
     * @param room_id
     * @return
     * @throws IOException
     */
    public String closeLive(String room_id) throws IOException {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("csrf", cookie.getCsrf());
        params.put("room_id", room_id);

        String content = HttpClientUtils.doPost(URL_CLOSE, headers, params, null).getContent();
        return content;
    }


    /**
     * 开启直播
     * @param area_v2 直播分区id（子分区id）
     * @param platform 直播平台
     *                 web端：空字符串或null
     *                 bililink：android_link
     */
    public String openLive(String area_v2,String platform) throws IOException {
        return openLive(room_id, area_v2, platform);
    }



    /**
     * 开启直播
     * @param room_id 直播间id
     * @param area_v2 直播分区id（子分区id）
     * @param platform 直播平台
     *                 web端：空字符串或null
     *                 bililink：android_link
     */
    public String openLive(String room_id,String area_v2,String platform) throws IOException {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("csrf", cookie.getCsrf());
        params.put("room_id", room_id);
        params.put("area_v2", area_v2);
        params.put("platform", platform);

        String content = HttpClientUtils.doPost(URL_OPEN, headers, params, null).getContent();
        return content;
    }



    /**
     * 更新标题
     * @param title 标题
     * @return 返回一个json
     * @Code 0：成功；65530：token错误（登录错误）；1：错误
     * @throws IOException
     */
    public String update(String title) throws IOException {
        return update(this.room_id,title);
    }


    /**
     * 更新标题
     * @param roomid 房间号
     * @param title 标题
     * @return 返回一个json
     * @Code 0：成功；65530：token错误（登录错误）；1：错误
     * @throws IOException
     */
    public String update(String roomid, String title) throws IOException {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("csrf", cookie.getCsrf());
        params.put("title", title);
        params.put("room_id", roomid);

        String content = HttpClientUtils.doPost(URL_TITLE, headers, params, null).getContent();
        return content;
    }
}
