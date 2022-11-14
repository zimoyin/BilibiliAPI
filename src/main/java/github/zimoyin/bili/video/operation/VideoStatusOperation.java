package github.zimoyin.bili.video.operation;

import com.alibaba.fastjson.JSONObject;
import github.zimoyin.bili.cookie.Cookie;
import github.zimoyin.bili.utils.IDConvert;
import github.zimoyin.bili.utils.net.httpclient.HttpClientResult;
import github.zimoyin.bili.utils.net.httpclient.HttpClientUtils;

import java.io.IOException;
import java.util.HashMap;

/**
 * 点赞、转发、投币、分享
 * @API: https://github.com/SocialSisterYi/bilibili-API-collect
 */
public class VideoStatusOperation {

    private final String URL_like = "http://api.bilibili.com/x/web-interface/archive/like";
    private final String URL_coin = "http://api.bilibili.com/x/web-interface/coin/add";
    private final String URL_star = "http://api.bilibili.com/medialist/gateway/coll/resource/deal";
    private final String URL_star_2 = "http://api.bilibili.com/x/v3/fav/resource/deal";
    private final String URL_share = "http://api.bilibili.com/x/web-interface/share/add";
    private final String URL_SanLian = "http://api.bilibili.com/x/web-interface/archive/like/triple";
    private Cookie cookie;

    private HashMap<String, String> header = new HashMap<>();

    public VideoStatusOperation(Cookie cookie) {
        this.cookie = cookie;
        cookie.setCookieToHeader(header);
        header.put("referer", "https://www.bilibili.com");
    }

    /**
     * 点赞
     *
     * @param bv
     * @return
     * @throws IOException
     */
    public boolean like(String bv) throws IOException {
        String result = likePage(bv);
        int code = (int) JSONObject.parseObject(result).get("code");
        return code == 0;
    }

    /**
     * 点赞
     *
     * @param bv
     * @return
     * @throws IOException
     */
    public String likePage(String bv) throws IOException {
        HashMap<String, String> param = new HashMap<>();
        param.put("bvid", bv);
        param.put("like", "1");
        param.put("csrf", cookie.get("bili_jct"));

        //点赞
        HttpClientResult result = HttpClientUtils.doPost(URL_like, header, param, null);
        return result.getContent();
    }


    /**
     * 取消点赞
     *
     * @param bv
     * @return
     * @throws IOException
     */
    public boolean notLike(String bv) throws IOException {
        String result = notLikePage(bv);
        int code = (int) JSONObject.parseObject(result).get("code");
        return code == 0;
    }


    /**
     * 取消点赞
     *
     * @param bv
     * @return
     * @throws IOException
     */
    public String notLikePage(String bv) throws IOException {
        HashMap<String, String> param = new HashMap<>();
        param.put("bvid", bv);
        param.put("like", "2");
        param.put("csrf", cookie.get("bili_jct"));


        HttpClientResult result = HttpClientUtils.doPost(URL_like, header, param, null);
        return result.getContent();
    }


    /**
     * 投币（一次投一个）
     *
     * @param bv
     * @return
     * @throws IOException
     */
    public boolean coin(String bv) throws IOException {
        String result = coinPage(bv);
        int code = (int) JSONObject.parseObject(result).get("code");
        return code == 0;
    }


    /**
     * 投币（一次投一个）
     *
     * @param bv
     * @return
     * @throws IOException
     */
    public String coinPage(String bv) throws IOException {
        HashMap<String, String> param = new HashMap<>();
        param.put("bvid", bv);
        param.put("multiply", "1");
        param.put("csrf", cookie.get("bili_jct"));


        HttpClientResult result = HttpClientUtils.doPost(URL_coin, header, param, null);
        return result.getContent();
    }


    /**
     * 收藏
     * @param bv
     * @param starID  收藏夹ID
     * @return
     * @throws IOException
     */
    public boolean stat(String bv, String... starID) throws IOException {
        String result = statPage(bv, starID);
        int code = (int) JSONObject.parseObject(result).get("code");
        return code == 0;
    }

    /**
     * 收藏
     *
     * @param bv
     * @param starID 收藏夹ID
     * @return
     * @throws IOException
     */
    public String statPage(String bv, String... starID) throws IOException {
        HashMap<String, String> param = new HashMap<>();
        param.put("rid", String.valueOf(IDConvert.BvToAvNumber(bv)));
        param.put("type", "2");
        param.put("csrf", cookie.get("bili_jct"));
        if (starID != null) {
            String ids = "";
            for (String id : starID) {
                ids = ids + "," + id;
            }
            param.put("add_media_ids", ids);
        }
        param.put("add_media_ids", "139168019");
        HttpClientResult result = HttpClientUtils.doPost(URL_star, header, param, null);
        return result.getContent();
    }


    /**
     * 取消收藏
     * @param bv
     * @param starID  收藏夹ID
     * @return
     * @throws IOException
     */
    public boolean notStat(String bv, String... starID) throws IOException {
        String result = notStatPage(bv, starID);
        int code = (int) JSONObject.parseObject(result).get("code");
        return code == 0;
    }


    /**
     * 取消收藏
     *
     * @param bv
     * @param starID 收藏夹ID
     * @return
     * @throws IOException
     */
    public String notStatPage(String bv, String... starID) throws IOException {
        HashMap<String, String> param = new HashMap<>();
        param.put("rid", String.valueOf(IDConvert.BvToAvNumber(bv)));
        param.put("type", "2");
        param.put("csrf", cookie.get("bili_jct"));
        if (starID != null) {
            String ids = "";
            for (String id : starID) {
                ids = ids + "," + id;
            }
            param.put("del_media_ids", ids);
        }
//        param.put("del_media_ids", "139168019");
        HttpClientResult result = HttpClientUtils.doPost(URL_star, header, param, null);
        return result.getContent();
    }


    /**
     * 取消收藏
     * @param bv
     * @return
     * @throws IOException
     */
    public boolean share(String bv) throws IOException {
        String result = sharePage(bv);
        int code = (int) JSONObject.parseObject(result).get("code");
        return code == 0;
    }


    /**
     * 取消收藏
     *
     * @param bv
     * @return
     * @throws IOException
     */
    public String sharePage(String bv) throws IOException {
        HashMap<String, String> param = new HashMap<>();
//        param.put("aid",  String.valueOf(IDConvert.BvToAvNumber(bv)));
        param.put("bvid",  bv);
        param.put("csrf", cookie.get("bili_jct"));

        HttpClientResult result = HttpClientUtils.doPost(URL_share, header, param, null);
        return result.getContent();
    }
    /**
     * 取消收藏
     * @param bv
     * @return
     * @throws IOException
     */
    public boolean sanLian(String bv) throws IOException {
        String result = sanLianPage(bv);
        System.out.println(result);
        int code = (int) JSONObject.parseObject(result).get("code");
        return code == 0;
    }


    /**
     * 取消收藏
     *
     * @param bv
     * @return
     * @throws IOException
     */
    public String sanLianPage(String bv) throws IOException {
        HashMap<String, String> param = new HashMap<>();
//        param.put("aid",  String.valueOf(IDConvert.BvToAvNumber(bv)));
        param.put("bvid",  bv);
        param.put("csrf", cookie.get("bili_jct"));

        HttpClientResult result = HttpClientUtils.doPost(URL_SanLian, header, param, null);
        return result.getContent();
    }
}
