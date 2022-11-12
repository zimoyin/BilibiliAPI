package github.zimoyin.core;

import github.zimoyin.core.cookie.GlobalCookie;
import github.zimoyin.core.exception.CookieNotFoundException;
import github.zimoyin.core.video.download.DownloadControl;
import github.zimoyin.core.video.download.DownloadHandle;
import github.zimoyin.core.video.download.VideoDownload;
import github.zimoyin.core.video.download.VideoDownloadSetting;
import github.zimoyin.core.video.url.data.Fnval;
import github.zimoyin.core.video.url.data.QN;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws CookieNotFoundException {
        VideoDownloadSetting setting = new VideoDownloadSetting("ep508404", GlobalCookie.getInstance());
//        setting.setBv("BV1Qa411p7o8");//视频
//        setting.setID("ep508404");//番剧
        setting.setFnval(Fnval.VideoFormat_mp4);
        setting.setQn(QN.P306);

        VideoDownload videoDownload = new VideoDownload();
        videoDownload.setSetting(setting);
        videoDownload.setListens(info -> System.out.print("\r" + info.getDownloadSize() + "/" + info.getFileSize()));

        for (int i = 3; i <= setting.getPageCount(); i++) {
            setting.setPage(i);
            setting.update();
            System.out.println(setting.getBv());
            System.out.println(setting.getCid());
//            System.out.print("download: " + i + "p ");
//            videoDownload.downloadThread(true);
//            System.out.print(" done\r\n");
            System.out.println("===============================================");
        }
    }
}
