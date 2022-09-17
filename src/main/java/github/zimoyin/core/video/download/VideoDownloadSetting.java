package github.zimoyin.core.video.download;

import github.zimoyin.core.cookie.Cookie;
import github.zimoyin.core.cookie.GlobalCookie;
import github.zimoyin.core.cookie.WebCookie;
import github.zimoyin.core.exception.CookieNotFoundException;
import github.zimoyin.core.exception.DownloadException;
import github.zimoyin.core.fanju.info.SeriesINFO;
import github.zimoyin.core.fanju.pojo.info.seriesI.Episodes;
import github.zimoyin.core.fanju.pojo.info.seriesI.SeriesJsonRootBean;
import github.zimoyin.core.user.pojo.user.Series;
import github.zimoyin.core.utils.IDConvert;
import github.zimoyin.core.video.info.VideoInfo;
import github.zimoyin.core.video.info.pojo.info.WEBVideoINFOJsonRootBean;
import github.zimoyin.core.video.info.pojo.info.data.Pages;
import github.zimoyin.core.video.url.data.Fnval;
import github.zimoyin.core.video.url.data.ID;
import github.zimoyin.core.video.url.data.QN;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.http.HttpException;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;


/**
 * 注意：当设置内的参数发生变化时请重新构建
 * 视频下载设置（支持链式调用）,部分属性未能赋值的话，请调用build方法以此来让程序进行推导和初始化
 * 对于互动视频，请手动指定一个模块的cid这样才可以下载，至于自动下载所有p视频、下载指定范围视频p与下载所有模块视频，请找上层实现
 */
@Data
public class VideoDownloadSetting {
    private ArrayList<EpisodesInfo> episodesList = new ArrayList<EpisodesInfo>();
    private String id;
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

    /**
     * 如果不存在cookie，且要求画质在1080p 则是否开启 预览1080p视频下载方案
     */
    private boolean isPreview1080p;
    /**
     * 如果已经存在同名称的文件是否覆盖重写
     */
    private boolean isOverride = true;

    public VideoDownloadSetting() {
    }
    public VideoDownloadSetting(Cookie cookie) {
        setCookie(cookie);
    }
    public VideoDownloadSetting(String id,Cookie cookie) {
        setID(id);
        setCookie(cookie);
    }
    /**
     * 视频id 可以是 ssid avid bvid epid
     *
     * @param id
     */
    public VideoDownloadSetting(String id) {
        initID(id);
    }

    public VideoDownloadSetting(String id, boolean isOverride) {
        this.isOverride = isOverride;
        initID(id);
    }

    public VideoDownloadSetting(boolean isOverride) {
        this.isOverride = isOverride;
    }

    public VideoDownloadSetting(String id, int qn, int fnval) {
//        this.bv = bv;
        this.qn = qn;
        this.fnval = fnval;
        initID(id);
    }

    /**
     * 根据给出的id智能判断属于什么类型，并赋值给相应的id类型，如：bv，av，ep等
     *
     * @param id
     */
    private void initID(String id) {
        this.id = id;
        if (id.trim().length() <= 2) throw new IllegalArgumentException("不合法的id，无法判断的id类型");
        String pr = id.trim().substring(0, 2).toUpperCase();
        switch (pr.toUpperCase()) {
            case "BV":
                this.bv = id;
                break;
            case "AV":
                this.bv = IDConvert.AvToBv(id);
                break;
            case "EP":
                this.ep = id;
                this.setPage(0);
                break;
            case "SS":
                this.ssid = id;
                this.setPage(0);
                break;
            default:
                throw new IllegalArgumentException("不合法的id，无法判断的id类型");
//                this.bv = IDConvert.AvToBv("av"+id);
        }
    }

    /**
     * 根据给出的id智能判断属于什么类型，并赋值给相应的id类型，如：bv，av，ep,ssid等
     *
     * @param id
     */
    public void setID(String id) {
        initID(id);
    }

    /**
     * 推导未赋值的属性的参数
     *
     * @return
     */
    public VideoDownloadSetting build() {
        boolean isFanju = false;
        //id赋值
        if (bv != null) id = bv;
        if (ep != null) id = ep;
        if (ssid != null) id = ssid;
        //cookie
        buildCookie();
        //文件保存路径构建
        buildFileDirectory();
        //剧集
        try {
            isFanju=buildFanJu();
        } catch (HttpException e) {
            throw new RuntimeException(e);
        }
        //视频信息对象
        buildVideoInfo();
        //视频的所有page数
        if (!isFanju)buildPageCount(this.videoInfo);
        //当前p名称
        if (!isFanju)buildPageName(this.videoInfo);
        //page到 cid 的映射
        if (!isFanju)buildCidMap(this.videoInfo);

        //设置cid
        buildCid();
        //智能判断是否需要开启预览1080p视频下载
        buildPrevie1080p();
        //文件名称
        buildFileName(this.videoInfo);
        return this;
    }

