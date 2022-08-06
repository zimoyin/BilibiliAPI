package github.zimoyin.core.comment.info;

import com.alibaba.fastjson.JSONObject;
import github.zimoyin.core.Incompleteness;
import github.zimoyin.core.comment.enums.CommentType;
import github.zimoyin.core.comment.pojo.area.page.CommentAreaPageTurningLoadreaJRoot;
import github.zimoyin.core.cookie.Cookie;
import github.zimoyin.core.utils.IDConvert;
import github.zimoyin.core.utils.net.httpclient.HttpClientResult;
import github.zimoyin.core.utils.net.httpclient.HttpClientUtils;

import java.io.IOException;
import java.util.HashMap;


/**
 * 评论回复
 */
@Incompleteness
public class CommentReply {
    private static final String URL ="http://api.bilibili.com/x/v2/reply/reply";
    private Cookie cookie;

    /**
     * 视频评论区回复
     *
     * @param bv  视频bv id
     * @param rpid 根回复 rpid
     * @param pn   页码
     * @return 原数据
     * page：页面信息
     * replies：热评列表
     * @throws IOException
     */
    public CommentAreaPageTurningLoadreaJRoot getVideoPojo(String bv, String rpid,int pn) throws IOException {
        int oid = IDConvert.BvToAvNumber(bv);
        return getPojo(CommentType.AV_ID, String.valueOf(oid), rpid, 49, pn);
    }

    /**
     * 评论区翻页热评
     *
     * @param type 评论区类型
     * @param oid  评论区的oid，每个type对影一个oid，oid具体是什么见该type的注释中“：”后面的内容
     * @param rpid 根回复 rpid
     * @param ps   每页项数（1-49）
     * @param pn   页码
     * @return 原数据
     * page：页面信息
     * replies：热评列表
     * @throws IOException
     */
    public CommentAreaPageTurningLoadreaJRoot getPojo(CommentType type, String oid, String rpid, int ps, int pn) throws IOException {
        String page = getPage(type, oid, rpid, ps, pn);
        CommentAreaPageTurningLoadreaJRoot json = JSONObject.parseObject(page, CommentAreaPageTurningLoadreaJRoot.class);
        return json;
    }


    /**
     * 评论区翻页热评
     *
     * @param type 评论区类型
     * @param oid  评论区的oid，每个type对影一个oid，oid具体是什么见该type的注释中“：”后面的内容
     * @param rpid 根回复 rpid
     * @param ps   每页项数（1-49）
     * @param pn   页码
     * @return 原数据
     * @throws IOException
     */
    public String getPage(CommentType type, String oid, String rpid, int ps, int pn) throws IOException {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("type", type.toString());
        params.put("oid", oid);
        params.put("root", rpid);
        params.put("ps", String.valueOf(ps));
        params.put("pn", String.valueOf(pn));

        HttpClientResult result = HttpClientUtils.doGet(URL, cookie != null ? cookie.toHeaderCookie() : null, params);
        return result.getContent();
    }
}
