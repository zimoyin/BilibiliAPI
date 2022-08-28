package column;

import github.zimoyin.core.utils.net.httpclient.HttpClientResult;
import github.zimoyin.core.utils.net.httpclient.HttpClientUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Collections;

public class A {
    public static void main(String[] args) throws IOException {
//        String url = "https://www.bilibili.com/read/cv14320144";
//        String url = "https://www.bilibili.com/read/cv18309565?from=articleDetail&spm_id_from=333.976.b_726561645265636f6d6d656e64496e666f.3";
//        String url = "https://www.bilibili.com/read/cv13761907?from=search&spm_id_from=333.337.0.0";
        String url = "https://www.bilibili.com/read/cv18237381?from=search&spm_id_from=333.337.0.0";
        HttpClientResult result = HttpClientUtils.doGet(url);
        String content0 = result.getContent();

        Document parse = Jsoup.parse(content0);
        //正文位置
        Elements parseContent = parse.getElementsByClass("article-container");
        Element content = parseContent.get(0);
        if (parseContent.size() > 1) System.err.println("检测到多个正文");
        //标题
        Elements title = content.getElementsByClass("title");
        System.out.print("title: ");
        title.stream().flatMap(element -> element.getElementsByTag("h1").stream()).map(Element::text).forEach(System.out::println);
        //up 名称
        Elements name = content.getElementsByClass("up-name");
        System.out.print("up 名称: ");
        Collections.unmodifiableList(name).stream().map(Element::text).forEach(System.out::println);
        //up 粉丝数量
        Elements fan = content.getElementsByClass("fans-num");
        System.out.print("粉丝数量: ");
        fan.stream().map(Element::text).forEach(System.out::println);
        //up 文章数量
        Elements view = content.getElementsByClass("view-num");
        System.out.print("文章数量: ");
        view.stream().map(Element::text).forEach(System.out::println);
        //正文
//        Elements select = content.select("div#read-article-holder > p");
        Elements select = content.select("div#read-article-holder > *");
        content(select);
    }


    private static void content(Elements select) {
        System.out.println("-----------------------------------------------");
        System.out.println("共计自然段：" + select.size());
        for (int i = 0; i < select.size(); i++) {
            Element element = select.get(i);
//            System.out.println(i+":"+element);
            //图片
            Elements img1 = element.getElementsByTag("img");
            //遍历属性
            img1.forEach(element1 -> {
                System.out.println("[图片-0]");
                image(element1);
            });
            Elements images = element.getElementsByClass("image-package");
            for (Element image : images) {
                //遍历这个自然段下的所有图片
                System.out.println("[图片-1]");
                for (Element img2 : image.getElementsByTag("img")) {
                    image(img2);
                }
            }
            //加粗 的字符串
            Elements strong = element.getElementsByTag("strong");
            for (Element element1 : strong) {
                System.out.println("[加粗] "+element1.text());
            }
            //引论 的字符串
            Elements blockquote = element.getElementsByTag("blockquote");
            for (Element element1 : blockquote) {
                System.out.println("[引论] "+element1.text());
            }
            //标题 的字符串
            Elements title = element.getElementsByTag("h1");
            for (Element element1 : title) {
                System.out.println("[小标题] "+element1.text());
            }
            //红色 的字符串
            Elements red = element.getElementsByTag("color-pink-03");
            for (Element element1 : red) {
                System.out.println("[红色] "+element1.text());
            }

            //获取正文
            String text = element.text();
            if (!(text.length() <= 0)) System.out.println(text);
            System.out.println();
            //将 加粗 标题 对正文加以样式才是真正的正文
        }
        System.out.println("-----------------------------------------------");
    }


    private static void image(Element image) {
        //遍历属性
        for (Attribute attribute : image.attributes());
//            System.out.println(attribute.getKey() + ":" + attribute.getValue());
    }
}
