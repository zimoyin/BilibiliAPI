package github.zimoyin.core.live.massage;

import com.alibaba.fastjson.JSONObject;
import github.zimoyin.core.cookie.Cookie;
import github.zimoyin.core.live.pojo.message.host.DanmuInfoJsonRootBean;
import github.zimoyin.core.utils.net.httpclient.HttpClientResult;
import github.zimoyin.core.utils.net.httpclient.HttpClientUtils;
import lombok.Data;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

/**
 * 直播信息流：包含直播弹幕信息
 */
@Data
public class MassageStreamInfo {
    private final String URL = "http://api.live.bilibili.com/xlive/web-room/v1/index/getDanmuInfo?id=%s";
    /**
     * 直播间ID
     */
    private long roomid;
    /**
     * 与mid绑定
     */
    private Cookie cookie;
    /**
     * 用户id 与cookie绑定
     */
    private long mid;

    HashMap<String, String> header = new HashMap<>();

    public MassageStreamInfo() {
    }

    public MassageStreamInfo(Cookie cookie, long mid) {
        this.cookie = cookie;
        cookie.toHeaderCookie(header);
    }

    public MassageStreamInfo(long roomid) {
        this.roomid = roomid;
    }


    /**
     * 获取直播间认证
     * @return
     */
    public DanmuInfoJsonRootBean getJsonPojo() {
        return getJsonPojo(this.roomid);
    }


    /**
     * 获取直播间认证
     *
     * @param id 直播间ID
     * @return
     */
    public DanmuInfoJsonRootBean getJsonPojo(long roomid) {
        String page = null;
        try {
            page = getPage(roomid);
        } catch (Exception e) {
            throw new RuntimeException("访问URL失败", e);
        }
        DanmuInfoJsonRootBean bean = JSONObject.parseObject(page, DanmuInfoJsonRootBean.class);
        bean.setMid(this.mid);
        bean.setRoomID(roomid);
        return bean;
    }


    /**
     * 获取原始信息：获取认证信息
     *
     * @param id 直播间ID
     * @return
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws KeyStoreException
     * @throws URISyntaxException
     * @throws KeyManagementException
     */
    public String getPage(long id) throws IOException, NoSuchAlgorithmException, KeyStoreException, URISyntaxException, KeyManagementException {
        String url = String.format(URL, id);
        if (cookie != null) cookie.toHeaderCookie(header);
        HttpClientResult result = HttpClientUtils.doGet(url, header, null);
        return result.getContent();
    }
}
