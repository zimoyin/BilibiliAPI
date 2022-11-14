package github.zimoyin.bili.collection;

import com.alibaba.fastjson.JSONObject;
import github.zimoyin.bili.collection.pojo.userlist.UserCollectionListJsonRoot;
import github.zimoyin.bili.utils.net.httpclient.HttpClientResult;
import github.zimoyin.bili.utils.net.httpclient.HttpClientUtils;

import java.io.IOException;
import java.util.HashMap;

public class UserCollection {
    private static final String URL = "https://api.bilibili.com/x/polymer/space/seasons_series_list?mid=8047632&page_num=1&page_size=20";

    /**
     * 返回用户创建的合集列表
     * @param mid 用户的mid
     * @return
     */
    public UserCollectionListJsonRoot getJsonPojo(long mid) throws IOException {
        String page = getPage(mid);
        UserCollectionListJsonRoot userCollectionListJsonRoot = JSONObject.parseObject(page, UserCollectionListJsonRoot.class);
        return userCollectionListJsonRoot;
    }
    /**
     * 返回用户创建的合集列表
     * @param mid 用户的mid
     * @param pn 页数
     * @return
     */
    public UserCollectionListJsonRoot getJsonPojo(long mid, int pn) throws IOException {
        String page = getPage(mid, pn);
        UserCollectionListJsonRoot userCollectionListJsonRoot = JSONObject.parseObject(page, UserCollectionListJsonRoot.class);
        return userCollectionListJsonRoot;
    }
    /**
     * 返回用户创建的合集列表
     * @param mid 用户的mid
     * @param pn 页数
     * @param ps 返回条数 1-20
     * @return
     */
    public UserCollectionListJsonRoot getJsonPojo(long mid, int pn, int ps) throws IOException {
        String page = getPage(mid, pn, ps);
        UserCollectionListJsonRoot userCollectionListJsonRoot = JSONObject.parseObject(page, UserCollectionListJsonRoot.class);
        return userCollectionListJsonRoot;
    }
    /**
     * 返回用户创建的合集列表
     * @param mid 用户的mid
     * @return
     */
    public String getPage(long mid) throws IOException {
        return getPage(mid,1,20);
    }
    /**
     * 返回用户创建的合集列表
     * @param mid 用户的mid
     * @param pn 页数
     * @return
     */
    public String getPage(long mid,int pn) throws IOException {
        return getPage(mid,pn,20);
    }
    /**
     * 返回用户创建的合集列表
     * @param mid 用户的mid
     * @param pn 页数
     * @param ps 返回条数 1-20
     * @return
     */
    public String getPage(long mid,int pn,int ps) throws IOException {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("mid", String.valueOf(mid));
        if (pn > -1) params.put("page_num", String.valueOf(pn));//1
        else params.put("page_num", String.valueOf(1));
        if (ps > -1 && ps <=20) params.put("page_size", String.valueOf(ps));//30
        else params.put("page_size", String.valueOf(30));


        HttpClientResult httpClientResult = HttpClientUtils.doGet(URL, params);
        return httpClientResult.getContent();
    }
}
