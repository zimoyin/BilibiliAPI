import github.zimoyin.core.video.download.*;
import github.zimoyin.core.video.info.VideoHomePageRecommendation;
import github.zimoyin.core.video.info.pojo.recommendation.VideoHomeRecommendationRootBean;
import github.zimoyin.core.video.url.data.Fnval;
import github.zimoyin.core.video.url.pojo.VideoURLJsonRoot;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.function.Consumer;

public class Main {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        VideoDownloadSetting videoDownloadSetting = new VideoDownloadSetting("BV1PE411i7CV");
        videoDownloadSetting.setFnval(Fnval.VideoFormat_dash);
        videoDownloadSetting.setPage(3);
        VideoDownload videoDownload = new VideoDownload();
        videoDownload.setSetting(videoDownloadSetting);
        System.out.println(videoDownload.getUrlPojo(true).getURLs());
//        ArrayList<Future<DownloadResult>> futures = videoDownload.downloadThread(true);
//        for (Future<DownloadResult> future : futures) {
//            DownloadResult result = future.get();
//            System.out.println(result);
//        }

    }
}