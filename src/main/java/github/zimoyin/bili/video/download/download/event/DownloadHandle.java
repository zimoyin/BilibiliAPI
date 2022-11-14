package github.zimoyin.bili.video.download.download.event;


/**
 * 该类以前是一个抽象类，注意异常与报错
 * 提供一个进度监听
 */
public interface DownloadHandle{
    /**
     * 是否停止这个监听
     */
    boolean channel = false;
    public abstract void handle(DownloadingINFO.Info info) ;
}
