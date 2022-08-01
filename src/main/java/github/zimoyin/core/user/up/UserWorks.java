package github.zimoyin.core.user.up;


import com.alibaba.fastjson.JSONObject;
import github.zimoyin.core.user.pojo.works.UserWorksRootBean;
import github.zimoyin.core.utils.net.httpclient.HttpClientResult;
import github.zimoyin.core.utils.net.httpclient.HttpClientUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

/**
 * 用户的作品
 *
 * @API： zimo
 */
public class UserWorks {
    /**
     * mid
     * ps 返回作品数量
     * pn 要看的页数
     * order  排序方式
     * >>>>>>>>> pubdate 发布日期
     * >>>>>>>>> click 点击数
     * >>>>>>>>> stow 收藏
     * tid  分区（默认0）
     */
    //https://api.bilibili.com/x/space/arc/search?mid=1614167459&ps=30&tid=0&pn=1&keyword=&order=pubdate&jsonp=jsonp
    private final String URL = "https://api.bilibili.com/x/space/arc/search?mid=%s&ps=%s&pn=%s&order=%s&jsonp=jsonp";

    /**
     * 返回用户作品
     *
     * @param mid           用户的mid
     * @param publishNumber 显示最多用户投稿数量
     * @param page          页数
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws KeyStoreException
     * @throws URISyntaxException
     * @throws KeyManagementException
     */
    public UserWorksRootBean userWorksPojo(long mid, int publishNumber, int page) throws IOException, NoSuchAlgorithmException, KeyStoreException, URISyntaxException, KeyManagementException {
        String result = userWorksPage(mid, publishNumber, page);
        UserWorksRootBean pojo = JSONObject.parseObject(result, UserWorksRootBean.class);
        return pojo;
    }


    /**
     * 返回用户作品
     *
     * @param mid           用户的mid
     * @param publishNumber 显示最多用户投稿数量
     * @param page          页数
     * @param order         排序方式
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws KeyStoreException
     * @throws URISyntaxException
     * @throws KeyManagementException
     */
    public UserWorksRootBean userWorksPojo(long mid, int publishNumber, int page, String order) throws IOException, NoSuchAlgorithmException, KeyStoreException, URISyntaxException, KeyManagementException {
        String result = userWorksPage(mid, publishNumber, page, order);
        UserWorksRootBean pojo = JSONObject.parseObject(result, UserWorksRootBean.class);
        return pojo;
    }

    /**
     * 返回用户作品 按照发布时间排序
     *
     * @param mid           用户的mid
     * @param publishNumber 显示最多用户投稿数量
     * @param page          页数
     * @return
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws KeyStoreException
     * @throws URISyntaxException
     * @throws KeyManagementException
     */
    public String userWorksPage(long mid, int publishNumber, int page) throws IOException, NoSuchAlgorithmException, KeyStoreException, URISyntaxException, KeyManagementException {
        return userWorksPage(mid, publishNumber, page, "pubdate");
    }

    /**
     * 返回用户作品
     *
     * @param mid           用户的mid
     * @param publishNumber 显示最多用户投稿数量
     * @param page          页数
     * @param order         排序方式
     * @return
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws KeyStoreException
     * @throws URISyntaxException
     * @throws KeyManagementException
     */
    public String userWorksPage(long mid, int publishNumber, int page, String order) throws IOException, NoSuchAlgorithmException, KeyStoreException, URISyntaxException, KeyManagementException {
        String url = String.format(URL, mid, publishNumber, page, order);
        HttpClientResult result = HttpClientUtils.doGet(url);
        return result.getContent();
    }
}
