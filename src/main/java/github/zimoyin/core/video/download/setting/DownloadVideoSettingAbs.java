package github.zimoyin.core.video.download.setting;

import github.zimoyin.core.cookie.Cookie;
import github.zimoyin.core.video.download.setting.info.DownloadVideoInfo;
import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.util.function.Consumer;

public abstract class DownloadVideoSettingAbs {
    private String filePath = "./download";
    @Setter
    @Getter
    private int threadCount = 32;

    /**
     * 设置要下载的视频的page（p数）
     */
    public abstract void updateDownloadPage(int page);

    public abstract DownloadVideoInfo getPage();

    public abstract DownloadVideoInfo getPage(int page);


    public abstract int getSize();

    public abstract void forEach(Consumer<? super DownloadVideoInfo> action);

    public abstract void setCookie(Cookie cookie);


    public String getFilePath() {
        File file = new File(filePath);
        boolean mkdirs = file.mkdirs();
        if (file.exists() && file.isFile()) throw new IllegalStateException("路径不存在或者路径不为一个文件夹");
       return filePath;
    }

    public void setFilePath(String filePath) {
        File file = new File(filePath);
        if (file.exists() && file.isFile()) throw new IllegalStateException("请指定路径为文件夹");
        boolean mkdirs = file.mkdirs();
        this.filePath = filePath;
    }
}
