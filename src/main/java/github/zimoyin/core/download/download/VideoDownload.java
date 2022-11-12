package github.zimoyin.core.download.download;

import github.zimoyin.core.download.download.setting.DownloadVideoSetting;
import github.zimoyin.core.exception.DownloadException;
import github.zimoyin.core.utils.net.httpclient.VideoDownloadUtil;
import github.zimoyin.core.video.download.DownloadResult;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.Future;

/**
 * 完成基本内容
 */
@Getter
public class VideoDownload implements VideoDownloadInterface {

    private final DownloadVideoSetting setting;

    public VideoDownload(DownloadVideoSetting setting) {
        this.setting = setting;
    }

    @Override
    public boolean download() throws DownloadException {
        try {
            ArrayList<URL> list = setting.getPage().getURL();
            for (int i = 0; i < list.size(); i++) {
                String path = setting.getFilePath() + "/" + setting.getPage().getTitle() + "-" + i + "." + setting.getPage().getType();
                VideoDownloadUtil.downloadFile(path, list.get(i));
            }
        } catch (IOException e) {
            throw new DownloadException("下载失败", e);
        }
        return false;
    }

    @Override
    public void downloadAsynchronous() throws DownloadException {
    //TODO
    }

    @Override
    public ArrayList<Future<DownloadResult>> downloadThread(boolean isWaitTakeFinish) throws DownloadException {
    //TODO
        return null;
    }

    @Override
    public void stop() throws DownloadException {
    //TODO
    }
}
