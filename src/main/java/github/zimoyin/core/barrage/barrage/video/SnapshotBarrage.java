package github.zimoyin.core.barrage.barrage.video;



import com.alibaba.fastjson.JSONObject;
import github.zimoyin.core.barrage.barrage.BarrageInterface;
import github.zimoyin.core.barrage.data.Barrage;
import github.zimoyin.core.utils.net.httpclient.HttpClientUtils;


import java.util.ArrayList;
import java.util.HashMap;

/**
 * 快照弹幕，只能获取视频最新的20条弹幕
 * <p>
 * https://api.bilibili.com/x/player/pagelist?bvid=BV1r54y1Q7go&jsonp=jsonp
 * cid
 * https://comment.bilibili.com/{cid}.xml
 * https://api.bilibili.com/x/v1/dm/list.so?oid={cid}
 * 结果一致
 * @API: https://github.com/SocialSisterYi/bilibili-API-collect
 */
public class SnapshotBarrage extends ArrayList<Barrage> implements BarrageInterface {
    //GET
    private static final String URL = "http://api.bilibili.com/x/v2/dm/ajax";
    private static final int MAX = 20;


    public SnapshotBarrage() {
    }

    public SnapshotBarrage(String bv) throws Exception {
        HashMap<String, String> param = new HashMap<>();
        param.put("aid", bv);
        String content = HttpClientUtils.doGet(URL, param).getContent();
        for (Object data : JSONObject.parseObject(content).getJSONArray("data")) {
            add(new Barrage(data.toString()));
        }
    }

    /**
     * 返回最大弹幕数
     *
     * @return
     */
    public int getMAX() {
        return MAX;
    }

    /**
     * 返回最近20条弹幕 JOSN
     *
     * @param avOrBv
     * @return
     * @throws Exception
     */
    public String getPage(String avOrBv) throws Exception {
        HashMap<String, String> param = new HashMap<>();
        param.put("aid", avOrBv);
        return HttpClientUtils.doGet(URL, param).getContent();
    }

    /**
     * 返回最近20条弹幕
     *
     * @param avOrBv
     * @return
     * @throws Exception
     */
    public ArrayList<Barrage> getBarrage(String avOrBv) throws Exception {
        HashMap<String, String> param = new HashMap<>();
        ArrayList<Barrage> list = new ArrayList<>();
        param.put("aid", avOrBv);
        String content = HttpClientUtils.doGet(URL, param).getContent();
        for (Object data : JSONObject.parseObject(content).getJSONArray("data")) {
            Barrage barrage = new Barrage(data.toString());
            list.add(barrage);
        }
        return list;
    }
}
