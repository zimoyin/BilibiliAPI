package github.zimoyin.core.video.info;


import github.zimoyin.core.exception.CodeException;
import github.zimoyin.core.exception.URLFormatException;
import github.zimoyin.core.video.info.pojo.info.WEBVideoINFOJsonRootBean;
import github.zimoyin.core.video.info.pojo.info.data.Desc_v2;
import github.zimoyin.core.video.info.pojo.info.data.Owner;
import github.zimoyin.core.video.info.pojo.info.data.Pages;
import github.zimoyin.core.video.info.pojo.info.data.staff.StaffRoot;
import github.zimoyin.core.video.url.VideoURLPreviewFormatP360;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

/**
 * 视频信息的摘要
 */
public class VideoINFOSummary {
    private WEBVideoINFOJsonRootBean info;

    public VideoINFOSummary(WEBVideoINFOJsonRootBean info) {
        this.info = info;
    }

    /**
     * 视频UP
     *
     * @return
     */
    public Owner getAuthor() {
        return info.getData().getOwner();
    }

    /**
     * 视频简介
     *
     * @return
     */
    public String getVideoDesc() {
        return info.getData().getDesc();
    }

    /**
     * 视频新版简介
     *
     * @return
     */
    public ArrayList<String> getVideoDesc_v2() {
        ArrayList<String> desc = new ArrayList<>();
        for (Desc_v2 desc_v2 : info.getData().getDesc_v2()) {
            desc.add(desc_v2.getRaw_text());
        }
        return desc;
    }

    /**
     * 视频封面
     *
     * @return
     */
    public URL getVideoCover() {
        return info.getData().getPic();
    }

    /**
     * 视频标题
     *
     * @return
     */
    public String getTitle() {
        return info.getData().getTitle();
    }


    /**
     * 视频BV
     *
     * @return
     */
    public String getVideBV() {
        return info.getData().getBvid();
    }

    /**
     * 视频发布时间戳
     *
     * @return
     */
    public long getVideoPubDate() {
        return info.getData().getPubdate();
    }

    /**
     * 视频总时长（所有分P），单位为秒
     *
     * @return
     */
    public double getDuration() {
        return info.getData().getDuration();
    }

    /**
     * 获取视频分p
     *
     * @return
     */
    public List<Pages> getPages() {
        return info.getData().getPages();
    }

    /**
     * 获取所有合作作者
     *
     * @return
     */
    public ArrayList<StaffRoot> getStaff() {
        return info.getData().getStaff();
    }


    /**
     * 获取预览视频(360P)得下载链接，注意视频可能是分段的，要把所有链接的视频全部下载
     *
     * @return
     */
    public ArrayList<URL> getVideoURLPreview() throws URLFormatException, IOException, NoSuchAlgorithmException, KeyStoreException, URISyntaxException, KeyManagementException, CodeException {
        VideoURLPreviewFormatP360 webVideoURLPreviewFormatP360 = new VideoURLPreviewFormatP360();
        ArrayList<URL> urLs = webVideoURLPreviewFormatP360.getURLs(info.getData().getBvid());
        return urLs;
    }

    @Override
    public String toString() {
        ArrayList<URL> videoURLPreview = null;
        try {
            videoURLPreview = getVideoURLPreview();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        ArrayList<String> aa = new ArrayList<>();
        if (getStaff() != null) {
            for (StaffRoot staff : getStaff()) {
                aa.add(staff.getName());
            }
        }
        return "WebVideoINFOSummary{" +
                "\n下载链接(360P)：" + videoURLPreview +
                "\n标题：" + getTitle() +
                "\n封面：" + getVideoCover() +
                "\nBV: " + getVideBV() +
                "\n时长: " + String.format("%.4f", getDuration() / 60) + " min" +
                "\nP数: " + getPages().size() +
                "\n作者：" + getAuthor() +
                "\n合作作者列表: " + aa +
                "\n}";
    }
}
