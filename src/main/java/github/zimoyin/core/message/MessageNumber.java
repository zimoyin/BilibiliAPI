package github.zimoyin.core.message;

import com.alibaba.fastjson.JSONObject;
import github.zimoyin.core.cookie.Cookie;
import github.zimoyin.core.message.pojo.num.MessageNumberJsonRoot;
import github.zimoyin.core.utils.net.httpclient.HttpClientResult;
import github.zimoyin.core.utils.net.httpclient.HttpClientUtils;
import org.apache.http.HttpException;

import java.io.IOException;

/**
 * 未读信息数：点赞、私信、回复等
 */
public class MessageNumber {
    private static final String URL = "http://api.bilibili.com/x/msgfeed/unread";

    private Cookie cookie;

    public MessageNumber(Cookie cookie) {
        this.cookie = cookie;
    }

    /**
     * 获取未读信息数
     * @return
     * @throws IOException
     */
    public MessageNumberJsonRoot getJsonPojo() throws IOException {
        String page = getPage();
        MessageNumberJsonRoot root = JSONObject.parseObject(page, MessageNumberJsonRoot.class);
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
