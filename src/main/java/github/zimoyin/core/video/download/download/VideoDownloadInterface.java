package github.zimoyin.core.video.download.download;

import github.zimoyin.core.exception.DownloadException;

import java.util.ArrayList;
import java.util.concurrent.Future;


public  interface VideoDownloadInterface {
    /**
     * 下载视频
     *
     * @throws Exception
     */
    public abstract boolean download() throws DownloadException;

    /**
     * 异步单线程下载视频
     *
     * @throws Exception
     */
    public abstract void downloadAsynchronous() throws DownloadException;

    /**
     * 多线程下载视频: 注意多个线程对同一个文件进行读写操作，下载速度有所提升，但是可能导致灾难发生
     *
     * @param isWaitTakeFinish 是否阻塞主线程等待下载完毕
     * @throws Exception
     */
    public abstract ArrayList<Future<DownloadResult>> downloadThread(boolean isWaitTakeFinish) throws DownloadException;

    /**
     * 停止下载
     */
    public abstract void stop() throws DownloadException;
}
