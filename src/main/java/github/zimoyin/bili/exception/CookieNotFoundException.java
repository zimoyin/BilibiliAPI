package github.zimoyin.bili.exception;

public class CookieNotFoundException extends Exception{
    public CookieNotFoundException() {
        super("未能成功加载到Cookie！");
    }

    public CookieNotFoundException(String message) {
        super(message);
    }

    public CookieNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public CookieNotFoundException(Throwable cause) {
        super(cause);
    }

    public CookieNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
