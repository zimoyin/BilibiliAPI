import github.zimoyin.core.exception.URLFormatException;
import github.zimoyin.core.utils.IDConvert;
import github.zimoyin.core.utils.net.httpclient.HttpClientResult;
import github.zimoyin.core.utils.net.httpclient.HttpClientUtils;
import github.zimoyin.core.utils.net.httpclient.NetFileUtil;
import github.zimoyin.core.utils.reflex.FindClass;
import github.zimoyin.core.video.download.*;
import github.zimoyin.core.video.info.VideoHomePageRecommendation;
import github.zimoyin.core.video.info.pojo.recommendation.VideoHomeRecommendationRootBean;
import github.zimoyin.core.video.url.VideoURLFormat;
import github.zimoyin.core.video.url.VideoURLPreviewFormatP1080;
import github.zimoyin.core.video.url.data.Fnval;
import github.zimoyin.core.video.url.data.QN;
import github.zimoyin.core.video.url.pojo.Dash;
import github.zimoyin.core.video.url.pojo.Media;
import github.zimoyin.core.video.url.pojo.VideoURLJsonRoot;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.function.Consumer;

public class Main {
    public static void main(String[] args) throws ExecutionException, InterruptedException, URLFormatException, IOException, NoSuchAlgorithmException, KeyStoreException, URISyntaxException, KeyManagementException {
        VideoDownloadSetting setting = new VideoDownloadSetting();
//        setting.setID("ep469140");
        setting.setID("BV1ra411X7RX");
        setting.setFnval(Fnval.VideoFormat_mp4);
        setting.setQn(QN.P306);
        setting.build();

        VideoURLFormat urlFormat = new VideoURLFormat();
        System.out.println(urlFormat.getJsonPOJO(setting).getURLs());
    }
}