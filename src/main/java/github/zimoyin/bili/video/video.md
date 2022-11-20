所有pojo基本都有data对象，每个pojo的data类都不一样，因此禁止用data传值，定义全局变量  
在video.Main 里面有一些例子

# 视频操作

### 视频操作集合

> github.zimoyin.core.video.Video

### 视频信息

> github.zimoyin.core.video.info.VideoInfo  
> 注意如果你想要下载CC字幕请查看 CC类下的subtitle_url字段，他是一个下载字幕的URL。    
> 对于如何获取CC对象： 他是 json pojo -> WEBVideoINFOJsonRootBean.data.subtitle.list.cc

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
获取视频下载的URL，示例（过时）
```
VideoURLFormat urlFormat = new VideoURLFormat();
VideoURLJsonRoot pojo = urlFormat.getJsonPOJO(setting);
pojo.getURLs().forEach(new Consumer<URL>() {
    @Override
    public void accept(URL url) {
        System.out.println(url);
    }
});
```

### 预览视频URL信息(360P,不需要cookie和防盗链)

> github.zimoyin.core.video.url.VideoURLPreviewFormatP360

```
ArrayList<URL> 视频URL = new VideoURLPreviewFormatP360().getURLs("bv号");
```

### 预览视频URL信息(1080P,不需要cookie和防盗链)

> github.zimoyin.core.video.url.VideoURLPreviewFormatP1080

### 视频下载（上古案例）
下载视频需要 下载器、设置器、监听器  
* 下载器: 位于 github.zimoyin.bili.video.download.download.downloader 包下，每个下载器作用不同，但是基本相同
    - VideoDownload 是最基础的下载器，允许下载 番剧、剧集、普通视频  
* 设置器: 位于 github.zimoyin.bili.video.download.setting 包下，请根据下载器去选择设置器
    - DownloadVideoSetting 是最基础设置器
* 监听器： VideoDownload.setListener(new DownloadHandle());
* 对于 下载器、监听器 除了基础类以外，不再对其进行维护
* 对于互动视频下载，请自行实现； 依照 InteractVideoInfo 获取cid，bvid，后自行调用 VideoDownload 下载
* 对于收藏夹视频：咕咕咕
案例：  
```
DownloadVideoSetting setting = new DownloadVideoSetting("BV1Zx41197jJ");
VideoDownload download = new VideoDownload(setting);
download.setListener(info -> System.out.print("\r" + info.getPage().getTitle() + ": " + info.getFileSize() + "/" + info.getFileFinalSize()));
download.download();
```