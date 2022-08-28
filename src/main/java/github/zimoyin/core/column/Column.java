package github.zimoyin.core.column;

import github.zimoyin.core.utils.net.httpclient.HttpClientResult;
import github.zimoyin.core.utils.net.httpclient.HttpClientUtils;
import lombok.Data;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

/**
 * 专栏内容(爬取html并加以解析的实现，非访问接口实现)
 */
@Data
public class Column {
    /**
     * 专栏html
     */
    private String html;
    /**
     * 正文的html
     */
    private String contentHtml;
    /**
     * 文章标题
     */
    private String title;
    /**
     * 作者名称
     */
    private String authorName;
    /**
     * 作者粉丝数
     */
    private String authorFanNumber;
    /**
     * 作者的文章数
     */
    private String views;
    /**
     * 文章地址
     */
    private String url = "https://www.bilibili.com/read/";
    /**
     * cv
     */
    private String cv;
    /**
     * 自然段
     */
    private ArrayList<Paragraph> paragraphs = new ArrayList<Paragraph>();
    private String text;
    public Column(String cv) {
        this.url = url + cv;
        this.cv = cv;
        try {
            init();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void init() throws IOException {
        //访问URL
        HttpClientResult result = HttpClientUtils.doGet(url);
        html = result.getContent();
        //解析HTML
        Document parse = Jsoup.parse(html);
        this.text = parse.text();
        //正文位置
        Elements parseContent = parse.getElementsByClass("article-container");
        Element content = parseContent.get(0);
        if (parseContent.size() > 1) throw new IllegalArgumentException("检测到多个正文");
        //标题
        Elements titleEl = content.getElementsByClass("title");
        titleEl.stream().flatMap(element -> element.getElementsByTag("h1").stream()).map(Element::text).filter(s -> title == null).forEach(s -> title = s);
        //up 名称
        Elements name = content.getElementsByClass("up-name");
        Collections.unmodifiableList(name).stream().map(Element::text).filter(s -> authorName == null).forEach(s -> authorName = s);
        //up 粉丝数量
        Elements fan = content.getElementsByClass("fans-num");
        fan.stream().map(Element::text).filter(s -> authorFanNumber == null).forEach(s -> authorFanNumber = s);
        //up 文章数量
        Elements view = content.getElementsByClass("view-num");
        view.stream().map(Element::text).filter(s -> views == null).forEach(s -> views = s);
        //正文
        Elements select = content.select("div#read-article-holder > *");
        this.contentHtml = select.html();
        for (Element element : select) {
            Paragraph paragraph = new Paragraph(element);
            paragraphs.add(paragraph);
        }
    }
}
