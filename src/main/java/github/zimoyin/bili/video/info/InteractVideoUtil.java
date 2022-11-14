package github.zimoyin.bili.video.info;

import com.alibaba.fastjson.JSONObject;
import github.zimoyin.bili.exception.CodeException;
import github.zimoyin.bili.utils.IDConvert;
import github.zimoyin.bili.utils.JsonSerializeUtil;
import github.zimoyin.bili.utils.net.httpclient.HttpClientResult;
import github.zimoyin.bili.utils.net.httpclient.HttpClientUtils;


import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

public class InteractVideoUtil {
//    剧情图id
//    https://api.bilibili.com/x/player/v2?cid=381841269&aid=632023735&bvid=BV1vb4y1r7cg
//    https://api.bilibili.com/x/player.so?id=cid:381841253&bvid=BV1vb4y1r7cg
    private static final String URL = "https://api.bilibili.com/x/player/v2?bvid=%s&cid=%s";



    public static String getGraphVersion(String bv) throws CodeException, IOException, NoSuchAlgorithmException, KeyStoreException, URISyntaxException, KeyManagementException {
        String cid = IDConvert.BvToCID(bv);
        String url = String.format(URL, bv, cid);
        HttpClientResult result = HttpClientUtils.doGet(url);

        JSONObject jsonObject = JSONObject.parseObject(result.getContent());
        if (jsonObject.getInteger("code") != 0) return null;
        String graph_version = JsonSerializeUtil.getJsonPath().read(jsonObject, "/data/interaction/graph_version");
        return graph_version;
    }
}
