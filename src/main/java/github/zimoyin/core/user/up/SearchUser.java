package github.zimoyin.core.user.up;

import com.alibaba.fastjson.JSONObject;
import github.zimoyin.core.cookie.Cookie;
import github.zimoyin.core.cookie.GlobalCookie;
import github.zimoyin.core.cookie.WebCookie;
import github.zimoyin.core.exception.CodeException;
import github.zimoyin.core.search.SearchCategories;
import github.zimoyin.core.search.enums.SearchType;
import github.zimoyin.core.search.pojo.search.SearchCategoriesJsonRoot;
import github.zimoyin.core.search.pojo.search.result.user.ResultUser;
import github.zimoyin.core.user.pojo.search.Result;
import github.zimoyin.core.user.pojo.search.SearchUserTableRootBean;
import github.zimoyin.core.utils.net.httpclient.HttpClientResult;
import github.zimoyin.core.utils.net.httpclient.HttpClientUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

/**
 * 查找用户
 *
 * @API： zimo
 */
public class SearchUser {
    //注意汉字要编码
    //https://api.bilibili.com/x/web-interface/search/type?__refresh__=true&_extra=&context=&page=1&page_size=36&order=&duration=&from_source=&from_spmid=333.337&platform=pc&highlight=1&single_column=0&keyword=%E5%87%A4%E7%A5%9E%E9%94%85%E9%94%85&category_id=&search_type=bili_user&order_sort=0&user_type=0&dynamic_offset=0&preload=true&com2co=true
    private final String URL_2 = "https://api.bilibili.com/x/web-interface/search/type?page=%s&page_size=36&platform=pc&highlight=1&keyword=%s&search_type=bili_user&order_sort=0&user_type=0";
//    private final String URL_2 = "https://api.bilibili.com/x/web-interface/search/type?keyword=%s&search_type=bili_user";

    private Cookie cookie;

    public SearchUser() {
        this.cookie = new WebCookie();
        try {
            cookie.updateCookie();
        } catch (IOException e) {
            throw new RuntimeException("Cookie更新失败",e);
        }
    }

    public SearchUser(Cookie cookie) {
        this.cookie = cookie;
    }

    /**
     * 返回用户的mid
     *
     * @param userName
     * @return
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws KeyStoreException
     * @throws URISyntaxException
     * @throws KeyManagementException
     */
    public long getMid(String userName) throws IOException, NoSuchAlgorithmException, KeyStoreException, URISyntaxException, KeyManagementException {
        return search(userName).getMid();
    }

    /**
     * 返回用户的mid
     *
     * @param userName
     * @return
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws KeyStoreException
     * @throws URISyntaxException
     * @throws KeyManagementException
     */
    public long getMid2(String userName) throws IOException {
        SearchCategories searchCategories = new SearchCategories(cookie);
        SearchCategoriesJsonRoot pojo = searchCategories.getJsonPojo(new SearchCategories.SearchCategoriesParams(userName, SearchType.User));
        if (pojo.getCode() != 0)
            throw new CodeException("code = " + pojo.getCode() + "：" + pojo.getMessage());
        for (ResultUser user : pojo.getData().getResult().getUserType()) {
            if (user.getUname().equals(userName)) {
                return user.getMid();
            }
        }
        return -1;
    }

    /**
     * 精确查找某人
     *
     * @param userName
     * @return
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws KeyStoreException
     * @throws URISyntaxException
     * @throws KeyManagementException
     */
    public Result search(String userName) throws IOException, NoSuchAlgorithmException, KeyStoreException, URISyntaxException, KeyManagementException, CodeException {
        SearchUserTableRootBean pojo = searchPojo(userName, 1);
        if (pojo.getCode() != 0) {
            throw new CodeException("code = " + pojo.getCode() + "：" + pojo.getMessage());
        }
        Result result = pojo.getData().getResult().get(0);
        if (result.getUname().equals(userName)) {
            return result;
        }

        return null;
    }

    /**
     * 查找用户（pojo）
     *
     * @param userName 用户名
     * @return 一个查找用户的表，里面有近似用户名的项
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws KeyStoreException
     * @throws URISyntaxException
     * @throws KeyManagementException
     */
    public SearchUserTableRootBean searchPojo(String userName) throws IOException, NoSuchAlgorithmException, KeyStoreException, URISyntaxException, KeyManagementException {
        return searchPojo(userName, 1);
    }

    /**
     * 查找用户（原始数据）
     *
     * @param userName 用户名
     * @return 一个查找用户的表，里面有近似用户名的项
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws KeyStoreException
     * @throws URISyntaxException
     * @throws KeyManagementException
     */
    public String searchPage(String userName) throws IOException, NoSuchAlgorithmException, KeyStoreException, URISyntaxException, KeyManagementException {
        return searchPage(userName, 1);
    }


    /**
     * 查找用户（pojo）
     *
     * @param userName 用户名
     * @param page     页数 （总页数默认36，URL参数可改）
     * @return 一个查找用户的表，里面有近似用户名的项
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws KeyStoreException
     * @throws URISyntaxException
     * @throws KeyManagementException
     */
    public SearchUserTableRootBean searchPojo(String userName, int page) throws IOException, NoSuchAlgorithmException, KeyStoreException, URISyntaxException, KeyManagementException {
        String result = searchPage(userName, page);
        SearchUserTableRootBean searchUserTableRootBean = JSONObject.parseObject(result, SearchUserTableRootBean.class);
        return searchUserTableRootBean;
    }


    /**
     * 查找用户（原始数据）
     *
     * @param userName 用户名
     * @param page     页数 （总页数默认36，URL参数可改）
     * @return 一个查找用户的表，里面有近似用户名的项
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws KeyStoreException
     * @throws URISyntaxException
     * @throws KeyManagementException
     */
    public String searchPage(String userName, int page) throws IOException, NoSuchAlgorithmException, KeyStoreException, URISyntaxException, KeyManagementException {
        String url = String.format(URL_2, page, userName);

        HttpClientResult result = HttpClientUtils.doGet(url, cookie != null ? cookie.toHeaderCookie() : null,null);
        return result.getContent();
    }

}
