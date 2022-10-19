import github.zimoyin.core.cookie.GlobalCookie;
import github.zimoyin.core.login.Login;
import github.zimoyin.core.login.login.LoginImpl;
import github.zimoyin.core.video.download.VideoDownloadSetting;
import github.zimoyin.core.video.url.VideoURLFormat;
import github.zimoyin.core.video.url.data.Fnval;
import github.zimoyin.core.video.url.data.QN;
import github.zimoyin.core.video.url.pojo.VideoURLJsonRoot;

public class Main {
    public static void main(String[] args) throws Exception {
        System.out.println(GlobalCookie.getInstance().toStringV2());
        VideoDownloadSetting videoDownloadSetting = new VideoDownloadSetting("BV1U34y1R7gM");
        videoDownloadSetting.setFnval(Fnval.VideoFormat_dash);
        videoDownloadSetting.setQn(QN.P8k_cookie_vip);
        videoDownloadSetting.setPreview1080p(false);

        VideoURLFormat videoURLFormat = new VideoURLFormat(GlobalCookie.getInstance());
        VideoURLJsonRoot jsonPOJO = videoURLFormat.getJsonPOJO(videoDownloadSetting);
        System.out.println(jsonPOJO.getUri());
        System.out.println(jsonPOJO);
    }
}