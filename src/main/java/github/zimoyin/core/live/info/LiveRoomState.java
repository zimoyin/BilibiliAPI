package github.zimoyin.core.live.info;

import com.alibaba.fastjson.JSONObject;
import github.zimoyin.core.live.pojo.info.LiveInfoJsonRootBean;
import github.zimoyin.core.live.pojo.state.LiveStateJsonRootBean;
import github.zimoyin.core.utils.net.httpclient.HttpClientResult;
import github.zimoyin.core.utils.net.httpclient.HttpClientUtils;
import org.apache.http.HttpException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;


/**
 * 用户对应的直播间状态，目前接口出于半失效状态
 */
@Deprecated
public class LiveRoomState {
    private static final String URL = "http://api.live.bilibili.com/room/v1/Room/getRoomInfoOld?mid=%s";
    /**
     * 用户的唯一标识 允许通过SearchUser 来获取
     */
    private long mid;

    public LiveRoomState() {
    }

    public LiveRoomState(long mid) {
        this.mid = mid;
    }


    /**
     * 用户对应的直播间状态
     * @param mid 用户的mid
     * @return
     * @throws HttpException
     */
    public LiveStateJsonRootBean getPojo(long mid) throws HttpException {
        String page = null;
        try {
            page = getPage(mid);
        } catch (Exception e) {
            throw new HttpException("访问URL失败",e);
        }
        if (page == null) return null;
        LiveStateJsonRootBean bean = JSONObject.parseObject(page, LiveStateJsonRootBean.class);
        return bean;
    }

    public LiveStateJsonRootBean getPojo() throws HttpException {
        return getPojo(mid);
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
        return getPage(this.mid);
    }

    /**
     * 用户对应的直播间状态
     * @param mid 用户id
     * @return
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws KeyStoreException
     * @throws URISyntaxException
     * @throws KeyManagementException
     */
    public String getPage(long mid) throws IOException, NoSuchAlgorithmException, KeyStoreException, URISyntaxException, KeyManagementException {
        String url = String.format(URL, mid);
        HttpClientResult result = HttpClientUtils.doGet(url);
        return result.getContent();
    }
}
