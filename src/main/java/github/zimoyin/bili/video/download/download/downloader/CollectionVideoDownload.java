package github.zimoyin.bili.video.download.download.downloader;

import github.zimoyin.bili.exception.DownloadException;
import github.zimoyin.bili.video.download.download.DownloadResult;
import github.zimoyin.bili.video.download.setting.CollectionVideoSetting;

import java.util.ArrayList;
import java.util.Set;
import java.util.concurrent.Future;

/**
 * 视频集合下载器，用于根据用户mid下载。
 * 根据集合sid下载，随便一个下载器都可，但是要使用 DownloadCollectionVideoSetting
 */
public class CollectionVideoDownload extends VideoDownload implements VideoDownloadInterface {

    private final CollectionVideoSetting setting;

    public CollectionVideoDownload(CollectionVideoSetting setting) {
        super(setting);
        this.setting = setting;
    }

    @Override
    public boolean download() throws DownloadException {
        String path = setting.getFilePath();
        setting.setFilePath(path+"/"+setting.getCollectionMap().get(setting.getSid()));
        super.downloadAll();
        return true;
    }

    @Override
    @Deprecated
    public ArrayList<Future<DownloadResult>> downloadThread(boolean isWaitTakeFinish) throws DownloadException {
        return super.downloadThread(isWaitTakeFinish);
    }

    /**
     * 下载UP的所有合集的视频
     * @return
     */
    @Override
    public ArrayList<Future<DownloadResult>> downloadAll() {
        Set<Long> sids = setting.getCollectionMap().keySet();
        ArrayList<Future<DownloadResult>> futures = new ArrayList<Future<DownloadResult>>();
        String path = setting.getFilePath();
        for (Long sid : sids) {
            setting.setSid(sid);
            setting.setFilePath(path+"/"+setting.getCollectionMap().get(sid));
            futures.addAll(super.downloadAll());
        }
        return futures;
    }
}
