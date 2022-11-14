package github.zimoyin.bili.video.info;


import github.zimoyin.bili.exception.CodeException;
import github.zimoyin.bili.utils.IDConvert;
import github.zimoyin.bili.utils.net.httpclient.HttpClientUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

/**
 * 高能进度条
 * 高能进度条反应了在时域上，单位时间内弹幕发送量的变化趋势
 *
 * 并用曲线顶点表示在进度条上，实现可视化
 * 服务器返回的json中出现java关键字，无法按照约定制作POJO
 * 注：events对象中的数组：包含弹幕顶点（顶点个数由视频时长和采样时间决定）
 * @API: https://github.com/SocialSisterYi/bilibili-API-collect
 */
public class VideoBarrageCurve {
    private final String URL="http://bvc.bilivideo.com/pbp/data?cid=%s";

    public String getPage(String bv) throws CodeException, IOException, NoSuchAlgorithmException, KeyStoreException, URISyntaxException, KeyManagementException {
        return HttpClientUtils.doGet(String.format(URL, IDConvert.BvToCID(bv))).getContent();
    }
}
