package github.zimoyin.core.video.download;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.LogManager;


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
    private volatile String filePath = null;
    private volatile String fileName = null;
    /**
     * 需要下载的文件的实际线程数
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

    private static boolean warn = false;
//    private Logger logger = LoggerFactory.getLogger(DownloadControl.class);
    private final org.apache.logging.log4j.Logger logger = LogManager.getLogger(DownloadControl.class);
    public DownloadControl(int threadCount) {
        this.threadCount = threadCount;
        executorService = Executors.newFixedThreadPool(threadCount / 2 + 1);
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
        if (downloadSize>fileSize && !warn) {
            warn= true;
            logger.warn("当前已下载的字节数大于控制器中描述的文件字节长度，这说明获取文件长度的方法有缺陷，但是不影响下载");
        }
        for (DownloadHandle handle : handles) {
            DownloadInfo info = new DownloadInfo(
                    threadCount,
                    finalThreadCount,
                    finishCount,

                    fileSize,
                    downloadSize,

                    Thread.currentThread().getName(),

                    threadDownloadSize,
                    threadSize
            );
            info.setThreadFinishedCount(finishCount);
            info.setFilePath(filePath);
            info.setFileName(fileName);
            handle.handle(info);
        }
    }

    /**
     * 监听文件下载
     */
    public void setListenDownloadSize(DownloadHandle handle) {
        handles.add(handle);
    }


    public void setFileSize(long fileSize) {
        long size = this.fileSize;
        this.fileSize = this.fileSize + fileSize;
//        logger.debug("控制器描述当前文件长度：{}，追加文件长度:{},最终文件长度:{}",size,fileSize,this.fileSize);
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
        //发出事件
        for (DownloadHandle handle : handles) {
            DownloadInfo info = new DownloadInfo(
                    threadCount,
                    finalThreadCount,
                    finishCount,
                    fileSize,
                    downloadSize,
                    Thread.currentThread().getName(),
                    0,
                    0
            );
            info.setThreadFinishedCount(finishCount);
            info.setFilePath(filePath);
            info.setFileName(fileName);
            handle.handle(info);
        }
    }

    /**
     * 下载完成: 当所有线程完成任务时则完成
     */
    public void finish() {
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                finishCount++;
                if (finishCount >= threadCount) {
                    close();
                }
            }
        });
    }

    /**
     * 清理类
     */
    public void clear() {
        this.finishCount = 0;
        this.downloadSize = 0;
        this.executorService = Executors.newFixedThreadPool(threadCount / 2 + 1);
    }

    @Data
    @NoArgsConstructor
    public class DownloadInfo {
        private volatile String filePath = null;
        private volatile String fileName = null;
        /**
         * 真实的线程数(核心线程)
         */
        private volatile int threadCount;
        /**
         * 需要利用的线程数，需要利用线程的次数（线程的任务数）。
         * 这是个任务数量总计，当所有的任务完成后（threadFinishedCount） 就会和他保持一致
         * 因此当使用通过下载字节数无法判断是否下载成功的时候请通过他来完成是否下载完成的判断
         */
        private volatile int threadDownloadCount;

        /**
         * 完成下载任务的线程数（线程的任务数）
         * 这是个完成任务数量总计，当所有的任务完成后（threadFinishedCount） 就会和他（threadDownloadCount）保持一致
         * 因此当使用通过下载字节数无法判断是否下载成功的时候请通过他来完成是否下载完成的判断
         */
        private volatile int threadFinishedCount;


        /**
         * 文件总长度
         */
        private volatile long fileSize;
        /**
         * 已下载文件的长度（byte）
         */
        private volatile long downloadSize;

        /**
         * 当前执行统计的线程名称
         */
        private volatile String threadName;
        /**
         * 线程需要下载文件的总字节数
         */
        private volatile long threadSize;
        /**
         * 线程已经下载的总字节数
         */
        private volatile long threadDownloadSize;

        public DownloadInfo(int threadCount, int threadFinishedCount,int threadDownloadCount,
                            long fileSize, long downloadSize,
                            String threadName,
                            long threadSize, long threadDownloadSize) {
            this.threadCount = threadCount;
            this.threadDownloadCount = threadDownloadCount;
            this.threadFinishedCount = threadFinishedCount;

            this.fileSize = fileSize;
            this.downloadSize = downloadSize;

            this.threadName = threadName;

            this.threadSize = threadSize;
            this.threadDownloadSize = threadDownloadSize;
        }


        /**
         * 判断是否下载完成
         * @return
         */
        public boolean isFinished() {
            if (this.threadFinishedCount >= this.threadCount) return true;
            return false;
        }


        /**
         * 获取下载进度 这是个下载比率 为 1 (100%) 则下载完成
         */
        public double downloadProgress(){
            double sizeProgress = (double) downloadSize / fileSize;
            double threadProgress = (double) threadFinishedCount / threadDownloadCount;
            if (threadProgress >= 1) return 1;
            return sizeProgress;
        }
    }
}