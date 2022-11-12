package github.zimoyin.core.download.download.setting;

import github.zimoyin.core.cookie.Cookie;

import java.util.function.Consumer;

public abstract class DownloadVideoSettingAbs {
    /**
     * 设置要下载的视频的page（p数）
     */
    public abstract void updateDownloadPage(int page);
    public abstract DownloadVideoInfo getPage();
    public abstract DownloadVideoInfo getPage(int page);


    public abstract int getSize();
    public abstract void forEach(Consumer<? super DownloadVideoInfo> action);

    public abstract void setCookie(Cookie cookie);
    public abstract void setFilePath(String filePath);
}
