package github.zimoyin.core.live.info;

import com.alibaba.fastjson.JSONObject;
import github.zimoyin.core.cookie.Cookie;
import github.zimoyin.core.live.pojo.info.LiveInfoJsonRootBean;
import github.zimoyin.core.user.pojo.user.UserRootBean;
import github.zimoyin.core.utils.net.httpclient.HttpClientResult;
import github.zimoyin.core.utils.net.httpclient.HttpClientUtils;
import org.apache.http.HttpException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;


/**
 * 直播间信息
 */
public class LiveInfo {
    private static final String URL = "http://api.live.bilibili.com/room/v1/Room/room_init?id=%s";
    /**
     * 用户的唯一标识 允许通过SearchUser 来获取
     */
    private long roomID;

    public LiveInfo() {
    }

    public LiveInfo(long roomid) {
        this.roomID = roomid;
    }


    /**
     * 获取直播间信息
     * @param roomid 直播间房间ID
     * @return
     * @throws HttpException
     */
    public LiveInfoJsonRootBean getPojo(long roomid) throws HttpException {
        String page = null;
        try {
            page = getPage(roomid);
        } catch (Exception e) {
            throw new HttpException("访问URL失败",e);
        }
        if (page == null) return null;
        LiveInfoJsonRootBean bean = JSONObject.parseObject(page, LiveInfoJsonRootBean.class);
        return bean;
    }

    public LiveInfoJsonRootBean getPojo() throws HttpException {
        return getPojo(roomID);
    }

    /**
     * 直播间信息
     * @return
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws KeyStoreException
     * @throws URISyntaxException
     * @throws KeyManagementException
     */
    public String getPage() throws IOException, NoSuchAlgorithmException, KeyStoreException, URISyntaxException, KeyManagementException {
        return getPage(this.roomID);
    }

    /**
     * 直播间信息
     * @param roomid 直播间房间ID
     * @return
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws KeyStoreException
     * @throws URISyntaxException
     * @throws KeyManagementException
     */
    public String getPage(long roomid) throws IOException, NoSuchAlgorithmException, KeyStoreException, URISyntaxException, KeyManagementException {
        String url = String.format(URL, roomid);
        HttpClientResult result = HttpClientUtils.doGet(url);
        return result.getContent();
    }
}
