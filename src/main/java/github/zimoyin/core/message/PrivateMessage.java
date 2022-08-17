package github.zimoyin.core.message;

import com.alibaba.fastjson.JSONObject;
import github.zimoyin.core.cookie.Cookie;
import github.zimoyin.core.cookie.GlobalCookie;
import github.zimoyin.core.exception.CookieNotFoundException;
import github.zimoyin.core.message.pojo.prnum.PrivateMessageNumberJsonRoot;
import github.zimoyin.core.utils.net.httpclient.HttpClientResult;
import github.zimoyin.core.utils.net.httpclient.HttpClientUtils;

import java.io.IOException;
import java.util.HashMap;

/**
 * 私信记录
 */
public class PrivateMessage {
    private static final String URL = "https://api.vc.bilibili.com/svr_sync/v1/svr_sync/fetch_session_msgs";

    private Cookie cookie;

    public PrivateMessage(Cookie cookie) {
        this.cookie = cookie;
    }


    /**
     * 获取信息记录
     *
     * @param uid         聊天对象的UID
     * @return
     * @throws IOException
     */
    public PrivateMessage getJsonPojo(int uid) throws IOException {
        return getJsonPojo(uid,1);
    }

    /**
     * 获取信息记录
     *
     * @param uid         聊天对象的UID
     * @param type        聊天对象的类型：1为用户，2为粉丝团
     * @return
     * @throws IOException
     */
    public PrivateMessage getJsonPojo(int uid, int type) throws IOException {
        return getJsonPojo(uid,type,20);
    }


    /**
     * 获取信息记录
     *
     * @param uid         聊天对象的UID
     * @param type        聊天对象的类型：1为用户，2为粉丝团
     * @param messageSize 列出消息条数（默认20）
     * @return
     * @throws IOException
     */
    public PrivateMessage getJsonPojo(int uid, int type, int messageSize) throws IOException {
        String page = getPage(uid, type, messageSize);
        PrivateMessage root = JSONObject.parseObject(page, PrivateMessage.class);
        return root;
    }

    /**
     * 获取和用户聊天的信息记录，只能获取用户的
     *
     * @param uid 聊天对象的UID
     * @return
     * @throws IOException
     */
    public String getPage(int uid) throws IOException {
        return getPage(uid, 1, 20);
    }


    /**
     * 获取信息记录
     *
     * @param uid  聊天对象的UID
     * @param type 聊天对象的类型：1为用户，2为粉丝团
     * @return
     * @throws IOException
     */
    public String getPage(int uid, int type) throws IOException {
        return getPage(uid, type, 20);
    }

    /**
     * 获取信息记录
     *
     * @param uid         聊天对象的UID
     * @param type        聊天对象的类型：1为用户，2为粉丝团
     * @param messageSize 列出消息条数（默认20）
     * @return
     * @throws IOException
     */
    public String getPage(int uid, int type, int messageSize) throws IOException {
        HashMap<String, String> params = new HashMap<>();
        params.put("talker_id", String.valueOf(uid));
        params.put("session_type", String.valueOf(type));
        params.put("size", String.valueOf(messageSize));
        HttpClientResult result = HttpClientUtils.doGet(URL, cookie.toHeaderCookie(), params);
        return result.getContent();
    }


    public Cookie getCookie() {
        return cookie;
    }

    public void setCookie(Cookie cookie) {
        this.cookie = cookie;
    }
}
