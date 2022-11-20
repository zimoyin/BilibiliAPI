package github.zimoyin.bili.video.download.download.downloader;

import github.zimoyin.bili.exception.DownloadException;
import github.zimoyin.bili.video.download.download.DownloadResult;
import github.zimoyin.bili.video.download.download.event.DownloadHandle;

import java.util.ArrayList;
import java.util.concurrent.Future;


public  interface VideoDownloadInterface {
    /**
     * 设置监听
     * @param listener 监听器
     */
    public abstract void setListener(DownloadHandle listener);
    /**
     * 下载视频
     *
     * @throws DownloadException
     */
    public abstract boolean download() throws DownloadException;

    /**
     * 异步单线程下载视频
     *
     * @throws DownloadException
     */
    public abstract void downloadAsynchronous() throws DownloadException;

    /**
     * 多线程下载视频: 注意多个线程对同一个文件进行读写操作，下载速度有所提升，但是可能导致灾难发生
     *
     * @param isWaitTakeFinish 是否阻塞主线程等待下载完毕
     * @throws DownloadException
     */
    public abstract ArrayList<Future<DownloadResult>> downloadThread(boolean isWaitTakeFinish) throws DownloadException;

    /**
     * 下载所有的关于设置维护的视频列表
     * @return
     */
    public ArrayList<Future<DownloadResult>> downloadAll();
    /**
     * 停止下载
     */
    public abstract void stop() throws DownloadException;
}