    public void update() {
        //id赋值
        if (bv != null) id = bv;
        if (ep != null) id = ep;
        if (ssid != null) id = ssid;
        //cookie
        buildCookie();
        //文件保存路径构建
        buildFileDirectory();
        //当前p名称
        if (episodesList.size() == 0)buildPageName(this.videoInfo);
        //设置bv号
        if (episodesList.size() > 0)this.setBv(episodesList.get(this.getPage()-1).getBvid());
        buildCid();
        //文件名称
        buildFileName(this.videoInfo);

    }
    ///////////////////////////////////////////////////////////////////////////////////////////

    /**
     * 构建是否下载预览1080p视频: 默认开启，
     */
    public void buildPrevie1080p() {
        boolean empty = this.getCookie().isEmpty();
        if (!isPreview1080p) {
            //isPreview1080p 为 false 时就进行判断是否开启
            //条件：不存在cookie，但是想要下载1080p视频
            if (empty && qn == QN.P1080_cookie) {
                this.isPreview1080p = true;
                this.qn = QN.P1080_cookie;
                this.fnval = Fnval.VideoFormat_mp4;
            }
        }
        if (ep != null || ssid != null) this.isPreview1080p = false;
    }

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
        //如果是番剧就直接返回
        if (episodesList.size() > 0) {
            fileName = episodesList.get(this.getPage() - 1).getName();
            //去掉非法字符
            fileName = fileName.replaceAll("[.?/:<>|*\"\\\\]", "");
            fileName = fileName.replaceAll("\\s+", "_");
            return;
        }
        WEBVideoINFOJsonRootBean.Data data = info.getData();
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
        WEBVideoINFOJsonRootBean.Data data = info.getData();
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
        WEBVideoINFOJsonRootBean.Data data = info.getData();
        this.setPageCount(data.getVideos());
    }

    /**
     * 设置 page 到 cid 的映射
     */
    private void buildCidMap(WEBVideoINFOJsonRootBean info) {
        WEBVideoINFOJsonRootBean.Data data = info.getData();
        this.cidMap = new HashMap<>();
        List<Pages> pages = data.getPages();
        for (Pages page1 : pages) {
            cidMap.put(page1.getPage(), page1.getCid());
        }
    }

    /**
     * 根据page 设置 cid 的映射
     */
    private void buildCid() {
        //如果获取不到cidMap 映射表则抛出异常
        if (cidMap == null || cidMap.size() == 0)
            throw new NullPointerException("无法获取到该视频的cidMap 映射表，无法获取对应cid");
        //如果设置的p数大于视频所有的p数总量则抛出异常
        if (getPageCount() < getPage() || getPage() <= -1)
            throw new IllegalArgumentException("视频没有第 " + getPage() + " p");
        //获取cid
        setCid(getCidMap().get(getPage()));
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
    private boolean buildFanJu() throws HttpException {
        cidMap = new HashMap<>();
        //判断是否是番剧
        if (ssid == null && ep == null) return false;
        //番剧信息
        SeriesINFO series = new SeriesINFO();
        SeriesJsonRootBean pojo = series.getPojo(ssid == null ? ep : ssid);
        SeriesJsonRootBean.Result result = pojo.getResult();
        //设置ssid
        this.ssid = String.valueOf("ss" + result.getSeason_id());
        //设置其他信息
        List<Episodes> episodesList1 = result.getEpisodes();
        pageCount = episodesList1.size();//设置总p数
        //设置番剧具体信息
        episodesList1.forEach(episodes0 -> {
            String ebvid = episodes0.getBvid();
            long ecid = episodes0.getCid();
            String ename = episodes0.getShare_copy();
            String epage = episodes0.getTitle();
            cidMap.put(Integer.parseInt(epage),ecid);
            episodesList.add(new EpisodesInfo(Integer.parseInt(epage), result.getEpisodes().size(), ecid, ebvid, ename));
        });
        return true;
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
     * 注意该p数为视频列表中的p数位置，如果第2p为预告，第10p为真正的第二集内容那么将会根据你的提供的p数来获取是预告还是真正的内容
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


    @Data
    @NoArgsConstructor
    static class EpisodesInfo {
        private int page;
        private int pageCount;
        private long cid;
        private String bvid;
        private String name;


        public EpisodesInfo(int page, int pageCount, long cid, String bvid, String name) {
            this.page = page;
            this.pageCount = pageCount;
            this.cid = cid;
            this.bvid = bvid;
            this.name = name;
        }
    }
}
