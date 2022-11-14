package github.zimoyin.bili.video.download.download;

import lombok.Data;

/**
 * 多线程下载结果类
 */
@Data
public class DownloadResult {
    private String bv;
    /**
     * 文件路径
     */
    private String filePath;
    /**
     * 是否下载完成
     */
    private boolean isDownloadFinish;
    /**
     * 下载中爆出的异常
     */
    private Exception exception;
    /**
     * 下一次开始的位置
     */
    private long pointer;

    /**
     * 下载开始的位置
     */
    private long start0;
    /**
     * 预计要下载到的位置
     */
    private long end;

    /**
     * 负责下载的线程名称
     */
    private String threadName;
}
