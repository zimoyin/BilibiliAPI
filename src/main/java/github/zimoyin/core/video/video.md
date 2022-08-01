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
### 视频推荐
> github.zimoyin.core.video.info.VideoRecommendation
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
```java
ArrayList<URL> 视频URL = new VideoURLPreviewFormatP360().getURLs("bv号");
```
### 预览视频URL信息(1080P,不需要cookie和防盗链)
> github.zimoyin.core.video.url.VideoURLPreviewFormatP1080
### 视频下载（上古案例）
> github.zimoyin.core.video.url.download.WebVideoDownload
新案例请见: github.zimoyin.core.video.Main
```java
        //下载器设置
        VideoDownloadSetting setting = new VideoDownloadSetting();
        setting.setBv("BV11L411G7he");
        setting.setPage(1);
        setting.setCookie(Cookie.readCookie("./cache/webcookie.json"));
        setting.setQn(QN.P1080_60_cookie_vip);
        setting.setFnval(Fnval.VideoFormat_dash);
        setting.build();
        //获取下载器
        VideoDownload videoDownload = new VideoDownload(setting);
        //设置下载控制器
        DownloadControl control = new DownloadControl();
        //设置监听
        control.setListenDownloadSize(new DownloadHandle<DownloadControl.DownloadInfo, Object>() {
@Override
public Object handle(DownloadControl.DownloadInfo t) {
//                System.out.print("\r下载进度："+t.getDownloadSize()/(double)t.getFileSize()+" %");
        System.out.print("\r"+t.getDownloadSize()+" : "+t.getFileSize());
        if (t.getDownloadSize()/(double)t.getFileSize() == 1) {
        System.out.println();
        System.out.println("下载完毕: "+setting.getFileName());
        System.out.println("文件大小: "+(double)t.getFileSize()/1024/1024+" mb");
        System.out.println("用时: "+(double) (System.currentTimeMillis() - control.getInitTime()) / 1000+"s");
        }
        return super.handle(t);
        }
        });
        //控制器添加到下载器中
        videoDownload.setControl(control);
        //开始下载
        ArrayList<Future<DownloadResult>> futures = videoDownload.downloadThread(true);
        //下载结果信息
//        for (Future<DownloadResult> future : futures) {
//            System.out.println(future.get());
//        }
        //合并文件
        videoDownload.merge();
```
