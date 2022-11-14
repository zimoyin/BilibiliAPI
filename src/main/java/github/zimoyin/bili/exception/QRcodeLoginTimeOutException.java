package github.zimoyin.bili.exception;

import java.io.PrintStream;
import java.io.PrintWriter;


/**
 * 在规定时间内未能登录成功就抛出此异常
 */
public class QRcodeLoginTimeOutException extends LoginException {
    public QRcodeLoginTimeOutException() {
        super("在 180s 内未登录成功导致登录能凭证过期,请重新申请登录二维码");
    }

    public QRcodeLoginTimeOutException(String message) {
        super(message);
    }

    public QRcodeLoginTimeOutException(String message, Throwable cause) {
        super(message, cause);
    }

    public QRcodeLoginTimeOutException(Throwable cause) {
        super(cause);
    }

    protected QRcodeLoginTimeOutException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }

    @Override
    public String getLocalizedMessage() {
        return super.getLocalizedMessage();
    }

    @Override
    public synchronized Throwable getCause() {
        return super.getCause();
    }

    @Override
    public synchronized Throwable initCause(Throwable cause) {
        return super.initCause(cause);
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public void printStackTrace() {
        super.printStackTrace();
    }

    @Override
    public void printStackTrace(PrintStream s) {
        super.printStackTrace(s);
    }

    @Override
    public void printStackTrace(PrintWriter s) {
        super.printStackTrace(s);
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return super.fillInStackTrace();
    }

    @Override
    public StackTraceElement[] getStackTrace() {
        return super.getStackTrace();
    }

    @Override
    public void setStackTrace(StackTraceElement[] stackTrace) {
        super.setStackTrace(stackTrace);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
    }
}
