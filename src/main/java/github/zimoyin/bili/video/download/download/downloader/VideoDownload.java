package github.zimoyin.bili.video.download.download.downloader;

import github.zimoyin.bili.exception.DownloadException;
import github.zimoyin.bili.utils.AllocateBytes;
import github.zimoyin.bili.utils.net.httpclient.NetFileUtil;
import github.zimoyin.bili.utils.net.httpclient.VideoDownloadUtil;
import github.zimoyin.bili.video.download.download.DownloadResult;
import github.zimoyin.bili.video.download.download.event.DownloadHandle;
import github.zimoyin.bili.video.download.download.event.DownloadingINFO;
import github.zimoyin.bili.video.download.setting.DownloadVideoSettingAbs;
import lombok.Getter;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

/**
 * 基础视频下载器
 */
@Getter
public class VideoDownload implements VideoDownloadInterface {

    private final DownloadVideoSettingAbs setting;
    private final ArrayList<DownloadHandle> handles = new ArrayList<>();
    private DownloadingINFO Info;
    private boolean stop = false;
    private String filePath;

    public VideoDownload(DownloadVideoSettingAbs setting) {
        this.setting = setting;
    }


    @Override
    public boolean download() throws DownloadException {
        //单线程下载，强制设置线程数为 1
        setting.setThreadCount(1);
        try {
            ArrayList<URL> list = setting.getPage().getURL();
            for (int i = 0; i < list.size(); i++) {
                if (stop) break;
                filePath = buildPath(i);
                DownloadingINFO info = toDownloadingINFOBuild(
                        list.get(i),
                        setting,
                        list.size(),
                        (i + 1),
                        filePath
                );
                this.Info = info;
                VideoDownloadUtil.downloadFile(filePath, list.get(i), info);
            }
        } catch (Exception e) {
            throw new DownloadException("下载失败", e);
        }
        return false;
    }

    private String buildPath(int i){
        String fileName = setting.getPage().getTitle() + "-" + i + "." + setting.getPage().getType();
        fileName = setting.buildFileName(fileName);
        return setting.getFilePath() + "/" + fileName;
    }

    /**
     * 构建视频信息
     *
     * @param url        视频的地址
     * @param taskCount  任务数（dash下为2）
     * @param taskNumber 当前是第几个任务
     * @param filePath   文件保存位置
     */
    private DownloadingINFO toDownloadingINFOBuild(URL url, DownloadVideoSettingAbs setting, int taskCount, int taskNumber, String filePath) throws IOException, NoSuchAlgorithmException, KeyStoreException, URISyntaxException, KeyManagementException {
        HashMap<String, String> headers = new HashMap<>();
        long fileSize = NetFileUtil.getFileLength2(url.toString(), headers);
        DownloadingINFO info = new DownloadingINFO(setting.getPage(), setting.getThreadCount(), taskCount, fileSize, filePath);
        info.setTaskNumber(taskNumber);
        info.addAllHandle(handles);
        return info;
    }

    /**
     * 设置监听
     *
     * @param listener
     */
    public void setListener(DownloadHandle listener) {
        handles.add(listener);
    }

    @Override
    public void downloadAsynchronous() throws DownloadException {
        setting.setThreadCount(1);
        downloadThread(false);
    }

    @Override
    public ArrayList<Future<DownloadResult>> downloadThread(boolean isWaitTakeFinish) throws DownloadException {
        ArrayList<Future<DownloadResult>> futures = new ArrayList<>();
        try {
            int threadCount = setting.getThreadCount();
            ArrayList<URL> list = setting.getPage().getURL();
            //遍历链接
            for (int i = 0; i < list.size(); i++) {
                if (stop) break;
                filePath = buildPath(i);
                URL url = list.get(i);
                DownloadingINFO info = toDownloadingINFOBuild(
                        url,
                        setting,
                        list.size(),
                        (i + 1),
                        filePath
                );
                this.Info = info;
                //下载逻辑
                HashMap<Long, Long> bytes = AllocateBytes.CalculationBytes(threadCount, info.getFileFinalSize());
                bytes.forEach((start, end) -> {
                    FutureTask<DownloadResult> task = new FutureTask<>(() -> {
                        Thread.currentThread().setName("downloading-" + start + "-" + end);
                        return VideoDownloadUtil.downloadPartFile(
                                url.toString(),
                                filePath,
                                start,
                                end,
                                info
                        );
                    });
                    futures.add(task);
                    new Thread(task).start();
                });
            }
        } catch (Exception e) {
            throw new DownloadException("下载失败", e);
        }

        //等待所有的任务完成
        if (isWaitTakeFinish) for (Future<DownloadResult> task : futures) {
            try {
                task.get();
            } catch (Exception e) {
                throw new DownloadException("等待视频下载完毕时出现异常，异常原因大概为未能正常关闭资源导致", e);
            }
        }
        return futures;
    }

    /**
     * 下载所有分P
     */
    @Override
    public ArrayList<Future<DownloadResult>> downloadAll() {
        ArrayList<Future<DownloadResult>> futures = new ArrayList<>();
        for (int i = 0; i < setting.getSize(); i++) {
            setting.updateDownloadPage(i + 1);
            futures.addAll(downloadThread(true));
        }
        return futures;
    }


    @Override
    public void stop() throws DownloadException {
        stop = true;
        this.Info.stop();
    }
}
