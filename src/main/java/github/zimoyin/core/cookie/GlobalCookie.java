package github.zimoyin.core.cookie;

import com.alibaba.fastjson.JSONObject;
import github.zimoyin.core.exception.CookieNotFoundException;
import github.zimoyin.core.login.Login;
import github.zimoyin.core.utils.IO;
import github.zimoyin.core.utils.net.httpclient.HttpClientUtils;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * 全局Cookie会在 HttpClientUtils.class 中使用，如果不想请求携带全局Cookie，请把 isGlobalCookie 设置为false<br>
 * 全局cookie，自动扫描指定目录（./cache/cookie  可以修改路径）下的cookie，从而自动让程序使用该cookie<br>
 * 程序如何自动使用cookie的：类在初始化的时候会去看用户有没有去指定获取哪个cookie，如果没有就去获取全局cookie<br>
 * 程序获取不到cookie会这么办：程序会停止运行并抛出异常，程序不会自动去调用Login等方法提升用户登录<br>
 * 程序运行时我没自己初始化全局Cookie，但是他自己打印了异常：这是全局cookie在API系统内被初始化，并打印了异常<br>
 * 为什么异常不抛出而是自己打印呢：只要你事先初始化了全局Cookie就不会有这回事<br>
 * <p>
 * //打印日志而不是向上抛出，因为异常不需要处理，并且也不推荐全局Cookie在API系统内初始化，而是在一开始的main中初始化。<br>
 * //在系统内初始化说明就是不打算使用全局Cookie，在系统里抛出异常无可厚非
 * </p>
 */
public class GlobalCookie extends Cookie {
    private static final long serialVersionUID = 2168152194164783950L;

    private static Cookie cookie;

    /**
     * cookie保存路径
     */
    private static String cookiePath = Login.cookiePath;
    /**
     * 单例
     */
    private static GlobalCookie instance;

    /**
     * 优先加载JSON格式的cookie
     *
     * @throws IOException
     * @throws ClassNotFoundException
     */
    private GlobalCookie() throws CookieNotFoundException {
        //如果发生异常这里只打印读取JSON时发生的异常
        CookieNotFoundException exception = new CookieNotFoundException();
        boolean isException = false;
        try {
            ArrayList<File> json = IO.getFileType(cookiePath, "json");
            if (json != null && json.size() != 0) {
                File file = json.get(0);

                cookie = readCookie(file.getCanonicalPath());

                putAll(cookie);
            }
        } catch (Exception e) {
            isException = true;
            exception.addSuppressed(e);
        }

        if (cookie != null) return;
        try {
            ArrayList<File> cookieFiles = IO.getFileType(cookiePath, "cookie");
            if (cookieFiles != null && cookieFiles.size() != 0) {
                File file = cookieFiles.get(0);
                cookie = readSerializable(file.getCanonicalPath());
                putAll(cookie);
            }
        } catch (IOException | ClassNotFoundException e) {
            isException = true;
            exception.addSuppressed(e);
        }

        if (cookie == null && isException) {
            throw exception;
        }
    }

    public static synchronized GlobalCookie getInstance() throws CookieNotFoundException {
        if (instance == null) {
            instance = new GlobalCookie();
        }
        return instance;
    }


    /**
     * 设置在请求不携带cookie的时候是否使用全局Cookie，默认为TRUE
     */
    public static void setIsGlobalCookie(boolean isGlobalCookie){
        HttpClientUtils.isGlobalCookie = isGlobalCookie;
    }
    /**
     * 返回在请求不携带cookie的时候是否使用全局Cookie，默认为TRUE
     */
    public static boolean getIsGlobalCookie(){
        return HttpClientUtils.isGlobalCookie;
    }

    /**
     * 获取cookie 对象序列化 文件
     *
     * @return
     */
    private ArrayList<File> getCookieFiles() {
        return null;
    }


    /**
     * 返回cookie的所作路径，路径为一个文件夹
     * @return
     */
    public static String getCookiePath() {
        return cookiePath;
    }

    /**
     * 设置cookie的所作路径，路径为一个文件夹
     * @param cookiePath
     */
    public static void setCookiePath(String cookiePath) {
        GlobalCookie.cookiePath = cookiePath;
    }


    public Cookie getCookie() {
        return cookie;
    }

    public void setCookie(Cookie cookie) {
        GlobalCookie.cookie = cookie;
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public JSONObject toJSONString() {
        return super.toJSONString();
    }

    @Override
    public void writeCookieToJson(String filePath) throws Exception {
        super.writeCookieToJson(filePath);
    }

    @Override
    public void serializable(String filePath) throws IOException {
        super.serializable(filePath);
    }

    @Override
    public void setCookieToHeader(HashMap<String, String> header) {
        super.setCookieToHeader(header);
    }
}
