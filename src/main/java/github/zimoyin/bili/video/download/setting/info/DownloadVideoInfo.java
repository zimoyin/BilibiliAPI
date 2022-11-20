package github.zimoyin.bili.video.download.setting.info;

import github.zimoyin.bili.video.url.VideoURLFormat;
import github.zimoyin.bili.video.url.VideoURLPreviewFormatP1080;
import github.zimoyin.bili.video.url.param.Fnval;
import github.zimoyin.bili.video.url.param.ParamBuilder;
import github.zimoyin.bili.video.url.param.QN;
import github.zimoyin.bili.video.url.pojo.Dash;
import github.zimoyin.bili.video.url.pojo.Media;
import github.zimoyin.bili.video.url.pojo.VideoURLJsonRoot;
import lombok.Getter;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

/**
 * 视频当前page信息
 */
@Getter
public class DownloadVideoInfo {
    private final ParamBuilder param;
    /**
     * 视频的BVID与CID 不能为 null
     */
    private final DownloadVideoID ID;
    /**
     * 完整标题：标题+视频当前P名称
     */
    private final String title;
    /**
     * 视频名称
     */
    private final String videoName;
    /**
     * 视频当前P名称
     */
    private final String pageName;
    private final ArrayList<URL> URL = new ArrayList<>();
    private final int pageNum;
    private final int pageSize;
    /**
     * 视频类型
     */
    private Boolean dash = false;
    private String type = "mp4";

    public DownloadVideoInfo(
            ParamBuilder param,
            DownloadVideoID ID,
            String title,
            String videoName,
            String pageName,
            int pageNum,
            int pageSize
    ) {
        this.param = param;
        this.ID = ID;
        this.title = title;
        this.videoName = videoName;
        this.pageName = pageName;
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        init();
    }

    private void init() {
        // TODO Auto-generated method stub
    }

    public ArrayList<URL> getURL() throws IOException {
        if (URL.size() != 0) return URL;
        if (param.getCookie() != null) return getCookieURL();
        if (param.getCookie() == null) return getNotCookieURL();
        return URL;
    }

    /**
     * 如果不存在Cookie
     *
     * @return
     * @throws IOException
     */
    private ArrayList<URL> getNotCookieURL() throws IOException {
        //Dash 模式下禁止获取大于 720P 的视频
        if (param.getQn().getQn() > QN.P720.getQn() && param.getFnval() >= Fnval.Dash.getValue())
            throw new IllegalArgumentException("请设置 Cookie");
        //禁止获取 1080P 以上的视频
        if (param.getQn().getQn() > QN.P1080_cookie.getQn())
            throw new IllegalArgumentException("请设置 Cookie");
        //如果是非Dash模式，且获取1080P视频；访问1080p固定接口
        if (param.getQn() == QN.P1080_cookie && param.getFnval() < Fnval.Dash.getValue()) isP1080();
        else getURL(new VideoURLFormat().getJsonPOJO(param.append(ID.getBV()).append(ID.getCID())).getData());
        return URL;
    }

    private void isP1080() {
        java.net.URL url = new VideoURLPreviewFormatP1080().getURLs(ID.getBV()).get(0);
        URL.add(url);
    }

    /**
     * 如果存在Cookie
     *
     * @return
     * @throws IOException
     */
    private ArrayList<URL> getCookieURL() throws IOException {
        VideoURLJsonRoot.Data data = new VideoURLFormat(param.getCookie()).getJsonPOJO(param.append(ID.getBV()).append(ID.getCID())).getData();
//        System.out.println(new VideoURLFormat().getJsonPOJO(param.append(ID.getBV()).append(ID.getCID())).getUri());
        return getURL(data);
    }

    /**
     * 获取URL
     *
     * @param data
     * @return
     */
    private ArrayList<URL> getURL(VideoURLJsonRoot.Data data) {
        type = data.getFormat();
        //如果 durl 模式就获取第一个URL（不获取备链接）
        if (data.durl != null) URL.add(data.getDurl().get(0).getUrl());
        //如果 dash 模式，就默认返回 ParamBuilder 中描述的清晰度视频（随机返回符合要求的视频链接，编码等不限）
        if (data.dash != null) {
            type = "m4s";
            dash = true;

            Dash dash = data.getDash().get(0);
            int max = 0;
            //视频
            for (Media media : dash.getVideo()) {
                if (media.id > max) max = media.id;
                if (media.id == param.getQn().getQn()) {
                    URL baseUrl = media.getBaseUrl();
                    ArrayList<URL> backupUrl = media.getBackupUrl();
                    if (baseUrl != null) URL.add(baseUrl);
                    else if (backupUrl.size() > 0) URL.add(backupUrl.get(0));
                    else throw new NullPointerException("在给定的URL获取地点下，无可用URL");
                    break;
                }
            }
            if (URL.size() == 0)
                throw new NullPointerException("在给定的视频清晰度下，无可用URL;请检查Cookie，QN字段等；目标清晰度：" + param.getQn().getQn() + " 所返回的最大清晰度: " + max);
            //音频
            Media media = dash.getAudio().get(0);
            java.net.URL baseUrl = media.getBaseUrl();
            ArrayList<URL> backupUrl = media.getBackupUrl();
            if (baseUrl != null) URL.add(baseUrl);
            else if (backupUrl.size() > 0) URL.add(backupUrl.get(0));
            else throw new NullPointerException("在给定的URL获取地点下，无可以URL");
        }
        if (URL.size() == 0 || URL.size() >= 3) throw new IllegalArgumentException("URL 非正常数量");
        return URL;
    }

    @Override
    public String toString() {
        return "DownloadVideoInfo{" +
                "param=" + param +
                ", ID=" + ID +
                ", title='" + title + '\'' +
                ", videoName='" + videoName + '\'' +
                ", pageName='" + pageName + '\'' +
                ", URL=" + URL +
                ", pageNum=" + pageNum +
                ", pageSize=" + pageSize +
                '}';
    }
}
