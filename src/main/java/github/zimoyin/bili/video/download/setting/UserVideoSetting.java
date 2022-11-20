package github.zimoyin.bili.video.download.setting;

import github.zimoyin.bili.cookie.Cookie;
import github.zimoyin.bili.user.pojo.works.UserWorksRootBean;
import github.zimoyin.bili.user.pojo.works.Vlist;
import github.zimoyin.bili.user.up.UserWorks;
import github.zimoyin.bili.video.download.setting.info.DownloadVideoInfo;
import github.zimoyin.bili.video.download.setting.info.DownloadVideoInfoList;
import github.zimoyin.bili.video.url.param.ParamBuilder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;

@Getter
public class UserVideoSetting extends DownloadVideoSettingAbs {
    @Setter
    private Cookie Cookie;
    private final ParamBuilder Param;
    private final long Mid;
    private final ArrayList<String> pagesList = new ArrayList<String>();
    private final HashMap<String, Vlist> pages = new HashMap<>();
    private final HashMap<String, DownloadVideoInfoList> pagesCache = new HashMap<>();
    private int Page;

    /**
     * @param mid   用户 mid
     */
    public UserVideoSetting(long mid) {
        Param = new ParamBuilder();
        Mid = mid;
        try {
            init(mid);
        } catch (Exception e) {
            throw new RuntimeException("UserVideoSetting 初始化失败", e);
        }
    }

    /**
     * @param mid   用户 mid
     * @param param 视频参数
     */
    public UserVideoSetting(long mid, ParamBuilder param) {
        Param = param;
        Mid = mid;
        try {
            init(mid);
        } catch (Exception e) {
            throw new RuntimeException("UserVideoSetting 初始化失败", e);
        }
    }

    /**
     * @param mid    用户 mid
     * @param param  视频参数
     * @param cookie cookie
     */
    public UserVideoSetting(long mid, ParamBuilder param, Cookie cookie) {
        this.Cookie = cookie;
        this.Param = param;
        this.Mid = mid;
        try {
            init(mid);
        } catch (Exception e) {
            throw new RuntimeException("UserVideoSetting 初始化失败", e);
        }
    }

    /**
     * 获取UP所有视频信息
     *
     * @param mid
     */
    private void init(long mid) throws IOException, NoSuchAlgorithmException, KeyStoreException, URISyntaxException, KeyManagementException {
        UserWorks works = new UserWorks();
        UserWorksRootBean pojo = works.userWorksPojo(mid, 50, 1);
        int count = pojo.getData().getPage().getCount();
        List<Vlist> list = pojo.getData().getList().getVlist();

        for (int i = 2; i <= count / 50 + 1; i++) {
            for (Vlist vlist : list) {
                pages.put(vlist.getBvid(), vlist);
                pagesList.add(vlist.getBvid());
            }
            list.clear();

            UserWorksRootBean bean = works.userWorksPojo(mid, 50, i);
            list = bean.getData().getList().getVlist();
        }
    }

    private void lazyInit(int page) {
        String bv = pagesList.get(page);
        DownloadVideoInfoList info = pagesCache.get(bv);

        if (info == null) {
            info = new DownloadVideoInfoList(bv, getParam());
            pagesCache.put(bv, info);
        }
    }

    @Override
    public void updateDownloadPage(int page) {
        this.Page = page;
    }

    @Deprecated
    @Override
    public DownloadVideoInfo getPage() {
        return getPages().getPage(1);
    }

    @Deprecated
    @Override
    public DownloadVideoInfo getPage(int page) {
        return getPages(page).getPage(1);
    }

    /**
     * 获取第n个视频（列表）
     * @return
     */
    public DownloadVideoInfoList getPages() {
        lazyInit(Page - 1);
        return pagesCache.get(pagesList.get(Page - 1));
    }

    /**
     * 获取第n个视频（列表）
     * @param page
     * @return
     */
    public DownloadVideoInfoList getPages(int page) {
        lazyInit(page - 1);
        return pagesCache.get(pagesList.get(page - 1));
    }

    @Override
    public int getSize() {
        return pagesList.size();
    }

    @Deprecated
    @Override
    public void forEach(Consumer<? super DownloadVideoInfo> action) {
        throw new IllegalArgumentException("forEach 未实现");
    }
}
