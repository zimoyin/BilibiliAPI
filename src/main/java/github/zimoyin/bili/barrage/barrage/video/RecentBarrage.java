package github.zimoyin.bili.barrage.barrage.video;


import github.zimoyin.bili.barrage.barrage.BarrageInterface;
import github.zimoyin.bili.barrage.data.Barrage;
import github.zimoyin.bili.utils.net.httpclient.HttpClientResult;
import github.zimoyin.bili.utils.net.httpclient.HttpClientUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.regex.Pattern;

/**
 *
 * 近期弹幕
 * @API: https://github.com/SocialSisterYi/bilibili-API-collect
 */
public class RecentBarrage extends ArrayList<Barrage> implements BarrageInterface {
    /**
     * https://api.bilibili.com/x/player/pagelist?bvid=BV1r54y1Q7go&jsonp=jsonp
     * cid
     * https://comment.bilibili.com/{cid}.xml
     * https://api.bilibili.com/x/v1/dm/list.so?oid={cid}
     * 结果一致
     */
    private static final String XML_URL_TEMP = "https://comment.bilibili.com/%s.xml";
    private static final String API_URL_TEMP = "https://api.bilibili.com/x/v1/dm/list.so?oid=%s";
    private static final String CID_URL_TEMP = "https://api.bilibili.com/x/player/pagelist?bvid=%s&jsonp=jsonp";
    private static final int MAX = 1200;


    public RecentBarrage() {

    }
    public RecentBarrage(String bv) throws Exception {
        String xml = getPage(bv);
        //6.Jsoup解析html
        Document document = Jsoup.parse(xml);
        //像js一样，通过标签获取 s
        Elements tag_d = document.getElementsByTag("d");
        tag_d.forEach(new Consumer<Element>() {
            @Override
            public void accept(Element element) {
                Barrage barrage = new Barrage();
                String p = element.attr("p");
                String text = element.text();
                String[] split = p.split(",");
                int index = 1;
                for (String val : split) {
                    val = val.trim();
                    boolean isMatch = Pattern.matches(".*\\D+.*", val);
                    barrage.setVal(index, isMatch ? 0 : Double.parseDouble(val), val);
                    index++;
                }
                barrage.setText(text);
                add(barrage);
            }
        });
    }


    /**
     * 返回最大弹幕数
     * @return
     */
    public int getMAX() {
        return MAX;
    }

    /**
     * 返回弹幕
     * @param bv
     * @return
     * @throws Exception
     */
    public ArrayList<Barrage> getBarrage(String bv) throws Exception {
        ArrayList<Barrage> barrages = new ArrayList<>();
        String xml = getPage(bv);
        //6.Jsoup解析html
        Document document = Jsoup.parse(xml);
        //像js一样，通过标签获取 s
        Elements tag_d = document.getElementsByTag("d");
        tag_d.forEach(new Consumer<Element>() {
            @Override
            public void accept(Element element) {
                Barrage barrage = new Barrage();
                String p = element.attr("p");
                String text = element.text();
                String[] split = p.split(",");
                int index = 1;
                for (String val : split) {
                    val = val.trim();
                    boolean isMatch = Pattern.matches(".*\\D+.*", val);
                    barrage.setVal(index, isMatch ? 0 : Double.parseDouble(val), val);
                    index++;
                }
                barrage.setText(text);
                barrages.add(barrage);
            }
        });
        return barrages;
    }

    @Override
    public String getPage(String bv) throws Exception {
        String cid = getCID(bv);
        String xml = getXML(cid);
        return xml;
    }


    /**
     * 获取视频的弹幕xml文件
     *
     * @param cid
     * @return
     * @throws Exception
     */
    private String getXML(String cid) throws Exception {
        HttpClientResult httpClientResult = HttpClientUtils.doGet(String.format(API_URL_TEMP, cid));//API_URL_TEMP
        String content = httpClientResult.getContent();
        return content;
    }
}
