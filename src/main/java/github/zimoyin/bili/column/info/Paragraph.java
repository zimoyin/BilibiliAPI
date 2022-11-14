package github.zimoyin.bili.column.info;

import lombok.Data;
import org.apache.logging.log4j.LogManager;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


import java.util.ArrayList;
import java.util.HashMap;

@Data
public class Paragraph {
    /**
     * 自然段的文字
     */
    private String text;
    /**
     * 自然段html
     */
    private String html;
    /**
     * 是否有加粗文字
     */
    private boolean isBold;
    private ArrayList<String> boldText;
    private String boldHtml;
    private ArrayList<String> boldStyles;
    /**
     * 是否有其他颜色文字
     */
    private boolean isColour;
    private ArrayList<String> colourText;
    private String colourHtml;
    private ArrayList<String> colourStyles;
    /**
     * 是否是标题
     */
    private boolean isTitle;
    private ArrayList<String> titleText;
    private String titleHtml;
    private ArrayList<String> titleStyles;
    /**
     * 是否是引论
     */
    private boolean isIntroduction;
    private ArrayList<String> introductionText;
    private String introductionHtml;
    private ArrayList<String> introductionStyles;
    /**
     * 图片的src
     */
    private ArrayList<String> imageFerSrc;
    private String imageFerHtml;
    /**
     * 图片的src
     */
    private ArrayList<String> imageSrc;
    private String imageHtml;
    /**
     * 图片：属性
     */
    private HashMap<String, Attributes> image = new HashMap<String, Attributes>();
    private Element element;

    public Paragraph(Element element) {
        this.html = element.html();
        this.text = element.text();
        this.element = element;
        init();
    }

    private void init() {
        //将 加粗 标题 对正文加以样式才是真正的正文
        content();
        color();
        title();
        bold();
        blockquote();
        image();
    }

    private void content() {
        //获取正文
        String text = element.text();
        if (!(text.length() <= 0)) this.text = text;
    }

    /**
     * 有颜色 的字符串
     * 注意 当样式描述的字符串1 小于 字符串2 那么样式就跟随样式1
     * 字越少优先级越高
     * eg: <class=red >我是<class = blue>小可爱呀</class></>
     * 渲染是先渲染"我是小可爱"为红色，然后再次渲染"小可爱"为蓝色
     */
    private void color() {
        //红色 的字符串
        this.colourText = new ArrayList<>();
        this.colourStyles = new ArrayList<>();
        Elements tag = element.select("*");
        for (Element element1 : tag) {
            String attr = element1.attr("class");
            if (attr.contains("color")) {
                colourText.add(element1.text());
                colourStyles.add(element1.attr("class"));
            }
        }
    }

    /**
     * 标题 的字符串
     */
    private void title() {
        //标题 的字符串
        Elements title = element.getElementsByTag("h1");
        this.titleHtml = title.html();
        if (title.size() > 0) {
            this.titleText = new ArrayList<>();
            this.titleStyles = new ArrayList<>();
        }
        for (Element element1 : title) {
            this.isTitle = true;
            this.titleText.add(element1.text());
            this.titleStyles.add(element1.attr("style"));
        }
    }

    /**
     * 加粗 的字符串
     */
    private void bold() {
        //加粗 的字符串
        Elements strong = element.getElementsByTag("strong");
        this.boldHtml = strong.html();
        if (strong.size() > 0) {
            this.boldText = new ArrayList<>();
            this.boldStyles = new ArrayList<>();
        }
        for (Element element1 : strong) {
            this.isBold = true;
            this.boldText.add(element1.text());
            this.boldStyles.add(element1.attr("class"));
        }
    }

    /**
     * 引论 的字符串
     */
    private void blockquote() {
        //引论 的字符串
        Elements blockquote = element.getElementsByTag("blockquote");
        this.introductionHtml = blockquote.html();
        if (blockquote.size() > 0) {
            this.introductionText = new ArrayList<>();
            this.introductionStyles = new ArrayList<>();
        }
        for (Element element1 : blockquote) {
            this.isIntroduction = true;
            System.out.println("[引论] " + element1.text());
            this.introductionText.add(element1.text());
            this.introductionStyles.add(element1.attr("style"));
        }
    }


    /**
     * 图片
     */
    private void image() {
        //图片 0
        Elements img1 = element.getElementsByTag("img");
        if (img1.size() > 0) {
            this.imageFerSrc = new ArrayList<>();
        }
        //遍历属性
        img1.forEach(element1 -> {
//            System.out.println("[图片-0]");
//            imageAtt(element1);
            this.image.put(element1.attr("data-src"), element1.attributes());
            this.imageFerSrc.add(element1.attr("data-src"));
            this.imageFerHtml = this.imageFerHtml + element1.text();
        });
        //-------------------------------------------------------------------------------
        //图片 1
        Elements images = element.getElementsByClass("image-package");
        if (images.size() > 0) {
            this.imageSrc = new ArrayList<>();
        }
        for (Element image : images) {
            //遍历这个自然段下的所有图片
//            System.out.println("[图片-1]");
            for (Element img2 : image.getElementsByTag("img")) {
//                imageAtt(img2);
                this.image.put(img2.attr("data-src"), img2.attributes());
                this.imageSrc.add(img2.attr("data-src"));
                this.imageHtml = this.imageHtml + img2.text();
            }
        }

        if (this.imageFerSrc != null && this.imageSrc != null && this.imageFerSrc.size() > 0 && this.imageSrc.size() > 0) {
            LogManager.getLogger(Paragraph.class).warn("该段落同时解析了两次相同图片：\r\n{}", element);

        }
    }

    /**
     * 图片属性
     *
     * @param image
     */
    private static void imageAtt(Element image) {
        //遍历属性
        for (Attribute attribute : image.attributes())
            System.out.println(attribute.getKey() + ":" + attribute.getValue());
    }
}
