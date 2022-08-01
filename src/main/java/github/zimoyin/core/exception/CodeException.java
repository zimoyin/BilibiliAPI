package github.zimoyin.core.exception;

public class CodeException extends Exception{
    public CodeException() {
        super("服务器响应码不符合程序预期，疑似服务器返回了预期之外的信息");
    }

    public CodeException(int code) {
        super("服务器响应码不符合程序预期，疑似服务器返回了预期之外的信息 code:"+code);
    }

    public CodeException(String message) {
        super(message);
    }

    public CodeException(String message, Throwable cause) {
        super(message, cause);
    }
}
