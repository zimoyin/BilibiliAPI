package github.zimoyin.bili.favorites.info;

import com.alibaba.fastjson.JSONObject;
import github.zimoyin.bili.cookie.Cookie;
import github.zimoyin.bili.favorites.pojo.conter.FavoriteContentListJsonRoot;
import github.zimoyin.bili.utils.net.httpclient.HttpClientResult;
import github.zimoyin.bili.utils.net.httpclient.HttpClientUtils;

import java.io.IOException;
import java.util.HashMap;

/**
 * 获取收藏夹内容明细列表
 */
public class FavoriteContentList {
    private static final String URL = "http://api.bilibili.com/x/v3/fav/resource/list";
    private Cookie cookie;

    public FavoriteContentList() {
    }

    public FavoriteContentList(Cookie cookie) {
        this.cookie = cookie;
    }


    /**
     * 解析收藏夹内容明细列表
     * @param page  收藏夹内容明细列表 原数据
     * @return
     */
    public FavoriteContentListJsonRoot getJsonPojo(String page){
        FavoriteContentListJsonRoot root = JSONObject.parseObject(page, FavoriteContentListJsonRoot.class);
        return root;
    }


    /**
     * 获取收藏夹内容明细列表
     * @param media_id 目标收藏夹mlid（完整id）
     * @param pn 页码:默认为1
     * @return
     * @throws IOException
     */
    public FavoriteContentListJsonRoot getJsonPojo(long media_id, int pn) throws IOException {
        String page = getPage(media_id, pn);
        FavoriteContentListJsonRoot root = JSONObject.parseObject(page, FavoriteContentListJsonRoot.class);
        return root;
    }

    /**
     * 获取收藏夹内容明细列表
     * @param media_id 目标收藏夹mlid（完整id）
     * @param pn 页码:默认为1
     * @param keyword 搜索关键字
     * @return
     * @throws IOException
     */
    public FavoriteContentListJsonRoot getJsonPojo(long media_id, int pn, String keyword) throws IOException {
        String page = getPage(media_id, pn, keyword);
        FavoriteContentListJsonRoot root = JSONObject.parseObject(page, FavoriteContentListJsonRoot.class);
        return root;
    }

    /**
     * 获取收藏夹内容明细列表
     * @param media_id 目标收藏夹mlid（完整id）
     * @param pn 页码:默认为1
     * @param keyword 搜索关键字
     * @param order 排序方式
     * @param tid 分区tid: 0：全部分区(默认)
     * @param ps 每页数量	:	定义域：1-20
     * @param type 查询范围
     * @param platform 平台标识
     * @return
     */
    public FavoriteContentListJsonRoot getJsonPojo(long media_id, int pn, String keyword, Order order, long tid, int ps, Type type, String platform) throws IOException {
        String page = getPage(media_id, pn, keyword, order, tid, ps, type, platform);
        FavoriteContentListJsonRoot root = JSONObject.parseObject(page, FavoriteContentListJsonRoot.class);
        return root;
    }


    /**
     * 获取收藏夹内容明细列表
     * @param media_id 目标收藏夹mlid（完整id）
     * @param pn 页码:默认为1
     * @return
     * @throws IOException
     */
    public String getPage(long media_id,int pn) throws IOException {
        return getPage(media_id, pn,null,null,-1,-1,null,null);
    }

    /**
     * 获取收藏夹内容明细列表
     * @param media_id 目标收藏夹mlid（完整id）
     * @param pn 页码:默认为1
     * @param keyword 搜索关键字
     * @return
     * @throws IOException
     */
    public String getPage(long media_id,int pn, String keyword) throws IOException {
        return getPage(media_id, pn, keyword,null,-1,-1,null,null);
    }

    /**
     * 获取收藏夹内容明细列表
     * @param media_id 目标收藏夹mlid（完整id）
     * @param pn 页码:默认为1
     * @param keyword 搜索关键字
     * @param order 排序方式
     * @param tid 分区tid: 0：全部分区(默认)
     * @param ps 每页数量	:	定义域：1-20
     * @param type 查询范围
     * @param platform 平台标识
     * @return
     */
    public String getPage(long media_id,
                          int pn,
                          String keyword,
                          Order order,
                          long tid,
                          int ps,
                          Type type,
                          String platform) throws IOException {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("media_id", String.valueOf(media_id));
        if (pn > -1)params.put("pn", String.valueOf(pn));
        if (keyword!=null && keyword.length() > 0)params.put("keyword", keyword);
        if (order != null)params.put("order", order.getOrder());
        if (tid >-1)params.put("tid", String.valueOf(tid));
        if (ps > -1)params.put("ps", String.valueOf(ps));
        else params.put("ps", "20");
        if (type != null)params.put("type", String.valueOf(type.getType()));
        if (platform != null && platform.length() > 0)params.put("platform", platform);


        HttpClientResult httpClientResult = HttpClientUtils.doGet(URL, cookie != null ? cookie.toHeaderCookie() : null, params);
        return httpClientResult.getContent();
    }

    /**
     * 排序方式
     */
    static enum Order {
        /**
         * 按收藏时间
         */
        FavoriteTime("mtime"),
        /**
         * 按播放量
         */
        View("view"),
        /**
         * 按投稿时间
         */
        publishTime("pubtime");
        private String order;

        Order(String order) {
            this.order = order;
        }

        public String getOrder() {
            return order;
        }
    }

    /**
     * 查询范围
     */
    static enum Type {
        /**
         * 全部收藏夹
         */
        All(1),
        /**
         * 当前收藏夹（对应media_id）
         */
        This(0);
        private int type;

        Type(int type) {
            this.type = type;
        }

        public int getType() {
            return type;
        }
    }
}
