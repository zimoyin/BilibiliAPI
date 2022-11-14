package github.zimoyin.bili.user.userinfo;


import com.alibaba.fastjson.JSONObject;
import github.zimoyin.bili.cookie.Cookie;
import github.zimoyin.bili.user.pojo.fans.Item;
import github.zimoyin.bili.user.pojo.fans.UserFansJsonRootBean;
import github.zimoyin.bili.utils.net.httpclient.HttpClientResult;
import github.zimoyin.bili.utils.net.httpclient.HttpClientUtils;
import org.apache.http.HttpException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 用户的粉丝详细情况，程序实现最多获取250名
 * 登录可看自己前1000名，其他用户可查看前250名（网页端请求时ps为20，所以直接查看只能看到前100名）
 */
public class UserFans {

    /**
     * 用户的mid
     * 每页项数（最大50）
     * 页码（其他用户仅可查看前5页）
     */
    private final String URL ="https://api.bilibili.com/x/relation/followers?vmid=%s&ps=50&pn=%s";


    private Cookie cookie;
    private HashMap<String, String> headers = new HashMap<String, String>();

    public UserFans() {
    }

    public void setCookie(Cookie cookie) {
        this.cookie = cookie;
        headers.clear();
        cookie.toHeaderCookie(headers);
    }

    /**
     * 获取能最查到的所有的粉丝
     * @return
     */
    public ArrayList<Item> getFollowers(long mid) throws HttpException {
        ArrayList<Item> arrayList = new ArrayList<>();
        ArrayList<UserFansJsonRootBean> pojo = getPojo(mid);
        for (UserFansJsonRootBean user : pojo) {
            List<Item> list = user.getData().getList();
            arrayList.addAll(list);
        }
        return arrayList;
    }


    /**
     * 获取所有的用户粉丝数
     * @param mid
     * @return
     * @throws HttpException
     */
    public ArrayList<UserFansJsonRootBean> getPojo(long mid) throws HttpException {
        ArrayList<UserFansJsonRootBean> list = new ArrayList<>();
        for (int i = 1; i <= 20; i++) {
            UserFansJsonRootBean pojo = getPojo(mid, i);
            if (pojo.getCode() != 0) {
                break;
            }
            list.add(pojo);
        }
        return list;
    }

    /**
     * 获取粉丝详细，作为一个josn的pojo返回
     * @param mid 用户的mid
     * @param pn 页数
     * @return
     * @throws HttpException
     */
    public UserFansJsonRootBean getPojo(long mid,int pn) throws HttpException {
        String page = null;
        try {
            page = getPage(mid,pn);
        } catch (Exception e) {
            throw new HttpException("访问URL失败",e);
        }
        if (page == null) return null;
        UserFansJsonRootBean bean = JSONObject.parseObject(page, UserFansJsonRootBean.class);
        return bean;
    }

    /**
     * 获取粉丝详细
     * @param mid 用户的mid
     * @param pn 页数
     * @return
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws KeyStoreException
     * @throws URISyntaxException
     * @throws KeyManagementException
     */
    public String getPage(long mid,int pn) throws IOException, NoSuchAlgorithmException, KeyStoreException, URISyntaxException, KeyManagementException {
        String url = String.format(URL, mid,pn);
        HttpClientResult result = HttpClientUtils.doGet(url, headers, null);
        return result.getContent();
    }

    public Cookie getCookie() {
        return cookie;
    }
}
