package github.zimoyin.core.video.download.download.event;

import github.zimoyin.core.video.download.setting.info.DownloadVideoInfo;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
public class DownloadingINFO {
    //视频地址
    private final String FilePath;
    //任务数（dash下为2）
    private final int TaskFinalCount;
    //当前是第几个任务
    @Setter
    private volatile int TaskNumber;
    //下载该文件的总线程数
    private final int ThreadFinalCount;
    //完成线程的线程数
    private volatile int ThreadFinish;
    //文件总大小
    private final long FileFinalSize;
    //文件已经下载的大小
    private volatile long FileSize;
    //是否被暂停
    private volatile boolean Stop = false;
    //监听
    private final ArrayList<DownloadHandle> handles = new ArrayList<>();
    //当前分p
    private final DownloadVideoInfo Page;
    public DownloadingINFO(DownloadVideoInfo page, int threadFinalCount, int taskFinalCount, long fileFinalSize, String filePath) {
        Page = page;
        FilePath = filePath;
        ThreadFinalCount = threadFinalCount;
        TaskFinalCount = taskFinalCount;
        FileFinalSize = fileFinalSize;
    }

    public synchronized void stop() {
        this.Stop = true;
        broadcast();
    }

    public synchronized void addFileSize(long fileSize) {
        this.FileSize += fileSize;
        broadcast();
    }

    public synchronized void threadFinish() {
        this.ThreadFinish++;
        broadcast();
    }

    public synchronized void addHandle(DownloadHandle handle) {
        handles.add(handle);
    }

    /**
     * 是否下载完毕
     *
     * @return
     */
    public synchronized boolean isFinished() {
        return ThreadFinish == ThreadFinalCount;
    }

    public synchronized void addAllHandle(ArrayList<DownloadHandle> handles) {
        this.handles.addAll(handles);
    }

    public synchronized void broadcast() {
        for (DownloadHandle handle : handles) {
            if (handle.channel) continue;
            handle.handle(new Info(
                    getPage(),
                    getFilePath(),
                    getTaskFinalCount(),
                    getTaskNumber(),
                    getThreadFinalCount(),
                    getThreadFinish(),
                    getFileFinalSize(),
                    getFileSize(),
                    isStop()
            ));
        }
    }

    /**
     * read-only
     */
    @Getter
    public static class Info {
        private final String FilePath;
        private final int TaskFinalCount;
        private final int TaskNumber;
        private final int ThreadFinalCount;
        private final int ThreadFinish;
        private final long FileFinalSize;
        private final long FileSize;
        private final boolean Stop;
        private final DownloadVideoInfo Page;

        public Info(DownloadVideoInfo page, String filePath, int taskFinalCount, int taskNumber, int threadFinalCount, int threadFinish, long fileFinalSize, long fileSize, boolean stop) {
            Page = page;
            FilePath = filePath;
            TaskFinalCount = taskFinalCount;
            TaskNumber = taskNumber;
            ThreadFinalCount = threadFinalCount;
            ThreadFinish = threadFinish;
            FileFinalSize = fileFinalSize;
            FileSize = fileSize;
            Stop = stop;
        }

        public String getThreadName(){
            return Thread.currentThread().getName();
        }

        @Override
        public String toString() {
            return "Info{" +
                    "TaskFinalCount=" + TaskFinalCount +
                    ", TaskNumber=" + TaskNumber +
                    ", ThreadFinalCount=" + ThreadFinalCount +
                    ", ThreadFinish=" + ThreadFinish +
                    ", FileFinalSize=" + FileFinalSize +
                    ", FileSize=" + FileSize +
                    '}';
        }
    }
}
