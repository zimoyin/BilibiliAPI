package github.zimoyin.bili.video.download.download.downloader;

import github.zimoyin.bili.exception.DownloadException;
import github.zimoyin.bili.video.download.download.DownloadResult;
import github.zimoyin.bili.video.download.download.event.DownloadHandle;
import github.zimoyin.bili.video.download.setting.DownloadVideoSettingAbs;
import github.zimoyin.bili.video.download.setting.UserVideoSetting;
import github.zimoyin.bili.video.download.setting.VideoSetting;
import github.zimoyin.bili.video.download.setting.info.DownloadVideoInfoList;

import java.util.ArrayList;
import java.util.concurrent.Future;

public class UserVideoDownload extends VideoDownload implements VideoDownloadInterface {
    private final UserVideoSetting setting;

    public UserVideoDownload(UserVideoSetting setting) {
        super(setting);
        this.setting = setting;
    }

    @Override
    @Deprecated
    public boolean download() throws DownloadException {
        return super.download();
    }

    @Override
    @Deprecated
    public void downloadAsynchronous() throws DownloadException {
        super.downloadAsynchronous();
    }

    @Override
    @Deprecated
    public ArrayList<Future<DownloadResult>> downloadThread(boolean isWaitTakeFinish) throws DownloadException {
        return super.downloadThread(isWaitTakeFinish);
    }

    /**
     * 下载用户的所有视频
     * @return
     */
    @Override
    public ArrayList<Future<DownloadResult>> downloadAll() {
        ArrayList<Future<DownloadResult>> futures = new ArrayList<Future<DownloadResult>>();
        for (int i = 1; i <= setting.getSize(); i++) {
            String path = setting.getFilePath();
            setting.updateDownloadPage(i);
            DownloadVideoInfoList pages = setting.getPages();
            ArrayList<DownloadHandle> handles = getHandles();
            if (pages.size() > 1) path = path + "/" + setting.buildFileName(setting.getPage().getTitle());
            //设置下载器
            VideoSetting videoSetting = new VideoSetting(pages.getParam(), pages.getID(), setting.getCookie());
            videoSetting.setFilePath(path);
            videoSetting.setThreadCount(setting.getThreadCount());
            VideoDownload download = new VideoDownload(videoSetting);
            download.getHandles().addAll(handles);
            futures.addAll(download.downloadAll());
        }
        return futures;
    }
}
