package github.zimoyin.core.video;


import github.zimoyin.core.cookie.GlobalCookie;
import github.zimoyin.core.video.download.DownloadControl;
import github.zimoyin.core.video.download.DownloadHandle;
import github.zimoyin.core.video.download.VideoDownload;
import github.zimoyin.core.video.download.VideoDownloadSetting;
import github.zimoyin.core.video.info.InteractVideoInfo;
import github.zimoyin.core.video.info.Node;
import github.zimoyin.core.video.info.VideoInfo;
import github.zimoyin.core.video.info.pojo.info.WEBVideoINFOJsonRootBean;
import github.zimoyin.core.video.info.pojo.info.data.subtitle.CC;
import github.zimoyin.core.video.url.data.Fnval;
import github.zimoyin.core.video.url.data.QN;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * 案例
 */
public class Main {
    public static void main(String[] args) throws Exception {
//        Main main = new Main();
//        main.test00();

        VideoDownloadSetting setting = new VideoDownloadSetting();
        setting.setCookie(GlobalCookie.getInstance());
        setting.setBv("BV1jG4y1Y77x");
        setting.setFnval(Fnval.VideoFormat_dash);
        setting.setQn(QN.P1080_cookie);
        setting.setCookie(GlobalCookie.getInstance());


        VideoDownload videoDownload = new VideoDownload();
        videoDownload.setSetting(setting);
        videoDownload.setListens(info -> System.out.print("\r下载进度："+info.getDownloadSize()+"/"+info.getFileSize()));
        videoDownload.downloadThread(true);
        videoDownload.merge();
    }


    /**
     * 下载视频
     */
    public void test00(){
        VideoDownloadSetting setting = new VideoDownloadSetting();
        setting.setBv("BV1oa411K7MG");
        setting.setFnval(Fnval.VideoFormat_mp4);
        setting.setQn(QN.P306);

        VideoDownload videoDownload = new VideoDownload();
        videoDownload.setSetting(setting);
        videoDownload.setListens(info -> System.out.print("\r下载进度："+info.getDownloadSize()+"/"+info.getFileSize()));
        videoDownload.download();
//        videoDownload.downloadThread(true);
    }

    /**
     * 下载所有p视频
     */
    public void test01(){
        VideoDownloadSetting setting = new VideoDownloadSetting();
        setting.setBv("BV1Qa411p7o8");
        setting.setFnval(Fnval.VideoFormat_mp4);
        setting.setQn(QN.P306);
        setting.setPage(1);

        VideoDownload videoDownload = new VideoDownload();
        videoDownload.setSetting(setting);
        videoDownload.setListens(new DownloadHandle() {
            @Override
            public void handle(DownloadControl.DownloadInfo info) {
                System.out.print("\r"+info.getDownloadSize()+"/"+info.getFileSize());
            }
        });

//        videoDownload.downloadThread(true);
//        videoDownload.download();
        for (int i = 0; i < setting.getPageCount(); i++) {
            System.out.print("download: "+(i+1)+"p ");
            setting.setPage(i+1);
//            videoDownload.download();
            videoDownload.downloadThread(true);
            System.out.print(" done\r\n");
        }
    }


    /**
     * 下载所有的互动视频片段
     */
    public void test02() throws Exception {
        //获取互动视频的所有模块的cid
        InteractVideoInfo interactVideoInfo = new InteractVideoInfo("BV1hu411d7R6");
        HashMap<Long, Node> cidMaps = interactVideoInfo.getCidMaps();
        Set<Long> cids = cidMaps.keySet();
        System.out.println("互动视频遍历完毕");
        //配置下载设置
        VideoDownloadSetting setting = new VideoDownloadSetting();
        setting.setBv("BV1hu411d7R6");
        //获取下载器
        VideoDownload videoDownload = new VideoDownload();
        videoDownload.setSetting(setting);
        //下载
        //注意异常捕获，可能导致的异常有超时等
        for (long cid : cids) {
            System.out.print("download :"+cidMaps.get(cid));
            setting.setCid(cid);
            setting.setPage(cidMaps.get(cid).getLevel());
            setting.setPageName(cidMaps.get(cid).getTitle());
            try {
//                videoDownload.download();
                videoDownload.downloadThread(true);
            }catch (Exception e){
                e.printStackTrace();
            }
            System.out.println(" done");
        }
    }


    /**
     * 获取CC弹幕文件(json)的链接
     */
    public void test03() throws IOException, NoSuchAlgorithmException, KeyStoreException, URISyntaxException, KeyManagementException {
        VideoInfo videoInfo = new VideoInfo();
        WEBVideoINFOJsonRootBean videoInfoInfo = videoInfo.getInfo("BV1H44y1a7JM");
        List<CC> list = videoInfoInfo.getData().getSubtitle().getList();
        for (CC cc : list) {
            System.out.println(cc.getSubtitle_url());
        }
    }


    /**
     * 下载番剧
     * 需要bv与cid
     */
    public void test04(){
        VideoDownloadSetting setting = new VideoDownloadSetting();
        setting.setBv("BV1ZY4y187fA");
        setting.setFnval(Fnval.VideoFormat_mp4);
        setting.setQn(QN.P306);
        setting.setCid(785726095);

        VideoDownload videoDownload = new VideoDownload();
        videoDownload.setSetting(setting);
        videoDownload.setListens(info -> System.out.print("\r下载进度："+info.getDownloadSize()+"/"+info.getFileSize()));
        videoDownload.download();
    }



    /**
     * 下载番剧
     * 需要ep或者ssid
     */
    public void test05(){
        VideoDownloadSetting setting = new VideoDownloadSetting();
        setting.setEp("ep567956");
        setting.setFnval(Fnval.VideoFormat_mp4);
        setting.setQn(QN.P306);
        //设置剧集，如果不设置的话根据ep来下载，如果是ssid则下载第一集
        setting.setPage(4);

        VideoDownload videoDownload = new VideoDownload();
        videoDownload.setSetting(setting);
        videoDownload.setListens(info -> System.out.print("\r下载进度："+info.getDownloadSize()+"/"+info.getFileSize()));
        videoDownload.downloadThread(true);
    }
}
