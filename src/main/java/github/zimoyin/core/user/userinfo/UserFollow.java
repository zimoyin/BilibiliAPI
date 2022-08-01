package github.zimoyin.core.user.userinfo;

import com.alibaba.fastjson.JSONObject;
import github.zimoyin.core.cookie.Cookie;
import github.zimoyin.core.user.pojo.fans.Item;
import github.zimoyin.core.user.pojo.fans.UserFansJsonRootBean;
import github.zimoyin.core.user.pojo.follow.UserFollowJsonRootBean;
import github.zimoyin.core.utils.net.httpclient.HttpClientResult;
import github.zimoyin.core.utils.net.httpclient.HttpClientUtils;
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
 * 获取用户的关注
 * 登录可看自己全部，其他用户仅可查看前5页
 */
public class UserFollow {
    /**
     * 用户的mid
     * 每页项数（最大50）
     * 页码（其他用户仅可查看前5页）
     */
    private final String URL ="https://api.bilibili.com/x/relation/followings?vmid=%s&ps=50&pn=%s";


    private Cookie cookie;
    private HashMap<String, String> headers = new HashMap<String, String>();

    public UserFollow() {
    }

    public void setCookie(Cookie cookie) {
        this.cookie = cookie;
        headers.clear();
        cookie.toHeaderCookie(headers);
    }

    /**
     * 获取能最查到的所有的关注
     * @return
     */
    public ArrayList<Item> getFollows(long mid) throws HttpException {
        ArrayList<Item> arrayList = new ArrayList<>();
        ArrayList<UserFollowJsonRootBean> pojo = getPojo(mid);
        for (UserFollowJsonRootBean user : pojo) {
            List<Item> list = user.getData().getList();
            arrayList.addAll(list);
        }
        return arrayList;
    }


    /**
     * 获取关注
     * @param mid
     * @return
     * @throws HttpException
     */
    public ArrayList<UserFollowJsonRootBean> getPojo(long mid) throws HttpException {
        ArrayList<UserFollowJsonRootBean> list = new ArrayList<>();
        for (int i = 1; i <= 100; i++) {
            UserFollowJsonRootBean pojo = getPojo(mid, i);
            if (pojo.getCode() != 0) {
                break;
            }
            list.add(pojo);
        }
        return list;
    }

    /**
     *获取关注，作为一个josn的pojo返回
     * @param mid 用户的mid
     * @param pn 页数
     * @return
     * @throws HttpException
     */
    public UserFollowJsonRootBean getPojo(long mid,int pn) throws HttpException {
        String page = null;
        try {
            page = getPage(mid,pn);
        } catch (Exception e) {
            throw new HttpException("访问URL失败",e);
        }
        if (page == null) return null;
        UserFollowJsonRootBean bean = JSONObject.parseObject(page, UserFollowJsonRootBean.class);
        return bean;
    }

    /**
     * 获取关注
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
