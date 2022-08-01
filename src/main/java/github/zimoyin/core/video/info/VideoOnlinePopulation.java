package github.zimoyin.core.video.info;


import com.alibaba.fastjson.JSONObject;
import github.zimoyin.core.exception.CodeException;
import github.zimoyin.core.utils.IDConvert;
import github.zimoyin.core.utils.net.httpclient.HttpClientUtils;
import github.zimoyin.core.video.info.pojo.online.OnlinePopulationRootBean;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

/**
 * 获取在线人数
 * @API: https://github.com/SocialSisterYi/bilibili-API-collect
 */
public class VideoOnlinePopulation {
    private final String URL = "http://api.bilibili.com/x/player/online/total?&bvid=%s&cid=%s";


    public String getPage(String bv) throws CodeException, IOException, NoSuchAlgorithmException, KeyStoreException, URISyntaxException, KeyManagementException {
        String url = String.format(URL, bv, IDConvert.BvToCID(bv));
        return HttpClientUtils.doGet(url).getContent();
    }

    public OnlinePopulationRootBean getJsonPojo(String bv) throws CodeException, IOException, NoSuchAlgorithmException, KeyStoreException, URISyntaxException, KeyManagementException {
        String page = getPage(bv);
        OnlinePopulationRootBean online = JSONObject.parseObject(page, OnlinePopulationRootBean.class);
        return online;
    }
}
