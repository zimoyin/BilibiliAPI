package github.zimoyin.bili.user;

import com.alibaba.fastjson.JSONObject;
import github.zimoyin.bili.utils.net.httpclient.HttpClientResult;
import github.zimoyin.bili.utils.net.httpclient.HttpClientUtils;
import org.apache.http.HttpException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

/**
 * 昵称检查
 */
public class DetectNicknames {
    private static final String URL = "http://passport.bilibili.com/web/generic/check/nickname?nickName=%s";


    /**
     * 用户的唯一标识 允许通过SearchUser 来获取
     */
    private String name;

    public DetectNicknames() {
    }

    public DetectNicknames(String name) {
        this.name = name;
    }

    /**
     * 判断名称是否合法
     * @param name
     * @return
     * @throws HttpException
     */
    public boolean isLegal (String name) throws HttpException, IOException, NoSuchAlgorithmException, KeyStoreException, URISyntaxException, KeyManagementException {
        String page = getPage(name);
        JSONObject jsonObject = JSONObject.parseObject(page);
        if (jsonObject.getInteger("code"    ) == 0) {
            return true;
        }
        return false;
    }

    /**
     *
     * @return
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws KeyStoreException
     * @throws URISyntaxException
     * @throws KeyManagementException
     */
    public String getPage() throws IOException, NoSuchAlgorithmException, KeyStoreException, URISyntaxException, KeyManagementException {
        return getPage(name);
    }

    /**
     * 0	昵称未被注册
     * 2001	该昵称已被他人使用
     * 40002	昵称包含敏感信息
     * 40004	昵称不可包含除-和_以外的特殊字符
     * 40005	昵称过长（超过16字符）
     * 40006	昵称过短（少于2字符）
     * 40014	昵称已存在
     *
     * @param name 待检测的名称
     * @return
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws KeyStoreException
     * @throws URISyntaxException
     * @throws KeyManagementException
     */
    public String getPage(String name) throws IOException, NoSuchAlgorithmException, KeyStoreException, URISyntaxException, KeyManagementException {
        String url = String.format(URL, name);
        HttpClientResult result = HttpClientUtils.doGet(url);
        return result.getContent();
    }
}
