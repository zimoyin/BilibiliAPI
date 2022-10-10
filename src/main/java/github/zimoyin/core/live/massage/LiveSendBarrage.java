package github.zimoyin.core.live.massage;

import github.zimoyin.core.cookie.Cookie;
import github.zimoyin.core.cookie.GlobalCookie;
import github.zimoyin.core.exception.CookieNotFoundException;
import github.zimoyin.core.utils.JsonSerializeUtil;
import github.zimoyin.core.utils.net.httpclient.HttpClientResult;
import github.zimoyin.core.utils.net.httpclient.HttpClientUtils;
import org.apache.logging.log4j.LogManager;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

/**
 * 发送直播弹幕
 */
public class LiveSendBarrage {
//    public final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final org.apache.logging.log4j.Logger logger = LogManager.getLogger(this.getClass());
    private final String url = "https://api.live.bilibili.com/msg/send";
    Map<String, String> paramMap = new HashMap<String, String>();

    private Cookie cookie;

    public LiveSendBarrage(Cookie cookie) {
        this.cookie = cookie;
    }

    /**
     * 发送弹幕，注意需要登录
     * @param roomid 房间号
     * @param msg 发送的信息
     * @return
     */
    public boolean sendBarrage(String roomid, String msg) {
        String result = null;
        try {
            //转换字符串到URLEncoder
            msg = URLEncoder.encode(msg, "UTF-8");
            //封装弹幕颜色
            paramMap.put("color", "16777215");
            //封装弹幕字体大小
            paramMap.put("fontsize", "25");
            //封装弹幕模式
            paramMap.put("mode", "1");
            //封装弹幕消息
            paramMap.put("msg", msg);
            //封装房间号
            paramMap.put("roomid", roomid);
            //封装Rnd 当前时间戳 单位(s)
            paramMap.put("rnd", String.valueOf(System.currentTimeMillis() / 1000));
            //封装csrf_token = bili_jct=
            paramMap.put("csrf_token", cookie.getCsrf());
            //封装csrf
            paramMap.put("csrf", cookie.getCsrf());
            //设置Cookie,请求头
            HashMap<String, String> header = cookie.toHeaderCookie();
            //获取返回值
            HttpClientResult result0 = HttpClientUtils.doPost(url, header,paramMap, null);
            result=result0.getContent();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        String code = JsonSerializeUtil.getJsonPath().read(result, "/code/");
        if ("0".equals(code)) return true;
        return false;
    }
}
