package github.zimoyin.core.live.barrage;

import com.alibaba.fastjson.JSONObject;
import github.zimoyin.core.live.pojo.barrage.LiveBarrageJsonRootBean;
import github.zimoyin.core.utils.net.httpclient.HttpClientResult;
import github.zimoyin.core.utils.net.httpclient.HttpClientUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

/**
 * 获取直播弹幕，获取最近的20条弹幕【管理员10条，普通用户10条】（如果弹幕太多会被顶掉哦）
 * 注意：如果想实时获取弹幕信息请使用 github.zimoyin.core.live.massage.LiveMassage 进行tcp连接后进行实时获取
 */
public class LiveBarrage {
    private static final String URL = "https://api.live.bilibili.com/xlive/web-room/v1/dM/gethistory?roomid=%s";


    /**
     * 获取弹幕
     * @param roomid
     * @return
     */
    public LiveBarrageJsonRootBean getJsonPojo(long roomid){
        String page = null;
        try {
            page = getPage(roomid);
        } catch (Exception e) {
            throw new RuntimeException("调用接口失败",e);
        }
        LiveBarrageJsonRootBean bean = JSONObject.parseObject(page, LiveBarrageJsonRootBean.class);
        return bean;
    }

    /**
     * 获取弹幕
     * @param roomid
     * @return
     */
    public String getPage(long roomid) throws IOException, NoSuchAlgorithmException, KeyStoreException, URISyntaxException, KeyManagementException {
        String url = String.format(URL, roomid);
        HttpClientResult result = HttpClientUtils.doGet(url);
        return result.getContent();
    }
}
