package github.zimoyin.core.video.download;

import github.zimoyin.core.cookie.Cookie;
import github.zimoyin.core.cookie.GlobalCookie;
import github.zimoyin.core.cookie.WebCookie;
import github.zimoyin.core.exception.CookieNotFoundException;
import github.zimoyin.core.exception.DownloadException;
import github.zimoyin.core.fanju.info.SeriesINFO;
import github.zimoyin.core.fanju.pojo.info.seriesI.Episodes;
import github.zimoyin.core.fanju.pojo.info.seriesI.Result;
import github.zimoyin.core.fanju.pojo.info.seriesI.SeriesJsonRootBean;
import github.zimoyin.core.user.pojo.user.Series;
import github.zimoyin.core.video.info.VideoInfo;
import github.zimoyin.core.video.info.pojo.info.WEBVideoINFOJsonRootBean;
import github.zimoyin.core.video.info.pojo.info.data.Pages;
import github.zimoyin.core.video.url.data.Fnval;
import github.zimoyin.core.video.url.data.QN;
import lombok.Data;
import org.apache.http.HttpException;

import java.io.File;
import java.util.HashMap;
import java.util.List;


/**
 * 注意：当设置内的参数发生变化时请重新构建
 * 视频下载设置（支持链式调用）,部分属性未能赋值的话，请调用build方法以此来让程序进行推导和初始化
 * 对于互动视频，请手动指定一个模块的cid这样才可以下载，至于自动下载所有p视频、下载指定范围视频p与下载所有模块视频，请找上层实现
 */
@Data
public class VideoDownloadSetting {
    /**
     * 视频的bv号
     */
    private String bv;
    /**
     * 视频的p数，默认下载第一p内容。
     * 如果是番剧的话，0 代表下载当前的剧集
     */
    private int page = 1;
    /**
     * 视频当前p的name，注意要先设置当前p再设置当前p的名称，否则不起作用
     */
    private String pageName;
    /**
     * 视频的所有的p数，由程序获取设置
     */
    private int pageCount = 1;
    /**
     * 视频page到cid的映射
     */
    private HashMap<Integer, Long> cidMap;
    /**
     * 根据bv号和page来推导，如有必要可以自行设置
     */
    private long cid;

    /**
     * 视频清晰度，720p以上需要cookie
     */
    private int qn = QN.P720;
    /**
     * 视频的格式：dash格式需要合并
     */
    private int fnval = Fnval.VideoFormat_flv;

    /**
     * 线程数（仅限于多线程
     */
    private int threadCount = 32;
    /**
     * cookie
     */
    private Cookie cookie;
    /**
     * 文件保存路径，请保证他是一个存在的物理磁盘上的一个具有权限的文件夹
     */
    private String path = "./download";
    /**
     * 文件名称，程序会自动推导，如有必要可以自行设置
     */
    private String fileName;
    /**
     * dash格式视频的匹配模板，他可以让你下载你想要的dash格式。
     * 因为dash格式特殊性，他会返回所有的视频链接，因此需要这个来筛选。
     * 如果不设置程序会使用默认值
     */
    private DashTemplate dashTemplate = new DashTemplate();

    /**
     * 视频信息
     */
    private WEBVideoINFOJsonRootBean videoInfo;

    /**
     * 番剧的ed
     */
    private String ep;
    /**
     * 番剧的ssid
     */
    private String ssid;


    public VideoDownloadSetting() {
    }

    public VideoDownloadSetting(String bv) {
        this.bv = bv;
    }

    public VideoDownloadSetting(String bv, int qn, int fnval) {
        this.bv = bv;
        this.qn = qn;
        this.fnval = fnval;
    }

