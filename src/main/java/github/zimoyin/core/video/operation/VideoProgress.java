package github.zimoyin.core.video.operation;


import com.alibaba.fastjson.JSONObject;
import github.zimoyin.core.cookie.Cookie;
import github.zimoyin.core.exception.CodeException;

import github.zimoyin.core.utils.net.httpclient.HttpClientUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

/**
 * 视频进度上报
 * @API: https://github.com/SocialSisterYi/bilibili-API-collect
 */
public class VideoProgress {
    /**
     * 双端上报进度，无法实现
     */
    private final String URL_UP_Progress = "http://api.bilibili.com/x/v2/history/report";
    /**
     * 上报心跳，以此来上报进度
     */
    private final String URL_UP_Heartbeat = "http://api.bilibili.com/x/click-interface/web/heartbeat";


    private Cookie cookie;

    HashMap<String, String> header = new HashMap<>();
    HashMap<String, String> params = new HashMap<>();

    public VideoProgress(Cookie cookie) {
        this.cookie = cookie;
        cookie.setCookieToHeader(header);
    }





    /**
     * 上报进度
     * @param bv
     * @param videoTimeS 视频看到的位置（秒）
     * @return
     * @throws CodeException
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws KeyStoreException
     * @throws URISyntaxException
     * @throws KeyManagementException
     */
    public boolean upProgress(String bv, long videoTimeS) throws CodeException, IOException, NoSuchAlgorithmException, KeyStoreException, URISyntaxException, KeyManagementException {
        String result = upProgressPage(bv, videoTimeS);
        int code = JSONObject.parseObject(result).getInteger("code");
        return code == 0;
    }




    /**
     * 上报进度
     * @param bv
     * @param videoTimeS 视频看到的位置（秒）
     * @param watchTimeS 观看时间（秒）
     * @return
     * @throws CodeException
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws KeyStoreException
     * @throws URISyntaxException
     * @throws KeyManagementException
     */
    public boolean upProgress(String bv, long videoTimeS,long watchTimeS) throws CodeException, IOException, NoSuchAlgorithmException, KeyStoreException, URISyntaxException, KeyManagementException {
        String result = upProgressPage(bv, videoTimeS, watchTimeS);
        int code = JSONObject.parseObject(result).getInteger("code");
        return code == 0;
    }

    /**
     * 上报进度
     * @param bv
     * @param videoTimeS 视频看到的位置（秒）
     * @return
     * @throws CodeException
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws KeyStoreException
     * @throws URISyntaxException
     * @throws KeyManagementException
     */
    public String upProgressPage(String bv, long videoTimeS) throws CodeException, IOException, NoSuchAlgorithmException, KeyStoreException, URISyntaxException, KeyManagementException {
        return upProgressPage(bv,videoTimeS,System.currentTimeMillis() / 1000);
    }


    /**
     * 上报进度
     * @param bv
     * @param videoTimeS 视频看到的位置（秒）
     * @param watchTimeS 观看时间（秒）
     * @return
     * @throws CodeException
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws KeyStoreException
     * @throws URISyntaxException
     * @throws KeyManagementException
     */
    public String upProgressPage(String bv, long videoTimeS,long watchTimeS) throws CodeException, IOException, NoSuchAlgorithmException, KeyStoreException, URISyntaxException, KeyManagementException {
//        params.put("SESSDATA", "6e6dcdf1%2C1660278479%2C74358%2A21");
        params.put("csrf", cookie.get("bili_jct"));
        params.put("start_ts", String.valueOf(watchTimeS));//什么时间看的视频（秒）
        params.put("realtime", String.valueOf(videoTimeS));//视频进度 (秒)
        params.put("bvid", bv);
        params.put("played_time", String.valueOf(videoTimeS));//视频进度 (秒)
        params.put("play_type", "0");
        params.put("dt", "2");

        String content = HttpClientUtils.doPost(URL_UP_Heartbeat, header,params,null).getContent();
        return content;
    }
}
