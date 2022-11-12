package github.zimoyin.core.download.download.setting;

import github.zimoyin.core.cookie.Cookie;
import github.zimoyin.core.download.param.ParamBuilder;
import github.zimoyin.core.fanju.info.SeriesINFO;
import github.zimoyin.core.fanju.pojo.info.seriesI.Episodes;
import github.zimoyin.core.fanju.pojo.info.seriesI.SeriesJsonRootBean;
import github.zimoyin.core.video.info.VideoInfo;
import github.zimoyin.core.video.info.pojo.info.WEBVideoINFOJsonRootBean;
import github.zimoyin.core.video.info.pojo.info.data.Pages;
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
//            //构建 ParamBuilder
//            DownloadVideoID id = info.getID();
//            ParamBuilder builder = new ParamBuilder()
//                    .append(id.getBV())
//                    .append(id.getCID())
//                    .append(Param.getFourk())
//                    .append(Param.getFnver())
//                    .append(Param.getFnvals())
//                    .append(Param.getQn())
//                    .append(Param.getCookie());
            //分发 ParamBuilder
//            info.setParam(Param);

            Pages.add(info);
            count++;
        }
    }

    private void getVideos() throws IOException {
        WEBVideoINFOJsonRootBean.Data data = new VideoInfo().getInfo(ID.getBV()).getData();
        String title = data.getTitle();
        for (Pages page : data.getPages()) {
            DownloadVideoInfo info = new DownloadVideoInfo(
                    Param,
                    new DownloadVideoID(ID.getID(), page.getCid()),
                    title + "#" + page.getPage() + "#" + page.getPart(),
                    title,
                    page.getPart(),
                    page.getPage(),
                    data.getVideos()
            );
            //构建 ParamBuilder
//            DownloadVideoID id = info.getID();
//            ParamBuilder builder = new ParamBuilder()
//                    .append(id.getBV())
//                    .append(id.getCID())
//                    .append(Param.getFourk())
//                    .append(Param.getFnver())
//                    .append(Param.getFnvals())
//                    .append(Param.getQn())
//                    .append(Param.getCookie());
//            info.setParam(Param);

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