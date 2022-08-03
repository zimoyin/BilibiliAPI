package github.zimoyin.core.live.massage;

import com.alibaba.fastjson.JSONObject;
import github.zimoyin.core.live.pojo.message.host.DanmuInfoJsonRootBean;
import github.zimoyin.core.utils.net.httpclient.HttpClientResult;
import github.zimoyin.core.utils.net.httpclient.HttpClientUtils;
import lombok.Data;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

/**
 * 直播信息流：包含直播弹幕信息
 */
@Data
public class MassageStreamInfo {
    private final String URL = "http://api.live.bilibili.com/xlive/web-room/v1/index/getDanmuInfo?id=%s";
    /**
     * 直播间ID
     */
    private String id;

    public MassageStreamInfo(){}

    public MassageStreamInfo(String id){
        this.id = id;
    }

    /**
     * 获取直播间认证
     * @param id 直播间ID
     * @return
     */
    public DanmuInfoJsonRootBean getJsonPojo(long id){
        String page = null;
        try {
            page = getPage(id);
        } catch (Exception e) {
            throw new RuntimeException("访问URL失败",e);
        }
        DanmuInfoJsonRootBean bean = JSONObject.parseObject(page, DanmuInfoJsonRootBean.class);
        return bean;
    }


    /**
     * 获取原始信息：获取认证信息
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
        HttpClientResult result = HttpClientUtils.doGet(url);
        return result.getContent();
    }
}
