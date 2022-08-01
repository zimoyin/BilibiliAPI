package github.zimoyin.core.exception;

/**
 * 下载异常
 */
public class DownloadException extends RuntimeException{
    public DownloadException() {
        super("无法下载网络资源");
    }

    public DownloadException(String message) {
        super(message);
    }

    public DownloadException(String message, Throwable cause) {
        super(message, cause);
    }

    public DownloadException(Throwable cause) {
        super(cause);
    }

    public DownloadException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
