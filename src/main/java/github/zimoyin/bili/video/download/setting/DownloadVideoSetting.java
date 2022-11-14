package github.zimoyin.bili.video.download.setting;

import github.zimoyin.bili.cookie.Cookie;
import github.zimoyin.bili.video.download.setting.info.DownloadVideoID;
import github.zimoyin.bili.video.download.setting.info.DownloadVideoInfo;
import github.zimoyin.bili.video.download.setting.info.DownloadVideoInfoList;
import github.zimoyin.bili.video.url.param.ParamBuilder;
import lombok.Getter;

import java.util.function.Consumer;

public class DownloadVideoSetting extends DownloadVideoSettingAbs {
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

    public DownloadVideoSetting(String ID) {
        this.ID = new DownloadVideoID(ID);
        this.param = new ParamBuilder();
        init();
    }

    public DownloadVideoSetting(DownloadVideoID ID) {
        this.ID = ID;
        this.param = new ParamBuilder();
        init();
    }

    public DownloadVideoSetting(ParamBuilder param) {
        this.param = param;
        this.ID = new DownloadVideoID(param.getBvid());
        init();
    }

    public DownloadVideoSetting(ParamBuilder param, Cookie cookie) {
        this.param = param;
        this.cookie = cookie;
        this.ID = new DownloadVideoID(param.getBvid());
        init();
    }

    /**
     * 推荐使用
     */
    public DownloadVideoSetting(ParamBuilder param, DownloadVideoID ID) {
        this.param = param;
        this.ID = ID;
        init();
    }

    /**
     * 推荐使用
     */
    public DownloadVideoSetting(ParamBuilder param, Cookie cookie, DownloadVideoID ID) {
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
}