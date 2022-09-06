所有pojo基本都有data对象，每个pojo的data类都不一样，因此禁止用data传值，定义全局变量  
在video.Main 里面有一些例子  
# 视频操作
### 视频操作集合
> github.zimoyin.core.video.Video
### 视频信息
> github.zimoyin.core.video.info.VideoInfo  
注意如果你想要下载CC字幕请查看 CC类下的subtitle_url字段，他是一个下载字幕的URL。    
对于如何获取CC对象： 他是 json pojo -> WEBVideoINFOJsonRootBean.data.subtitle.list.cc  
#### 视频信息摘要
> github.zimoyin.core.video.info.VideoINFOSummary
### 在线观看人数
> github.zimoyin.core.video.info.VideoOnlinePopulation
### 视频标签信息
> github.zimoyin.core.video.info.VideoTAG
#### 视频标签点赞等
> github.zimoyin.core.video.info.VideoTAG
### 点赞、投币、收藏等
> github.zimoyin.core.video.operation.VideoStatusOperation
### 视频快照（图片墙）
> github.zimoyin.core.video.info.VideoSnapshot
### 视频推荐（根据一个视频推荐同类型视频）
> github.zimoyin.core.video.info.VideoRecommendation
### 视频首页推荐（有cookie则是个性推荐）
> github.zimoyin.core.video.info.VideoHomePageRecommendation
### 视频进度上报
> github.zimoyin.core.video.operation.VideoProgress
### 视频高能进度条
> github.zimoyin.core.video.info.VideoBarrageCurve
### 互动视频
> github.zimoyin.core.video.info.InteractVideo
### 视频url信息（需要有防盗链与部分视频需要cookie）
> github.zimoyin.core.video.url.WEBVideoURLFormat
### 预览视频URL信息(360P,不需要cookie和防盗链)
> github.zimoyin.core.video.url.VideoURLPreviewFormatP360
```
ArrayList<URL> 视频URL = new VideoURLPreviewFormatP360().getURLs("bv号");
```
### 预览视频URL信息(1080P,不需要cookie和防盗链)
> github.zimoyin.core.video.url.VideoURLPreviewFormatP1080
### 视频下载（上古案例）
> github.zimoyin.core.video.url.download.WebVideoDownload
下载视频需要配置三个东西：  
* VideoDownloadSetting：下载设置类，用来设置下载什么类型的视频包括清晰度
* VideoDownload： 下载器，下载器会根据 VideoDownloadSetting 的实例去获取对应的下载URl，之后会根据URL去调用响应的底层下载器
* DownloadHandle: 下载器监听类：用来监听下载器下载文件的进度等待，但是多线程可能导致当前统计的已经下载的字节数小于实际的下载字节数。所以判断是否下载完成可以通过线程完成数来进行
```java
     class Demo{
    /**
     * 下载视频
     */
    public void test00() {
        VideoDownloadSetting setting = new VideoDownloadSetting();
        setting.setBv("BV1oa411K7MG");
        setting.setFnval(Fnval.VideoFormat_mp4);
        setting.setQn(QN.P306);

        VideoDownload videoDownload = new VideoDownload();
        videoDownload.setSetting(setting);
        videoDownload.setListens(info -> System.out.print("\r下载进度：" + info.getDownloadSize() + "/" + info.getFileSize()));
        videoDownload.download();
//        videoDownload.downloadThread(true);
    }

    /**
     * 下载所有p视频
     */
    public void test01() {
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
                System.out.print("\r" + info.getDownloadSize() + "/" + info.getFileSize());
            }
        });

//        videoDownload.downloadThread(true);
//        videoDownload.download();
        for (int i = 0; i < setting.getPageCount(); i++) {
            System.out.print("download: " + (i + 1) + "p ");
            setting.setPage(i + 1);
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
            System.out.print("download :" + cidMaps.get(cid));
            setting.setCid(cid);
            setting.setPage(cidMaps.get(cid).getLevel());
            setting.setPageName(cidMaps.get(cid).getTitle());
            try {
//                videoDownload.download();
                videoDownload.downloadThread(true);
            } catch (Exception e) {
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
    public void test04() {
        VideoDownloadSetting setting = new VideoDownloadSetting();
        setting.setBv("BV1ZY4y187fA");
        setting.setFnval(Fnval.VideoFormat_mp4);
        setting.setQn(QN.P306);
        setting.setCid(785726095);

        VideoDownload videoDownload = new VideoDownload();
        videoDownload.setSetting(setting);
        videoDownload.setListens(info -> System.out.print("\r下载进度：" + info.getDownloadSize() + "/" + info.getFileSize()));
        videoDownload.download();
    }


    /**
     * 下载番剧
     * 需要ep或者ssid
     */
    public void test05() {
        VideoDownloadSetting setting = new VideoDownloadSetting();
        setting.setEp("ep567956");
        setting.setFnval(Fnval.VideoFormat_mp4);
        setting.setQn(QN.P306);
        //设置剧集，如果不设置的话根据ep来下载，如果是ssid则下载第一集
        setting.setPage(4);

        VideoDownload videoDownload = new VideoDownload();
        videoDownload.setSetting(setting);
        videoDownload.setListens(info -> System.out.print("\r下载进度：" + info.getDownloadSize() + "/" + info.getFileSize()));
        videoDownload.downloadThread(true);
    }

    /**
     * 无需 cookie 下载 1080p mp4格式视频
     * 注意仅限 1080p 与 mp4格式的视频，不能是番剧或其他
     */
    public void test06() {
        VideoDownloadSetting setting = new VideoDownloadSetting();
        setting.setBv("BV1jG4y1Y77x");
        setting.setPreview1080p(true);


        VideoDownload videoDownload = new VideoDownload();
        videoDownload.setSetting(setting);
        videoDownload.setListens(info -> System.out.print("\r下载进度：" + info.getDownloadSize() + "/" + info.getFileSize()));
        videoDownload.downloadThread(true);

    }
}
```
