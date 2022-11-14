package github.zimoyin.bili.music.url;

import com.alibaba.fastjson.JSONObject;
import github.zimoyin.bili.cookie.Cookie;
import github.zimoyin.bili.music.url.pojo.url.MusicURLJsonRoot;
import github.zimoyin.bili.utils.net.httpclient.HttpClientResult;
import github.zimoyin.bili.utils.net.httpclient.HttpClientUtils;

import java.io.IOException;
import java.util.HashMap;

/**
 * 获取音乐的播放流
 * 可以获取付费音频，但是需要cookie,还有个获取url的api就不写了
 */
public class MusicURLFormat {
    private static final  String URL ="http://api.bilibili.com/audio/music-service-c/url";
    private Cookie cookie;

    public MusicURLFormat() {
    }

    public MusicURLFormat(Cookie cookie) {
        this.cookie = cookie;
    }


    /**
     * 获取音乐的播放流
     * @param sid  音频auid
     * @return
     * @throws IOException
     */
    public MusicURLJsonRoot getJsonPojo(long sid) throws IOException {
        String page = getPage(sid);
        MusicURLJsonRoot musicURLJsonRoot = JSONObject.parseObject(page, MusicURLJsonRoot.class);
        return musicURLJsonRoot;
    }
    /**
     * 获取音乐的播放流
     * @param sid  音频auid
     * @param mid 当前用户mid,可为任意值
     * @param qn 音质代码
     * @return
     * @throws IOException
     */
    public MusicURLJsonRoot getJsonPojo(long sid,long mid,QN qn) throws IOException {
        String page = getPage(sid, mid, qn);
        MusicURLJsonRoot musicURLJsonRoot = JSONObject.parseObject(page, MusicURLJsonRoot.class);
        return musicURLJsonRoot;
    }

    /**
     * 获取音乐的播放流
     * @param sid  音频auid
     * @return
     * @throws IOException
     */
    public String getPage(long sid) throws IOException {
        return  getPage(sid,255,QN.High);
    }

    /**
     * 获取音乐的播放流
     * @param sid  音频auid
     * @param mid 当前用户mid,可为任意值
     * @param qn 音质代码
     * @return
     * @throws IOException
     */
    public String getPage(long sid,long mid,QN qn) throws IOException {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("songid", String.valueOf(sid));
        params.put("quality", String.valueOf(qn.getQn()));
        params.put("privilege", String.valueOf(2));
        params.put("mid", String.valueOf(mid));
        params.put("platform", "web");

        HttpClientResult httpClientResult = HttpClientUtils.doGet(URL, cookie != null ? cookie.toHeaderCookie() : null, params);
        return httpClientResult.getContent();
    }

    public static enum QN{
        Low(0),
        Medium(1),
        High(2),
        Vip(3);
        private int qn;

        QN(int qn) {
            this.qn = qn;
        }

        public int getQn() {
            return qn;
        }
    }
}
