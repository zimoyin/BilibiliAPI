
import github.zimoyin.core.cookie.Cookie;
import github.zimoyin.core.video.download.*;
import github.zimoyin.core.video.info.InteractVideoInfo;
import github.zimoyin.core.video.info.Node;
import github.zimoyin.core.video.url.data.Fnval;
import github.zimoyin.core.video.url.data.QN;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.function.BiConsumer;

public class T {
//    public static void main(String[] args) throws InterruptedException, ExecutionException, IOException {
//        VideoDownloadSetting setting = new VideoDownloadSetting();
////        setting.setBv("BV11L411G7he");
//        setting.setBv("BV1QV411i7m7");
//        setting.setPage(1);
//        setting.setCookie(Cookie.readCookie("./cache/webcookie.json"));
//        setting.setQn(QN.P1080_60_cookie_vip);
//        setting.setFnval(Fnval.VideoFormat_dash);
//
//        VideoDownload videoDownload = new VideoDownload(setting);
//
//
//        DownloadControl control = new DownloadControl();
//        control.setListenDownloadSize(new DownloadHandle<DownloadControl.DownloadInfo, Object>() {
//            @Override
//            public Object handle(DownloadControl.DownloadInfo t) {
////                System.out.print("\r下载进度："+t.getDownloadSize()/(double)t.getFileSize()+" %");
//                System.out.print("\r"+t.getDownloadSize()+" : "+t.getFileSize());
//                if (t.getDownloadSize()/(double)t.getFileSize() == 1) {
//                    System.out.println();
//                    System.out.println("下载完毕: "+setting.getFileName());
//                    System.out.println("文件大小: "+(double)t.getFileSize()/1024/1024+" mb");
//                    System.out.println("用时: "+(double) (System.currentTimeMillis() - control.getInitTime()) / 1000+"s");
//                }
//                return super.handle(t);
//            }
//        });
//        videoDownload.setControl(control);
//
//
////        ArrayList<Future<DownloadResult>> futureArrayList = new ArrayList<Future<DownloadResult>>();
////        InteractVideoInfo interactVideoInfo = new InteractVideoInfo(setting.getBv());
////        interactVideoInfo.getCidMaps().forEach(
////                new BiConsumer<Long, Node>() {
////                    @Override
////                    public void accept(Long aLong, Node node) {
////                        System.out.println(aLong+":"+node);
////                        setting.setCid(aLong);
////                        setting.setPageName(node.getTitle());
////                        setting.build();
////
//////                        ArrayList<Future<DownloadResult>> futures = videoDownload.downloadThread(false);
//                        videoDownload.download();
////                    }
////                }
////        );
//
//
//
//
////        ArrayList<Future<DownloadResult>> futures = videoDownload.downloadThread(true);
////        for (Future<DownloadResult> future : futureArrayList) {
////            System.out.println(future.get());
////        }
////        videoDownload.mergeAll();
//        System.out.println("main -> 主程校验：ok");
//
//    }
}
