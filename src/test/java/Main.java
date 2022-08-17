import github.zimoyin.core.video.download.DownloadControl;
import github.zimoyin.core.video.download.DownloadHandle;
import github.zimoyin.core.video.download.VideoDownload;
import github.zimoyin.core.video.download.VideoDownloadSetting;
import github.zimoyin.core.video.url.pojo.VideoURLJsonRoot;

import java.net.URL;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        VideoDownloadSetting videoDownloadSetting = new VideoDownloadSetting("BV1PE411i7CV");

        VideoDownload videoDownload = new VideoDownload();
        videoDownload.setSetting(videoDownloadSetting);

        boolean b = videoDownload.downloadInit();
        VideoURLJsonRoot urlPojo = videoDownload.getUrlPojo();
        ArrayList<URL> urLs = urlPojo.getURLs();
        for (URL urL : urLs) {
            System.out.println(urL);
        }
    }
}