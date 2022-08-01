package github.zimoyin.core.video;


import github.zimoyin.core.Incompleteness;
import github.zimoyin.core.barrage.Barrage;
import github.zimoyin.core.cookie.Cookie;
import github.zimoyin.core.cookie.GlobalCookie;
import github.zimoyin.core.exception.CodeException;
import github.zimoyin.core.exception.CookieNotFoundException;
import github.zimoyin.core.exception.URLFormatException;
import github.zimoyin.core.video.download.VideoDownloadSetting;
import github.zimoyin.core.video.info.VideoInfo;
import github.zimoyin.core.video.info.pojo.info.WEBVideoINFOJsonRootBean;
import github.zimoyin.core.video.download.VideoDownload;
import github.zimoyin.core.video.url.VideoURLFormat;
import github.zimoyin.core.video.url.VideoURLPreviewFormatP360;
import github.zimoyin.core.video.url.data.Fnval;
import github.zimoyin.core.video.url.data.ID;
import github.zimoyin.core.video.url.data.QN;
import github.zimoyin.core.video.url.pojo.VideoURLJsonRoot;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * 视频操作类
 */
@Deprecated
@Incompleteness
public class Video {

    private Cookie cookie;
    private HashMap<String, String> header = new HashMap<>();
    private String bv;

    private WEBVideoINFOJsonRootBean videoInfo;

    public Video() {
        try {
            cookie = GlobalCookie.getInstance().getCookie();
        } catch (CookieNotFoundException e) {
            throw new RuntimeException("无法获取到全局Cookie，请手动指定Cookie",e);
        }
    }

    public Video(String bv) {
        this.bv = bv;
    }

    public Video(Cookie cookie) {
        this.cookie = cookie;
        this.cookie.setCookieToHeader(header);
    }

    public Video(Cookie cookie, String bv) {
        this.cookie = cookie;
        this.cookie.setCookieToHeader(header);
        this.bv = bv;
    }


    public Cookie getCookie() {
        return cookie;
    }

    public void setCookie(Cookie cookie) {
        this.cookie = cookie;
        header.clear();
        this.cookie.setCookieToHeader(header);
    }

    public String getBv() {
        return bv;
    }

    public void setBv(String bv) {
        this.bv = bv;
    }


    /**
     * 获取视频信息
     * @return
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws KeyStoreException
     * @throws URISyntaxException
     * @throws KeyManagementException
     */
    public WEBVideoINFOJsonRootBean getVideoINFO() throws IOException, NoSuchAlgorithmException, KeyStoreException, URISyntaxException, KeyManagementException {
        videoInfo = new VideoInfo().getInfo(getBv());
        return videoInfo;
    }


    /**
     * 获取预览视频(360P)得下载链接，注意视频可能是分段的，要把所有链接的视频全部下载(不能经过下载器下载)
     * @return
     */
    public ArrayList<URL> getVideoPreviewURLs() throws URLFormatException, IOException, NoSuchAlgorithmException, KeyStoreException, URISyntaxException, KeyManagementException, CodeException {
        VideoURLPreviewFormatP360 webVideoURLPreviewFormatP360 = new VideoURLPreviewFormatP360();
        ArrayList<URL> urLs = webVideoURLPreviewFormatP360.getURLs(getBv());
        return urLs;
    }


    /**
     * 获取 视频流（源）信息 构造器
     * @return
     */
    public VideoURLFormat getVideoURLInfo(){
        return new VideoURLFormat(getCookie());
    }

    /**
     * 获取 视频流（源）信息（视频url有防盗链，需要用下载器下载）
     * @return
     */
    public VideoURLJsonRoot getVideoURLJson(int qn,int fnval) throws CodeException, URLFormatException, IOException, NoSuchAlgorithmException, KeyStoreException, URISyntaxException, KeyManagementException {
        return new VideoURLFormat(getCookie()).getJsonPOJO(getBv(),qn,fnval);
    }


    /**
     * 获取 视频流（源）信息（视频url有防盗链，需要用下载器下载）
     * @return
     */
    public VideoURLJsonRoot getVideoURLJson(QN qn, Fnval fnval) throws CodeException, URLFormatException, IOException, NoSuchAlgorithmException, KeyStoreException, URISyntaxException, KeyManagementException {
        return new VideoURLFormat(getCookie()).getJsonPOJO(new ID(getBv()),qn,fnval);
    }

    /**
     * 获取 视频流（源）下载器，注意需要对下载器进行一些设置才能使用（下载路径等）
     * @return
     */
    public VideoDownload getVideoDownload(int qn, int fnval) throws CodeException, URLFormatException, IOException, NoSuchAlgorithmException, KeyStoreException, URISyntaxException, KeyManagementException {
        return getVideoDownload(new QN(qn),new Fnval(fnval));
    }


    /**
     * 获取  视频流（源）下载器，注意需要对下载器进行一些设置才能使用（下载路径等）
     * @return
     */
    public VideoDownload getVideoDownload(QN qn, Fnval fnval) throws CodeException, URLFormatException, IOException, NoSuchAlgorithmException, KeyStoreException, URISyntaxException, KeyManagementException {
        VideoURLJsonRoot jsonPOJO = new VideoURLFormat(getCookie()).getJsonPOJO(new ID(getBv()), qn, fnval);
        VideoDownload webVideoDownload = jsonPOJO.getVideoDownload();
        VideoDownloadSetting setting = webVideoDownload.getSetting();
        setting.setCookie(cookie);
        setting.build();

        return webVideoDownload;
    }


    /**
     * 获取弹幕操作类实例
     * @return
     */
    public Barrage getBarrage(){
        return new Barrage(getCookie(),getBv());
    }

}
