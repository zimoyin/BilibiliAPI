package github.zimoyin.core.comment.info;

import com.alibaba.fastjson.JSONObject;
import github.zimoyin.core.Incompleteness;
import github.zimoyin.core.comment.enums.CommentType;
import github.zimoyin.core.comment.pojo.area.lazy.CommentAreaLazyLoadJsonRoot;
import github.zimoyin.core.comment.pojo.area.page.CommentAreaPageTurningLoadreaJRoot;
import github.zimoyin.core.cookie.Cookie;
import github.zimoyin.core.utils.IDConvert;
import github.zimoyin.core.utils.net.httpclient.HttpClientResult;
import github.zimoyin.core.utils.net.httpclient.HttpClientUtils;

import java.io.IOException;
import java.util.HashMap;

/**
 * 指定评论对话树
 */
@Incompleteness
public class CommentTree {
    private static final String URL ="http://api.bilibili.com/x/v2/reply/reply";
    private Cookie cookie;

    /**
     * 视频评论区回复
     *
     * @param bv  视频bv id
     * @param root 根回复 rpid
     * @param dialog 对话树根 rpid
     * @param size   每页项数
     * @return 原数据
     * page：页面信息
     * replies：热评列表
     * @throws IOException
     */
    public CommentAreaLazyLoadJsonRoot getVideoPojo(String bv, String root,String dialog, int size) throws IOException {
        int oid = IDConvert.BvToAvNumber(bv);
        return getPojo(CommentType.AV_ID, String.valueOf(oid),root,dialog,size);
    }

    /**
     * 评论对话数
     *
     * @param type 评论区类型
     * @param oid  评论区的oid，每个type对影一个oid，oid具体是什么见该type的注释中“：”后面的内容
     * @param root 根回复 rpid
     * @param dialog 对话树根 rpid
     * @param size   每页项数
     * @return 原数据
     * @throws IOException
     */
    public CommentAreaLazyLoadJsonRoot getPojo(CommentType type, String oid, String root,String dialog, int size) throws IOException {
        String page = getPage(type, oid, root, dialog, size);
        CommentAreaLazyLoadJsonRoot json = JSONObject.parseObject(page, CommentAreaLazyLoadJsonRoot.class);
        return json;
    }


    /**
     * 评论对话数
     *
     * @param type 评论区类型
     * @param oid  评论区的oid，每个type对影一个oid，oid具体是什么见该type的注释中“：”后面的内容
     * @param root 根回复 rpid
     * @param dialog 对话树根 rpid
     * @param size   每页项数
     * @return 原数据
     * @throws IOException
     */
    public String getPage(CommentType type, String oid, String root,String dialog, int size) throws IOException {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("type", type.toString());
        params.put("oid", oid);
        params.put("root", root);
        params.put("dialog", dialog);
        params.put("size", String.valueOf(size));
//        params.put("min_floor",min_floor);//?????

        HttpClientResult result = HttpClientUtils.doGet(URL, cookie != null ? cookie.toHeaderCookie() : null, params);
        return result.getContent();
    }
}
