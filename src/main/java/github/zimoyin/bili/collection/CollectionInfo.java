package github.zimoyin.bili.collection;

import com.alibaba.fastjson.JSONObject;
import github.zimoyin.bili.collection.pojo.collection.CollectionJsonRoot;
import github.zimoyin.bili.utils.net.httpclient.HttpClientResult;
import github.zimoyin.bili.utils.net.httpclient.HttpClientUtils;

import java.io.IOException;
import java.util.HashMap;

public class CollectionInfo {
    private static final String URL = "https://api.bilibili.com/x/polymer/space/seasons_archives_list?page_num=1&page_size=";

    /**
     * 获取这个合集的信息
     * @param mid 用户的mid
     * @param sid 合集的sid
     * @return
     */
    public CollectionJsonRoot getJsonPojo(long mid, long sid) throws IOException {
        String page = getPage(mid, sid);
        return JSONObject.parseObject(page, CollectionJsonRoot.class);
    }
    /**
     * 获取这个合集的信息
     * @param mid 用户的mid
     * @param sid 合集的sid
     * @param pn 当前页数
     * @return
     */
    public CollectionJsonRoot getJsonPojo(long mid, long sid, int pn) throws IOException {
        String page = getPage(mid, sid, pn);
        return JSONObject.parseObject(page, CollectionJsonRoot.class);
    }
    /**
     * 获取这个合集的信息
     * @param mid 用户的mid
     * @param sid 合集的sid
     * @param pn 当前页数
     * @param ps 这一页的容量 1-100
     * @return
     */
    public CollectionJsonRoot getJsonPojo(long mid, long sid, int pn, int ps) throws IOException {
        String page = getPage(mid, sid, pn, ps);
        return JSONObject.parseObject(page, CollectionJsonRoot.class);
    }
    /**
     * 获取这个合集的信息
     * @param mid 用户的mid
     * @param sid 合集的sid
     * @param pn 当前页数
     * @param ps 这一页的容量 1-100
     * @param sort_reverse 排序反转
     * @return
     */
    public CollectionJsonRoot getJsonPojo(long mid, long sid, int pn, int ps, boolean sort_reverse) throws IOException {
        String page = getPage(mid, sid, pn, ps, sort_reverse);
        return JSONObject.parseObject(page, CollectionJsonRoot.class);
    }
    /**
     * 获取这个合集的信息
     * @param mid 用户的mid
     * @param sid 合集的sid
     * @return
     */
    public String getPage(long mid, long sid) throws IOException {
        return getPage(mid, sid, 1, 100, false);
    }
    /**
     * 获取这个合集的信息
     * @param mid 用户的mid
     * @param sid 合集的sid
     * @param pn 当前页数
     * @return
     */
    public String getPage(long mid, long sid, int pn) throws IOException {
        return getPage(mid, sid, pn, 100, false);
    }

    /**
     * 获取这个合集的信息
     * @param mid 用户的mid
     * @param sid 合集的sid
     * @param pn 当前页数
     * @param ps 这一页的容量 1-100
     * @return
     */
    public String getPage(long mid, long sid, int pn, int ps) throws IOException {
        return getPage(mid, sid, pn, ps, false);
    }
    /**
     * 获取这个合集的信息
     * @param mid 用户的mid
     * @param sid 合集的sid
     * @param pn 当前页数
     * @param ps 这一页的容量 1-100
     * @param sort_reverse 排序反转
     * @return
     */
    public String getPage(long mid, long sid, int pn, int ps, boolean sort_reverse) throws IOException {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("mid", String.valueOf(mid));
        params.put("season_id", String.valueOf(sid));
        if (pn > -1) params.put("page_num", String.valueOf(pn));//1
        else params.put("page_num", String.valueOf(1));
        if (ps > -1 && ps <=100) params.put("page_size", String.valueOf(ps));//30
        else params.put("page_size", String.valueOf(30));
        params.put("sort_reverse", String.valueOf(sort_reverse));//false

        HttpClientResult httpClientResult = HttpClientUtils.doGet(URL, params);
        return httpClientResult.getContent();
    }
}
