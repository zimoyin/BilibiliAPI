package github.zimoyin.core.message;

import com.alibaba.fastjson.JSONObject;
import github.zimoyin.core.cookie.Cookie;
import github.zimoyin.core.utils.JsonSerializeUtil;
import github.zimoyin.core.utils.net.httpclient.HttpClientResult;
import github.zimoyin.core.utils.net.httpclient.HttpClientUtils;

import java.io.IOException;
import java.util.HashMap;

/**
 * 发送私信
 */
public class SendMessage {
    private static final String URL = "http://api.vc.bilibili.com/web_im/v1/web_im/send_msg";
    private Cookie cookie;
    private String page;

    public SendMessage(Cookie cookie) {
        this.cookie = cookie;
    }

    public Cookie getCookie() {
        return cookie;
    }

    public void setCookie(Cookie cookie) {
        this.cookie = cookie;
    }
    /**
     * 判断发送的信息是否成功
     *
     * @return
     */
    public String isSuccess() {
        return String.valueOf(JsonSerializeUtil.getJsonPath().read(page, "code").equals("0"));
    }

    /**
     * 判断发送的信息是否成功
     *
     * @param page 你刚刚发送信息后的返回值
     * @return
     */
    public String isSuccess(String page) {
        return String.valueOf(JsonSerializeUtil.getJsonPath().read(page, "code").equals("0"));
    }

    /**
     * 获取你发送的信息的key
     * @return
     */
    public String getMsgKey() {
        return JsonSerializeUtil.getJsonPath().read(page, "/data/msg_key");
    }

    /**
     * 获取你发送的信息的key
     *
     * @param page 你刚刚发送信息后的返回值
     * @return
     */
    public String getMsgKey(String page) {
        return JsonSerializeUtil.getJsonPath().read(page, "/data/msg_key");
    }

    /**
     * 发送信息
     *
     * @param sendID  发送者mid
     * @param replyID 接收者mid
     * @param msgType 消息类型:
     *                1:发送文字
     *                2:发送图片
     *                5:撤回消息
     * @param msg     消息内容：
     *                发送文字时：传入文字,
     *                发送图片时：传入图片的URL，
     *                撤回消息时：传入信息的 msg_key(服务器给出的值)
     * @return
     */
    public String getPage(int sendID, int replyID, int msgType, String msg) throws IOException {
        return getPage(sendID, replyID, msgType, getMsgID(), (int) (System.currentTimeMillis() / 1000), msg);
    }

    /**
     * 发送信息
     *
     * @param sendID  发送者mid
     * @param replyID 接收者mid
     * @param msgType 消息类型:
     *                1:发送文字
     *                2:发送图片
     *                5:撤回消息
     * @param msgID   msgID
     * @param time    时间戳（秒）
     * @param msg     消息内容：
     *                发送文字时：传入文字,
     *                发送图片时：传入图片的URL，
     *                撤回消息时：传入信息的 msg_key(服务器给出的值)
     * @return
     */
    public String getPage(int sendID, int replyID, int msgType, String msgID, int time, String msg) throws IOException {
        HashMap<String, String> params = new HashMap<>();
        params.put("msg[sender_uid]", String.valueOf(sendID));
        params.put("msg[receiver_id]", String.valueOf(replyID));
        params.put("msg[receiver_type]", String.valueOf(1));
        params.put("msg[msg_type]", String.valueOf(msgType));
        params.put("msg[timestamp]", String.valueOf(time));
        params.put("msg[dev_id]", msgID);
        params.put("msg[content]", buildMessage(msgType, msg));
        params.put("csrf", cookie.getCsrf());

        HttpClientResult result = HttpClientUtils.doPost(URL, cookie.toHeaderCookie(), params);
        String content = result.getContent();
        this.page = content;
        return content;
    }

    /**
     * 构建信息
     *
     * @param type    信息类型
     * @param message 信息
     * @return
     */
    private String buildMessage(int type, String message) {
        JSONObject msgObj = new JSONObject();
        switch (type) {
            case 1:
                msgObj.put("content", message);
                break;
            case 2:
                msgObj.put("url", message);
                break;
            case 5:
                return message;
        }
        return msgObj.toString();
    }

    /**
     * 获取信息的msg id
     *
     * @return
     */
    public String getMsgID() {
        char[] b = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        char[] s = "xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx".toCharArray();
        for (int i = 0; i < s.length; i++) {
            if ('-' == s[i] || '4' == s[i]) {
                continue;
            }
            int randomInt = (int) (16 * Math.random());
            if ('x' == s[i]) {
                s[i] = b[randomInt];
            } else {
                s[i] = b[3 & randomInt | 8];
            }
        }
        return new String(s);
    }
}
