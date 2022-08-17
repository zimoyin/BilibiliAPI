import com.alibaba.fastjson.JSONObject;
import github.zimoyin.core.comment.pojo.table.Emote;
import github.zimoyin.core.exception.CodeException;
import github.zimoyin.core.fanju.info.SeriesINFO;
import github.zimoyin.core.fanju.pojo.info.seriesI.Episodes;
import github.zimoyin.core.fanju.pojo.info.seriesI.Result;
import github.zimoyin.core.fanju.pojo.info.seriesI.SeriesJsonRootBean;
import github.zimoyin.core.live.video.LiveVideoURL;
import github.zimoyin.core.live.video.data.Quality;
import github.zimoyin.core.utils.IDConvert;
import github.zimoyin.core.video.download.VideoDownload;
import github.zimoyin.core.video.download.VideoDownloadSetting;
import github.zimoyin.core.video.info.VideoInfo;
import github.zimoyin.core.video.info.pojo.info.Data;
import github.zimoyin.core.video.info.pojo.info.WEBVideoINFOJsonRootBean;
import github.zimoyin.core.video.info.pojo.info.data.Pages;
import github.zimoyin.core.video.info.pojo.info.data.Stat;
import github.zimoyin.core.video.info.pojo.online.OnlinePopulationRootBean;
import github.zimoyin.core.video.url.VideoURLPreviewFormatP1080;
import github.zimoyin.core.video.url.pojo.VideoURLJsonRoot;
import org.apache.http.HttpException;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.BiConsumer;

public class T {
    @Test
    public void s() throws HttpException, IOException, NoSuchAlgorithmException, KeyStoreException, URISyntaxException, KeyManagementException, CodeException {
        System.out.println(IDConvert.BvToCID("BV1PE411i7CV"));
        SeriesINFO seriesINFO = new SeriesINFO();
        Episodes ss = seriesINFO.getEpisodes("ss39661");
        System.out.println(ss.getBvid());
        System.out.println(ss.getCid());
    }
}