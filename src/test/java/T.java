import github.zimoyin.core.cookie.GlobalCookie;
import github.zimoyin.core.exception.CodeException;
import github.zimoyin.core.exception.CookieNotFoundException;
import github.zimoyin.core.favorites.FavoriteUtil;
import github.zimoyin.core.search.enums.SearchType;
import github.zimoyin.core.utils.net.httpclient.HttpClientResult;
import github.zimoyin.core.utils.net.httpclient.HttpClientUtils;
import github.zimoyin.core.collection.VideoCollection;
import github.zimoyin.core.video.download.VideoDownload;
import org.apache.http.HttpException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.ExecutionException;

public class T {
    SearchType type;

    @Test
    public void s() throws HttpException, IOException, NoSuchAlgorithmException, KeyStoreException, URISyntaxException, KeyManagementException, CodeException, CookieNotFoundException {
//        CreateFavorite createFavorite = new CreateFavorite(GlobalCookie.getInstance());
//        System.out.println(createFavorite.create("test"));

        FavoriteUtil util = new FavoriteUtil(GlobalCookie.getInstance());

//        System.out.println(util.getFavoriteID("子墨茵", "test1"));

    }

    public static void main(String[] args) throws CookieNotFoundException, IOException, NoSuchAlgorithmException, KeyStoreException, URISyntaxException, KeyManagementException, ExecutionException, InterruptedException {
        VideoDownload videoDownload = new VideoDownload();
    }

    /**
     * 获取当前视频下合集列表
     */
    void a() throws IOException {
        HttpClientResult httpClientResult = HttpClientUtils.doGet("https://www.bilibili.com/video/BV1WT41177px?spm_id_from=333.999.0.0&vd_source=58220274822a57a7161ee50c651db449");
        Document parse = Jsoup.parse(httpClientResult.getContent());


        //一些属性
        Elements elementsByClass2 = parse.getElementsByClass("first-line-title");
        for (Element str : elementsByClass2) {
            //合集链接
            System.out.println("URL: http:"+str.attr("href"));
            //合集名称
            System.out.println("Title:"+str.text());
            //合集的season_id
            System.out.println("season id:"+str.attr("href").split("\\?")[1].split("=")[1]);
        }

        //简介
        Elements elementsByClass = parse.getElementsByClass("abstract");
        for (Element str : elementsByClass) {
            System.out.println(str.text()+" end");
        }
        //播放量
        Elements elementsByClas = parse.getElementsByClass("play-num");
        for (Element str : elementsByClas) {
            System.out.println(str.text());
        }
        //订阅状态
        Elements elementsBylas = parse.getElementsByClass("second-line_right");
        for (Element str : elementsBylas) {
            System.out.println(str.text());
        }
        //播放列表
        Elements elementsByClass0 = parse.select(".video-episode-card > *");
        for (Element str : elementsByClass0) {
            System.out.print(str.getElementsByClass("video-episode-card__info-title").get(0).text());
            System.out.print("\t");
            System.out.println(str.getElementsByClass("video-episode-card__info-duration").get(0).text());
        }
    }
}