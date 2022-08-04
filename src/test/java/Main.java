import com.alibaba.fastjson.JSONObject;
import github.zimoyin.core.exception.CodeException;
import github.zimoyin.core.exception.URLFormatException;
import github.zimoyin.core.live.video.data.Quality;
import github.zimoyin.core.utils.JsonSerializeUtil;
import github.zimoyin.core.video.Video;
import github.zimoyin.core.video.download.DownloadControl;
import github.zimoyin.core.video.download.DownloadHandle;
import github.zimoyin.core.video.download.VideoDownload;
import github.zimoyin.core.video.download.VideoDownloadSetting;
import github.zimoyin.core.video.info.InteractVideoInfo;
import github.zimoyin.core.video.info.Node;
import github.zimoyin.core.video.info.VideoInfo;
import github.zimoyin.core.video.info.pojo.info.Data;
import github.zimoyin.core.video.info.pojo.info.WEBVideoINFOJsonRootBean;
import github.zimoyin.core.video.info.pojo.info.data.Owner;
import github.zimoyin.core.video.info.pojo.info.data.Stat;
import github.zimoyin.core.video.url.VideoURLFormat;
import github.zimoyin.core.video.url.data.Fnval;
import github.zimoyin.core.video.url.data.QN;
import jdk.nashorn.internal.parser.JSONParser;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Set;

public class Main {
    public static void main(String[] args) throws Exception {
        VideoDownload videoDownload = new VideoDownload();
        videoDownload.setListens(new DownloadHandle() {
            @Override
            public void handle(DownloadControl.DownloadInfo info) {
                info.isFinished();
                info.downloadProgress();
            }
        });
    }
}
