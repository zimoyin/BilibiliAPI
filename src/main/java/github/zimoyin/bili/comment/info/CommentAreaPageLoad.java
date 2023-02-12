package github.zimoyin.bili.comment.info;

import com.alibaba.fastjson.JSONObject;
import github.zimoyin.bili.comment.enums.CommentType;
import github.zimoyin.bili.comment.enums.Hot;
import github.zimoyin.bili.comment.enums.Sort;
import github.zimoyin.bili.comment.pojo.area.page.CommentAreaPageTurningLoadreaJRoot;
import github.zimoyin.bili.cookie.Cookie;
import github.zimoyin.bili.utils.IDConvert;
import github.zimoyin.bili.utils.net.httpclient.HttpClientResult;
import github.zimoyin.bili.utils.net.httpclient.HttpClientUtils;
import lombok.Data;

import java.io.IOException;
import java.util.HashMap;

/**
 * 评论区翻页加载
 */
@Data
public class CommentAreaPageLoad {
    private static  final String URL = "http://api.bilibili.com/x/v2/reply";
    private Cookie cookie;

    /**
     * 视频的评论区翻页加载
     * @param bv 视频的bv号
     * @param pn 页数
     * @return
     * @throws IOException
     */
    public CommentAreaPageTurningLoadreaJRoot getVideoCommentPojo(String bv,int pn) throws IOException {
        return getVideoCommentPojo(bv,Sort.TIME,pn);
    }

    /**
     * 视频的评论区翻页加载
     * @param bv 视频的bv号
     * @param pn 页数
     * @return
     * @throws IOException
     */
    public CommentAreaPageTurningLoadreaJRoot getVideoCommentPojo(String bv,Sort sort,int pn) throws IOException {
        String avid = String.valueOf(IDConvert.BvToAvNumber(bv));
        return getPojo(CommentType.AV_ID,avid,Sort.TIME,Hot.SHOW,49,pn);
    }

    /**
     * 视频的评论区翻页加载
     * @param type 评论区类型
     * @param oid 评论区的oid，每个type对影一个oid，oid具体是什么见该type的注释中“：”后面的内容
     * @param sort 排序方式：
     * 0：按时间
     * 1：按点赞数
     * 2：按回复数
     * @param nohot 是否不显示热评：
     * 1：不显示
     * 0：显示
     * @param ps 每页项数（1-49）
     * @param pn 页码
     * @return 原数据
     * @return
     * @throws IOException
     */
    public CommentAreaPageTurningLoadreaJRoot getPojo(CommentType type, String oid, Sort sort, Hot nohot, int ps, int pn) throws IOException {
        String page = getPage(type, oid, sort, nohot, ps, pn);
        CommentAreaPageTurningLoadreaJRoot json = JSONObject.parseObject(page, CommentAreaPageTurningLoadreaJRoot.class);
        return json;
    }

    /**
     * 视频的评论区翻页加载
     * @param bv 视频的bv号
     * @param pn 页数
     * @return
     * @throws IOException
     */
    public String getVideoCommentPage(String bv,int pn) throws IOException {
        String avid = String.valueOf(IDConvert.BvToAvNumber(bv));
        return getPage(CommentType.AV_ID,avid,Sort.TIME,Hot.SHOW,49,pn);
    }

    /**
     * 视频的评论区翻页加载
     * @param bv 视频的bv号
     * @param sort 评论排序方式
     * @param pn 页数
     * @return
     * @throws IOException
     */
    public String getVideoCommentPage(String bv,Sort sort,int pn) throws IOException {
        String avid = String.valueOf(IDConvert.BvToAvNumber(bv));
        return getPage(CommentType.AV_ID,avid,sort,Hot.SHOW,49,pn);
    }

    /**
     * 评论区翻页加载
     * @param type 评论区类型
     * @param oid 评论区的oid(对于视频评论来说通常一个纯数字的 avid )，每个type对应一个oid，oid具体是什么见该 CommentType中对于type 的注释中“：”后面的内容
     * @param sort 排序方式：
     * 0：按时间
     * 1：按点赞数
     * 2：按回复数
     * @param nohot 是否不显示热评：
     * 1：不显示
     * 0：显示
     * @param ps 每页项数（1-49）
     * @param pn 页码
     * @return 原数据
     * @throws IOException
     */
    public String getPage(CommentType type, String oid, Sort sort, Hot nohot, int ps, int pn) throws IOException {
        HashMap<String,String> params = new HashMap<String,String>();
        params.put("type", type.toString());
        params.put("oid",oid);
        params.put("sort",sort.toString());
        params.put("nohot",nohot.toString());
        params.put("ps", String.valueOf(ps));
        params.put("pn", String.valueOf(pn));

        HttpClientResult result = HttpClientUtils.doGet(URL, cookie != null ? cookie.toHeaderCookie() : null, params);
        return result.getContent();
    }
}
