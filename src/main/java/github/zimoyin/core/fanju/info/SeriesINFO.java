package github.zimoyin.core.fanju.info;

import com.alibaba.fastjson.JSONObject;
import github.zimoyin.core.cookie.Cookie;
import github.zimoyin.core.cookie.GlobalCookie;
import github.zimoyin.core.exception.CookieNotFoundException;
import github.zimoyin.core.fanju.pojo.info.essential.FanJuEssentialINFOJsonRootBean;
import github.zimoyin.core.fanju.pojo.info.seriesI.Episodes;
import github.zimoyin.core.fanju.pojo.info.seriesI.Result;
import github.zimoyin.core.fanju.pojo.info.seriesI.SeriesJsonRootBean;
import github.zimoyin.core.utils.net.httpclient.HttpClientResult;
import github.zimoyin.core.utils.net.httpclient.HttpClientUtils;
import org.apache.http.HttpException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;

/**
 * 番剧信息
 */
public class SeriesINFO {
    /**
     * season_id   番剧ssid，web端打开番剧，里面有ss开头的一个（restful样式）参数（https://www.bilibili.com/bangumi/play/ss41410）
     * ep_id	剧集epid，将番剧分享后的URL，里面有ed开头的一个（restful样式）参数
     */
    private final String URL = "https://api.bilibili.com/pgc/view/web/season";

    public SeriesINFO() {
    }

    /**
     * 根据传入进来的ep来确定具体某一集
     */
    public Episodes getEpisodes(String id) throws HttpException {
        Episodes episodes0=null;
        SeriesJsonRootBean pojo = this.getPojo(id);
        Result result = pojo.getResult();
        List<Episodes> episodes = result.getEpisodes();
        if (id.contains("ss"))return episodes.get(0);//如果是ssid就返回第一集
        for (Episodes episode : episodes) {
            long ep = episode.getId();
            String ep_id="ep"+ep;
            if (ep_id.equalsIgnoreCase(id)){
                episodes0 = episode;
                break;
            }
        }
        return episodes0;
    }

    /**
     * 获取剧集的基本信息
     *
     * @param id  剧集的ss id，或者ed id。传入参数时注意只要保留id标识如：ed8848
     * @return
     * @throws HttpException
     */
    public SeriesJsonRootBean getPojo(String id) throws HttpException {
        try {
            String page = getPage(id);
            SeriesJsonRootBean json = JSONObject.parseObject(page, SeriesJsonRootBean.class);
            return json;
        } catch (Exception e) {
            throw new HttpException("访问URL失败", e);
        }
    }

    /**
     * 获取用户的信息
     * @param id ssid或edid
     * @return
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws KeyStoreException
     * @throws URISyntaxException
     * @throws KeyManagementException
     */
    public String getPage(String id) throws IOException, NoSuchAlgorithmException, KeyStoreException, URISyntaxException, KeyManagementException {
        String param = "";
        //判断id类型构建参数
        if (id.contains("ep")) {
            id = id.replaceAll("[a-z]|[A-Z]+","");
            param +="?ep_id="+id;
        }else if (id.contains("ss")){
            id = id.replaceAll("[a-z]|[A-Z]+","");
            param +="?season_id="+id;
        }
        //构建URL
        String url = URL +param;
        //访问URL
        HttpClientResult result = HttpClientUtils.doGet(url);
        return result.getContent();
    }
}