    /**
     * 推导未赋值的属性的参数
     *
     * @return
     */
    public VideoDownloadSetting build() {
        //cookie
        buildCookie();
        //文件保存路径构建
        buildFileDirectory();
        //剧集
        try {
            buildFanJu();
        } catch (HttpException e) {
            throw new RuntimeException(e);
        }
        //视频信息对象
        buildVideoInfo();
        //视频的所有page数
        buildPageCount(this.videoInfo);
        //当前p名称
        buildPageName(this.videoInfo);
        //page到 cid 的映射
        buildCidMap(this.videoInfo);
        //设置cid
        buildCid(this.videoInfo);
        //文件名称
        buildFileName(this.videoInfo);
        return this;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////

    /**
     * 设置 videoInfo
     */
    private void buildVideoInfo() {
        try {
            VideoInfo videoInfoFay = new VideoInfo(cookie);
            videoInfo = videoInfoFay.getInfo(bv);
        } catch (Exception e) {
            throw new RuntimeException("无法获取到视频信息", e);
        }
    }


    /**
     * 构建文件保存路径
     */
    private void buildFileDirectory() {
        String path = getPath();
        File file = new File(path);
        boolean mkdirs = file.mkdirs();
        if (!file.isDirectory()) {
            throw new DownloadException("下载路径错误，他不是一个合法的物理文件夹 Path: " + path);
        }
    }

    /**
     * 构建文件名称
     *
     * @param info 视频信息类
     */
    private void buildFileName(WEBVideoINFOJsonRootBean info) {
        github.zimoyin.core.video.info.pojo.info.Data data = info.getData();
        //是否存在文件名，如果没有就赋值一个
        if (fileName == null) {
            fileName = data.getTitle();
            //总p数大于1p，并且当前p的名称不为空
            if (pageCount > 1 || pageName != null) {
                fileName = fileName + "#" + this.getPage() + "p#" + this.getPageName();
            }
        }
        //去掉非法字符
        fileName = fileName.replaceAll("[.?/:<>|*\"\\\\]", "");
        fileName = fileName.replaceAll("\\s+", "_");
    }

    /**
     * 构建当前P的name
     *
     * @param info 视频信息类
     */
    private void buildPageName(WEBVideoINFOJsonRootBean info) {
        github.zimoyin.core.video.info.pojo.info.Data data = info.getData();
        //如果就一个p就不去遍历了 或者 这个p有名称就不重复构建了
        if (this.getPageCount() <= 1 || this.getPageName() != null) {
            return;
        }
        //遍历所有p找到这个p的名称
        List<Pages> pages = data.getPages();
        for (Pages page1 : pages) {
            //从所有p 的列表中根据p号找到目标元素
            if (page1.getPage() == this.getPage()) {
                this.setPageName(page1.getPart());
                break;
            }
        }
    }


    /**
     * 设置 视频的所有page总数
     */
    private void buildPageCount(WEBVideoINFOJsonRootBean info) {
        github.zimoyin.core.video.info.pojo.info.Data data = info.getData();
        this.setPageCount(data.getVideos());
    }

    /**
     * 设置 page 到 cid 的映射
     */
    private void buildCidMap(WEBVideoINFOJsonRootBean info) {
        github.zimoyin.core.video.info.pojo.info.Data data = info.getData();
        this.cidMap = new HashMap<>();
        List<Pages> pages = data.getPages();
        for (Pages page1 : pages) {
            cidMap.put(page1.getPage(), page1.getCid());
        }
    }

    /**
     * 设置 cid 的映射
     */
    private void buildCid(WEBVideoINFOJsonRootBean info) {
        //如果有cid就不构建
        if (this.getCid() != 0) return;
        github.zimoyin.core.video.info.pojo.info.Data data = info.getData();
        //设置cid
        this.setCid(data.getCid());
        //如果设置的p数大于视频所有的p数总量则抛出异常
        if (getPageCount() < getPage()) throw new IllegalArgumentException("视频没有第 " + getPage() + " p");
        if (this.getPage() != 1) this.setCid(cidMap.get(this.getPage()));
    }

    /**
     * 构建 cookie 如果cookie为null就给cookie赋值全局Cookie，如果获取不到全局Cookie就用一个空Cookie
     */
    private void buildCookie() {
        if (cookie == null) {
            try {
                cookie = GlobalCookie.getInstance().getCookie();
            } catch (CookieNotFoundException e) {
                cookie = new WebCookie();
            }
            if (cookie == null) {
                cookie = new WebCookie();
            }
        }
    }

    /**
     * 如果是番剧就构建
     */
    private void buildFanJu() throws HttpException {
        //判断是否是番剧
        if (ssid == null && ep == null) return;
        //番剧信息
        SeriesINFO series = new SeriesINFO();
        //当前集信息
        Episodes episodes = null;
        //如果当前page（p数）为0就下载当前(p)集的视频
        //如果不为0就下载指定page的集视频
        if (this.getPage() == 0) {
            //获取剧集，根据ep获取，如果ep为null就获取第一集
            episodes = series.getEpisodes(ssid == null ? ep : ssid);
            this.ep = String.valueOf(ep);
        } else {
            SeriesJsonRootBean pojo = series.getPojo(ssid == null ? ep : ssid);
            Result result = pojo.getResult();
            //设置ssid
            this.ssid = String.valueOf(result.getSeason_id());
            //设置剧情信息
            episodes = result.getPage(page-1);
        }
        //设置bv
        String bvid = episodes.getBvid();
        this.setBv(bvid);
        //设置cid
        long cid = episodes.getCid();
        this.setCid(cid);
        //设置ep
        long ep = episodes.getId();
        this.ep = String.valueOf(ep);
    }


/** =====================================================  set方法 ============================================================ **/
    /**
     * 视频的bv号
     */
    public VideoDownloadSetting setBv(String bv) {
        this.bv = bv;
        //将名称设置为null
        setPageName(null);
        this.setFileName(null);
        return this;
    }

    /**
     * 视频的p数，默认下载第一p内容
     */
    public VideoDownloadSetting setPage(int page) {
        //设置名称为null
        this.page = page;
        this.setFileName(null);
        setPageName(null);
        return this;
    }

    /**
     * 根据bv号和page来推导，如有必要可以自行设置
     */
    public VideoDownloadSetting setCid(long cid) {
        this.cid = cid;
        this.setFileName(null);
        return this;
    }


    public void setEp(String ep) {
        this.ep = ep;
        this.setPage(0);
    }

    public void setSsid(String ssid) {
        this.ssid = ssid;
        this.setPage(0);
    }
}