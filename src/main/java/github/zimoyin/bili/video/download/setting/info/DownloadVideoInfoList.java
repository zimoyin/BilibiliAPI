package github.zimoyin.bili.video.download.setting.info;

import github.zimoyin.bili.cookie.Cookie;
import github.zimoyin.bili.fanju.info.SeriesINFO;
import github.zimoyin.bili.fanju.pojo.info.seriesI.Episodes;
import github.zimoyin.bili.fanju.pojo.info.seriesI.SeriesJsonRootBean;
import github.zimoyin.bili.video.url.param.ParamBuilder;
import github.zimoyin.bili.video.info.VideoInfo;
import github.zimoyin.bili.video.info.pojo.info.WEBVideoINFOJsonRootBean;
import github.zimoyin.bili.video.info.pojo.info.data.Pages;
import lombok.Data;
import org.apache.http.HttpException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 视频列表信息
 */
@Data
public class DownloadVideoInfoList {
    private ArrayList<DownloadVideoInfo> Pages = new ArrayList<DownloadVideoInfo>();
    private DownloadVideoID ID;
    /**
     * 进一步构建 ParamBuilder 要求追加 BVID，CID 字段
     */
    private final ParamBuilder Param;

    @Deprecated
    public DownloadVideoInfoList(String id) {
        this.ID = new DownloadVideoID(id);
        this.Param = new ParamBuilder(ID.getBV());
        try {
            init();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public DownloadVideoInfoList(String id, ParamBuilder param) {
        this.ID = new DownloadVideoID(id);
        this.Param = param;
        try {
            init();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void init() throws HttpException, IOException {
        if (ID.isEpisode()) {
            getEpisodes();
        } else if (ID.isVideo()) {
            getVideos();
        }
    }

    private void getEpisodes() throws HttpException {
        SeriesJsonRootBean.Result result = new SeriesINFO().getPojo(ID.getID()).getResult();
        List<Episodes> list = result.getEpisodes();
        int count = 1;
        for (Episodes episode : list) {
            DownloadVideoID downloadVideoID = new DownloadVideoID(episode.getBvid(), episode.getCid())
                    .setEP(episode.getEpId())
                    .setSSID(result.getSeason_id());
            DownloadVideoInfo info = new DownloadVideoInfo(
                    Param,
                    downloadVideoID,
                    episode.getShare_copy(),
                    episode.getShare_copy(),
                    episode.getShare_copy(),
                    count,
                    list.size()
            );

            Pages.add(info);
            count++;
        }
    }

    private void getVideos() throws IOException {
        WEBVideoINFOJsonRootBean.Data data = new VideoInfo().getInfo(ID.getBV()).getData();
        String title = data.getTitle();
        List<Pages> pages = data.getPages();
        for (Pages page : pages) {
            DownloadVideoInfo info = new DownloadVideoInfo(
                    Param,
                    new DownloadVideoID(ID.getID(), page.getCid()),
                    pages.size() > 1 ? title + "#" + page.getPage() + "#" + page.getPart() : title,
                    title,
                    page.getPart(),
                    page.getPage(),
                    data.getVideos()
            );

            this.Pages.add(info);
        }
    }

    public DownloadVideoInfo getPage(int page) {
        return this.Pages.get(page - 1);
    }

    public void setCookie(Cookie cookie) {
        for (DownloadVideoInfo page : this.Pages) {
            page.getParam().append(cookie);
        }
    }
}