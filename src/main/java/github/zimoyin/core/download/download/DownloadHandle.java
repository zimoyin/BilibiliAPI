package github.zimoyin.core.download.download;


/**
 * 该类以前是一个抽象类，注意异常与报错
 * 提供一个进度监听
 */
@Deprecated
public interface DownloadHandle{


    public abstract void handle(DownloadControl.DownloadInfo info) ;
}
