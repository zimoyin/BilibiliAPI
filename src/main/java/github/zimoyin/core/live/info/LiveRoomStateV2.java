package github.zimoyin.core.live.info;

import com.alibaba.fastjson.JSONObject;
import github.zimoyin.core.live.pojo.state.LiveStateJsonRootBean;
import github.zimoyin.core.live.pojo.statev2.Data;
import github.zimoyin.core.live.pojo.statev2.LiveStateV2JsonRootBean;
import github.zimoyin.core.utils.net.httpclient.HttpClientResult;
import github.zimoyin.core.utils.net.httpclient.HttpClientUtils;
import org.apache.http.HttpException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.function.BiConsumer;


/**
 *  批量查询用户的直播间状态
 */
public class LiveRoomStateV2 {
    private static final String URL = "https://api.live.bilibili.com/room/v1/Room/get_status_info_by_uids?";
    /**
     * 用户的唯一标识 允许通过SearchUser 来获取
     */
    private long [] mid;

    public LiveRoomStateV2() {
    }

    public LiveRoomStateV2(long... mid) {
        this.mid = mid;
    }


    /**
     * 用户对应的直播间状态
     * @param mid 用户的mid
     * @return
     * @throws HttpException
     */
    public LiveStateV2JsonRootBean getPojo(long... mid) throws HttpException {
        String page = null;
        try {
            page = getPage(mid);
        } catch (Exception e) {
            throw new HttpException("访问URL失败",e);
        }
        if (page == null) return null;
        LiveStateV2JsonRootBean bean = JSONObject.parseObject(page, LiveStateV2JsonRootBean.class);
        return bean;
    }

    public LiveStateV2JsonRootBean getPojo() throws HttpException {
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
     * @param mids 用户id
     * @return
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws KeyStoreException
     * @throws URISyntaxException
     * @throws KeyManagementException
     */
    public String getPage(long...mids) throws IOException, NoSuchAlgorithmException, KeyStoreException, URISyntaxException, KeyManagementException {
        String url = String.format(URL, mid);
        for (long mid : mids) {
            url = url +"uids[]="+mid+"&";
        }
        HttpClientResult result = HttpClientUtils.doGet(url);
        return result.getContent();
    }
}
