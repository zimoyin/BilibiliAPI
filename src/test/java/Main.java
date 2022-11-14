import github.zimoyin.bili.cookie.GlobalCookie;
import github.zimoyin.bili.video.download.download.VideoDownload;
import github.zimoyin.bili.video.download.setting.info.DownloadVideoInfo;
import github.zimoyin.bili.video.download.setting.DownloadVideoSetting;
import github.zimoyin.bili.video.url.param.Fnval;
import github.zimoyin.bili.video.url.param.ParamBuilder;
import github.zimoyin.bili.video.url.param.QN;
import github.zimoyin.bili.exception.CookieNotFoundException;
import org.apache.http.HttpException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

public class Main {
    public static void main(String[] args) throws IOException, NoSuchAlgorithmException, KeyStoreException, URISyntaxException, KeyManagementException, HttpException, CookieNotFoundException {
        long cid = 878104053;
        String bv = "BV1Ce4y147dS";

        ParamBuilder builder = new ParamBuilder()
                .append(bv)
//                .append(cid)
                .append(QN.P306)
                .append(Fnval.Dash);

        DownloadVideoSetting setting = new DownloadVideoSetting("ep674245");
        setting.setCookie(GlobalCookie.getInstance());
        DownloadVideoInfo page = setting.getPage();
        System.out.println(page.getTitle());

        VideoDownload download = new VideoDownload(setting);
        download.setListener(info -> System.out.print("\r" + info.getThreadName() + ": " + info));
        download.downloadAsynchronous();
    }
}
