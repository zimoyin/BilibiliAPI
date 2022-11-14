package github.zimoyin.bili.message;

import com.alibaba.fastjson.JSONObject;
import github.zimoyin.bili.cookie.Cookie;
import github.zimoyin.bili.message.pojo.group.MessageGroupJson;
import github.zimoyin.bili.utils.net.httpclient.HttpClientResult;
import github.zimoyin.bili.utils.net.httpclient.HttpClientUtils;

import java.io.IOException;

/**
 * 获取所有的信息组：获取所有与我私信过的人的信息与最近一条信息
 */
public class PrivateMessageGroup {
    /**
     * session_type 大概是私信页数
     */
    private static final String URL = "https://api.vc.bilibili.com/session_svr/v1/session_svr/get_sessions?session_type=1";


    private Cookie cookie;

    public PrivateMessageGroup(Cookie cookie) {
        this.cookie = cookie;
    }

    /**
     * 获取未读信息数
     * @return
     * @throws IOException
     */
    public MessageGroupJson getJsonPojo() throws IOException {
        String page = getPage();
        MessageGroupJson root = JSONObject.parseObject(page, MessageGroupJson.class);
        return root;
    }

    /**
     * 获取未读信息数原始信息
     * @return
     * @throws IOException
     */
    public String getPage() throws IOException {
        HttpClientResult result = HttpClientUtils.doGet(URL, cookie.toHeaderCookie(), null);
        return result.getContent();
    }

    public Cookie getCookie() {
        return cookie;
    }

    public void setCookie(Cookie cookie) {
        this.cookie = cookie;
    }
}
