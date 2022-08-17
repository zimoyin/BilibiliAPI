package github.zimoyin.core.video.url;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import github.zimoyin.core.exception.CodeException;
import github.zimoyin.core.utils.IDConvert;
import github.zimoyin.core.utils.JsonSerializeUtil;
import github.zimoyin.core.utils.net.httpclient.HttpClientResult;
import github.zimoyin.core.utils.net.httpclient.HttpClientUtils;
import github.zimoyin.core.video.url.pojo.VideoURLJsonRoot;
import org.apache.http.HttpException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

/**
 * 固定1080p视频 mp4格式，没有防盗链
 */
public class VideoURLPreviewFormatP1080 {
//    private final String URL="https://api.bilibili.com/x/player/playurl?avid=932160271&cid=384624341&qn=16&type=mp4&platform=html5&high_quality=1";

    private final String URL="https://api.bilibili.com/x/player/playurl?bvid=%s&cid=%s&qn=80&type=mp4&platform=html5&high_quality=1";

    public VideoURLPreviewFormatP1080() {
    }

    /**
     * 获取下载链接
     * @param bv
     * @return
     */
    public ArrayList<java.net.URL> getURLs(String bv){
        try {
            VideoURLJsonRoot jsonRoot = getJsonPojo(bv);
            ArrayList<java.net.URL> urLs = jsonRoot.getURLs();
            return urLs;
        } catch (Exception e) {
            throw new JSONException("无法解析出URL",e);
        }
    }


    /**
     * 获取下载链接
     * @param bv
     * @return
     */
    public ArrayList<java.net.URL> getURLs(String bv,String cid){
        try {
            VideoURLJsonRoot jsonRoot = getJsonPojo(bv,cid);
            ArrayList<java.net.URL> urLs = jsonRoot.getURLs();
            return urLs;
        } catch (Exception e) {
            throw new JSONException("无法解析出URL",e);
        }
    }


    /**
     * 获取JSON实体类
     * @param bv
     * @return
     * @throws CodeException
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws KeyStoreException
     * @throws URISyntaxException
     * @throws KeyManagementException
     */
    public VideoURLJsonRoot getJsonPojo(String bv) throws CodeException, IOException, NoSuchAlgorithmException, KeyStoreException, URISyntaxException, KeyManagementException {
        String page = getPage(bv);
        VideoURLJsonRoot jsonRoot = JSONObject.parseObject(page, VideoURLJsonRoot.class);
        return jsonRoot;
    }


    /**
     * 获取JSON实体类
     * @param bv
     * @return
     * @throws CodeException
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws KeyStoreException
     * @throws URISyntaxException
     * @throws KeyManagementException
     */
    public VideoURLJsonRoot getJsonPojo(String bv,String cid) throws CodeException, IOException, NoSuchAlgorithmException, KeyStoreException, URISyntaxException, KeyManagementException {
        String page = getPage(bv,cid);
        VideoURLJsonRoot jsonRoot = JSONObject.parseObject(page, VideoURLJsonRoot.class);
        return jsonRoot;
    }


    /**
     * 获取原始信息
     * @param bv
     * @return
     * @throws CodeException
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws KeyStoreException
     * @throws URISyntaxException
     * @throws KeyManagementException
     */
    public String getPage(String bv) throws CodeException, IOException, NoSuchAlgorithmException, KeyStoreException, URISyntaxException, KeyManagementException {
//        String url = String.format(URL, bv, IDConvert.BvToCID(bv));
//        HttpClientResult httpClientResult = HttpClientUtils.doGet(url);
//        return httpClientResult.getContent();
        return getPage(bv,IDConvert.BvToCID(bv));
    }


    /**
     * 获取原始信息
     * @param bv
     * @return
     * @throws CodeException
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws KeyStoreException
     * @throws URISyntaxException
     * @throws KeyManagementException
     */
    public String getPage(String bv,String cid) throws CodeException, IOException, NoSuchAlgorithmException, KeyStoreException, URISyntaxException, KeyManagementException {
        String url = String.format(URL, bv, cid);
        HttpClientResult httpClientResult = HttpClientUtils.doGet(url);
        return httpClientResult.getContent();
    }
}
