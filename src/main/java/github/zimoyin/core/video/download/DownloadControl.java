package github.zimoyin.core.video.download;

import lombok.Data;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 下载控制器
 * 通过了停止下载、监听下载进度功能(事件并发执行)
 */
@Data
public class DownloadControl {
    /**
     * 需要下载的文件
     */
    private volatile int finalThreadCount;

    /**
     * 下载完成的线程数
     */
    private volatile int finishCount;

    /**
     * 文件下载线程数，事件处理线程数是这个的一半+1
     */
    private volatile int threadCount;
    /**
     * 停止下载
     */
    private volatile boolean stop = false;
    /**
     * 文件总长度
     */
    private volatile long fileSize = 0;
    /**
     * 监听集合
     */
//    private volatile ArrayList<DownloadHandle<DownloadInfo, Object>> handles = new ArrayList<>();
    private volatile ArrayList<DownloadHandle> handles = new ArrayList<>();
    /**
     * 当前文件下载的字节数
     */
    private volatile long downloadSize = 0;

    /**
     * 类初始化的时间戳
     */
    private final long initTime = System.currentTimeMillis();

    /**
     * 最近事件触发时间
     */
    private volatile long eventTime = System.currentTimeMillis();

    /**
     * 创建线程池,用来处理事件执行
     * 线程池的销毁由定时器执行
     */
    private ExecutorService executorService;

    /**
     * 下载文件的线程池，这里不提供创建只负责在文件下载完毕后关闭
     */
    private ExecutorService downloadExecutorService;


    public DownloadControl(int threadCount) {
        this.threadCount = threadCount;
        executorService = Executors.newFixedThreadPool(threadCount/2+1);
    }

    /**
     * 触发实际
     *
     * @param len                线程当前下载的字节大小（当前从服务器获取到的包的大小）
     * @param threadDownloadSize 线程累计下载大小
     * @param threadSize         线程需要下载大小
     */
    public void setLength(long len, long threadDownloadSize, long threadSize) {
        this.downloadSize += len;
        eventTime = System.currentTimeMillis();
        executorService.execute(() -> send(fileSize, downloadSize, Thread.currentThread().getName(), threadSize, threadDownloadSize));
    }

    /**
     * 触发监听
     *
     * @param fileSize           文件总大小
     * @param downloadSize       下载了文件大小
     * @param threadName         线程名称
     * @param threadSize         线程下载总大小
     * @param threadDownloadSize 线程当前下载大小
     */
    private void send(long fileSize, long downloadSize, String threadName, long threadSize, long threadDownloadSize) {
        for (DownloadHandle handle : handles) {
            handle.handle(new DownloadInfo(
                    fileSize,
                    downloadSize,
                    Thread.currentThread().getName(),
                    threadDownloadSize,
                    threadSize
            ));
        }
    }

    /**
     * 监听文件下载
     */
    public void setListenDownloadSize(DownloadHandle handle) {
        handles.add(handle);
    }


    public void setFileSize(long fileSize) {
        this.fileSize += fileSize;
    }


    /**
     * 销毁线程池
     */
    public void close() {
        this.setStop(true);
        //关闭下载线程池
        if (downloadExecutorService != null) downloadExecutorService.shutdown();
        //关闭事件线程池
        executorService.shutdown();
    }

    /**
     * 下载完成
     */
    public void finish(){
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                finishCount++;
                if (finishCount >= threadCount){
                    close();
                }
            }
        });
    }

    /**
     * 清理类
     */
    public void clear(){
        this.finishCount=0;
        this.downloadSize = 0;
        this.executorService = Executors.newFixedThreadPool(threadCount/2+1);
    }
    @Data
    public class DownloadInfo {
        /**
         * 文件总长度
         */
        private volatile long fileSize;
        /**
         * 下载的长度
         */
        private volatile long downloadSize;

        /**
         * 线程名称
         */
        private volatile String threadName;
        /**
         * 线程需要下载的总量
         */
        private volatile long threadSize;
        /**
         * 线程下载总量
         */
        private volatile long threadDownloadSize;

        public DownloadInfo(long fileSize, long downloadSize, String threadName, long threadSize, long threadDownloadSize) {
            this.fileSize = fileSize;
            this.downloadSize = downloadSize;
            this.threadName = threadName;
            this.threadSize = threadSize;
            this.threadDownloadSize = threadDownloadSize;
        }
    }
}