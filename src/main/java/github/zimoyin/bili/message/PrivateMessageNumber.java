package github.zimoyin.bili.message;

import com.alibaba.fastjson.JSONObject;
import github.zimoyin.bili.cookie.Cookie;
import github.zimoyin.bili.message.pojo.prnum.PrivateMessageNumberJsonRoot;
import github.zimoyin.bili.utils.net.httpclient.HttpClientResult;
import github.zimoyin.bili.utils.net.httpclient.HttpClientUtils;

import java.io.IOException;


/**
 * 私信数
 */
public class PrivateMessageNumber {
    private static final String URL = "http://api.vc.bilibili.com/session_svr/v1/session_svr/single_unread";

    private Cookie cookie;

    public PrivateMessageNumber(Cookie cookie) {
        this.cookie = cookie;
    }

    /**
     * 获取未读信息数
     * @return
     * @throws IOException
     */
    public PrivateMessageNumberJsonRoot getJsonPojo() throws IOException {
        String page = getPage();
        PrivateMessageNumberJsonRoot root = JSONObject.parseObject(page, PrivateMessageNumberJsonRoot.class);
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
