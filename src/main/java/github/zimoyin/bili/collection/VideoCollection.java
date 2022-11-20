package github.zimoyin.bili.collection;

import github.zimoyin.bili.collection.pojo.collection.CollectionJsonRoot;
import github.zimoyin.bili.collection.pojo.userlist.UserCollectionListJsonRoot;
import github.zimoyin.bili.cookie.Cookie;
import github.zimoyin.bili.utils.net.httpclient.HttpClientResult;
import github.zimoyin.bili.utils.net.httpclient.HttpClientUtils;
import github.zimoyin.bili.utils.net.httpclient.ShortURL;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

@Data
public class VideoCollection {
//    private static final Logger logger = LoggerFactory.getLogger(VideoCollection.class);
    private static final org.apache.logging.log4j.Logger logger = LogManager.getLogger(VideoCollection.class);
    private Cookie cookie;
    /**
     * 合集的URL
     */
    private String url;
    /**
     * 合集标题
     */
    private String title;
    /**
     * 合集的season_id
     */
    private long seasonID;
    private String seasonUrl;
    /**
     * 合集的简介
     */
    private String description;

    /**
     * 播放量
     */
    private String view;
    /**
     * 播放量
     */
    private double viewNumber;

    /**
     * 订阅状态
     */
    private boolean subscribe;

    /**
     * 用户的名称
     */
    private String uname;
    /**
     * 用户的mid
     */
    private long mid;
    /**
     * 用户的空间URL
     */
    private String spaceURL;
    /**
     * 用户的签名
     */
    private String sign;
    /**
     * 播放列表
     */
    private ArrayList<VideoItem> videos = new ArrayList<VideoItem>();

    private String bv;


    public VideoCollection(String bv) {
        this.bv = bv;
        try {
            init();
        } catch (Exception e) {
            throw new RuntimeException("无法获取到视频的集合信息",e);
        }
    }

    public VideoCollection(Cookie cookie, String bv) {
        this.cookie = cookie;
        this.bv = bv;
        try {
            init();
        } catch (Exception e) {
            throw new RuntimeException("无法获取到视频的集合信息",e);
        }
    }

    private void init() throws IOException {
        HttpClientResult httpClientResult = HttpClientUtils.doGet("https://www.bilibili.com/video/"+bv);
        Document parse = Jsoup.parse(httpClientResult.getContent());


        //一些属性
        Elements e1 = parse.getElementsByClass("first-line-title");
        for (Element str : e1) {
            //合集链接
            this.url = str.attr("href");
            //合集名称
            this.title = str.text();
            //合集的season_id
            this.seasonUrl = str.attr("href");
            String sid = ShortURL.getURLValue(url, "sid").replaceAll("\\s","");
            this.seasonID = Long.parseLong(sid);
        }
        //up 信息
        Elements e2 = parse.getElementsByClass("username");
        for (Element str : e2) {
            this.uname = str.text();//用户名
            this.spaceURL = str.attr("href");//空间的url
            this.mid = Long.parseLong(spaceURL.replaceAll("\\D", ""));
            this.sign = parse.getElementsByClass("desc").get(0).text();
        }

        //简介
        Elements e3 = parse.getElementsByClass("abstract");
        for (Element str : e3) {
            this.description = str.text();
        }
        //播放量
        Elements e4 = parse.getElementsByClass("play-num");
        for (Element str : e4) {
            this.view = str.text();
            String rep = str.text().replaceAll("播放", "");
            this.viewNumber = Double.parseDouble(rep.replaceAll("[^\\d\\\\.]", ""));
            if (rep.contains("万")) viewNumber *= 10000;
            if (rep.contains("千")) viewNumber *= 1000;
            if (rep.contains("百")) viewNumber *= 100;
        }
        //订阅状态
        Elements e5 = parse.getElementsByClass("second-line_right");
        for (Element str : e5) {
            if (str.text().equals("已订阅")) this.subscribe = true;
        }
        //播放列表
        Elements e6 = parse.select(".video-episode-card > *");
        for (Element str : e6) {
            //打印播放列表
//            System.out.print(str.getElementsByClass("video-episode-card__info-title").get(0).text());
//            System.out.print("\t");
//            System.out.println(str.getElementsByClass("video-episode-card__info-duration").get(0).text());

            String videoTitle = str.getElementsByClass("video-episode-card__info-title").get(0).text();
            String duration = str.getElementsByClass("video-episode-card__info-duration").get(0).text();
            VideoItem videoItem = new VideoItem(videoTitle,duration);
            this.videos.add(videoItem);
        }
    }

    /**
     * 获取这个合集的具体信息
     * @return
     * @throws IOException
     */
    public CollectionJsonRoot getInfo() throws IOException {
        CollectionInfo collectionInfo = new CollectionInfo();
        return collectionInfo.getJsonPojo(mid, seasonID);
    }
    /**
     * 获取用户的集合列表
     * @return
     * @throws IOException
     */
    public UserCollectionListJsonRoot getList() throws IOException {
        UserCollection collectionInfo = new UserCollection();
        return collectionInfo.getJsonPojo(mid);
    }


    @Data
    @NoArgsConstructor
    public static class VideoItem {
        /**
         * 视频标题
         */
        private String title;
        /**
         * 时间长度 hh:mm:ss
         */
        private String duration;

        public VideoItem(String title, String duration) {
            this.title = title;
            this.duration = duration;
        }
    }
}
