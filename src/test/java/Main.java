import github.zimoyin.core.cookie.GlobalCookie;
import github.zimoyin.core.download.download.VideoDownload;
import github.zimoyin.core.download.download.setting.DownloadVideoInfo;
import github.zimoyin.core.download.download.setting.DownloadVideoSetting;
import github.zimoyin.core.download.param.Fnval;
import github.zimoyin.core.download.param.ParamBuilder;
import github.zimoyin.core.download.param.QN;
import github.zimoyin.core.exception.CookieNotFoundException;
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

        DownloadVideoSetting setting = new DownloadVideoSetting(builder);
        setting.setCookie(GlobalCookie.getInstance());
        DownloadVideoInfo page = setting.getPage();
        System.out.println(page.getTitle());

        new VideoDownload(setting).download();
    }
}
