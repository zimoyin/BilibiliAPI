package github.zimoyin.bili.video.download.setting;

import github.zimoyin.bili.cookie.Cookie;
import github.zimoyin.bili.utils.net.httpclient.VideoDownloadUtil;
import github.zimoyin.bili.video.download.setting.info.DownloadVideoID;
import github.zimoyin.bili.video.download.setting.info.DownloadVideoInfo;
import github.zimoyin.bili.video.download.setting.info.DownloadVideoInfoList;
import github.zimoyin.bili.video.url.param.ParamBuilder;
import lombok.Getter;

import java.util.function.Consumer;


/**
 * 单视频下载设置
 * 一个设置类对应一个视频（包括番剧、剧集、分p视频）
 */
public class VideoSetting extends DownloadVideoSettingAbs {
    /**
     * 初步构建 ParamBuilder 只要求包含 QN，Fnval 字段
     * 进一步(DownloadVideoInfo)构建 ParamBuilder 要求追加 BVID，CID 字段。此次构建是设置浮动字段
     * 浮动字段 bvid,cid(下发到 DownloadVideoInfo 时根据需要二次修改)
     */
    private final ParamBuilder param;
    /**
     * 初步构建 DownloadVideoID 只要求 ID字段
     * 进一步(DownloadVideoInfoList)构建 DownloadVideoID 要求追加 CID 字段
     */
    private final DownloadVideoID ID;
    @Getter
    private Cookie cookie;
    /**
     * page 列表（存储与解析）
     */
    @Getter
    private DownloadVideoInfoList pages;
    private int page = 1;

    /**
     * 适合对格式，清晰度没有要求的
     */
    public VideoSetting(String ID) {
        this.ID = new DownloadVideoID(ID);
        this.param = new ParamBuilder();
        init();
    }

    /**
     * 适合对格式，清晰度没有要求的
     */
    public VideoSetting(DownloadVideoID ID) {
        this.ID = ID;
        this.param = new ParamBuilder();
        init();
    }

    /**
     * 适用于只下载视频
     * @param param 视频
     */
    public VideoSetting(ParamBuilder param) {
        this.param = param;
        this.ID = new DownloadVideoID(param.getBvid());
        init();
    }

    /**
     * 适用于只下载视频
     * @param param 视频
     * @param cookie cookie
     */
    public VideoSetting(ParamBuilder param, Cookie cookie) {
        this.param = param;
        this.cookie = cookie;
        this.ID = new DownloadVideoID(param.getBvid());
        init();
    }

    /**
     * 推荐使用
     * 对于番剧等适合使用
     * 注意：下载视频的ID依照 ID 所定义的，param 的 id 会被忽略
     */
    public VideoSetting(ParamBuilder param, DownloadVideoID ID) {
        this.param = param;
        this.ID = ID;
        init();
    }

    /**
     * 推荐使用
     * 对于番剧等适合使用
     * 注意：下载视频的ID依照 ID 所定义的，param 的 id 会被忽略
     */
    public VideoSetting(ParamBuilder param, DownloadVideoID ID,Cookie cookie) {
        this.param = param;
        this.cookie = cookie;
        this.ID = ID;
        init();
    }

    private void init() {
        String id = ID.getID();
        if (id == null) throw new NullPointerException("id/ep 为 null");
    }


    /**
     * 懒初始化（进一步构建）
     */
    private void lazyInit() {
        if (this.pages == null)
            this.pages = new DownloadVideoInfoList(ID.getID(), param);
    }

    /**
     * 设置要下载的视频的page（p数）
     */
    public void updateDownloadPage(int page) {
        lazyInit();
        this.page = page;
    }

    public DownloadVideoInfo getPage() {
        lazyInit();
        return pages.getPage(this.page);
    }

    public DownloadVideoInfo getPage(int page) {
        lazyInit();
        return pages.getPage(page);
    }



    public int getSize(){
        return getPage().getPageSize();
    }

    public void forEach(Consumer<? super DownloadVideoInfo> action) {
        pages.getPages().forEach(action);
    }

    public void setCookie(Cookie cookie) {
        lazyInit();
        this.cookie = cookie;
        this.param.append(cookie);
        this.pages.setCookie(this.cookie);
    }

    /**
     * 下载限速：只对单线程下载有效。(不推荐进行限速，因为限速是通过 sleep thread 进行实现的)
     * 为了测试代码而不占用他人带宽而写的’大聪明‘实现
     * @param rateLimit
     */
    @Deprecated
    public void setRatelimit(int rateLimit) {
        VideoDownloadUtil.setRatelimit(rateLimit);
    }
}