package github.zimoyin.core.comment.operation;

import com.alibaba.fastjson.JSONObject;
import github.zimoyin.core.Incompleteness;
import github.zimoyin.core.comment.enums.CommentType;
import github.zimoyin.core.cookie.Cookie;
import github.zimoyin.core.utils.IDConvert;
import github.zimoyin.core.utils.net.httpclient.HttpClientResult;
import github.zimoyin.core.utils.net.httpclient.HttpClientUtils;

import java.io.IOException;
import java.util.HashMap;

/**
 * 置顶
 */
@Incompleteness
public class CommentTop extends CommentOperation{
    public CommentTop(Cookie cookie) {
        super(cookie);
    }

    /**
     * 给视频评论区的评论置顶
     * @param bv
     * @param rpid 目标评论rpid
     * @return
     * @throws IOException
     */
    public boolean topVideo(String bv,long rpid) throws IOException {
        return top(CommentType.AV_ID, String.valueOf(IDConvert.BvToAvNumber(bv)), rpid);
    }


    /**
     * 给视频评论区的评论取消置顶
     * @param bv
     * @param rpid 目标评论rpid
     * @return
     * @throws IOException
     */
    public boolean notTopVideo(String bv,long rpid) throws IOException {
        return notTop(CommentType.AV_ID, String.valueOf(IDConvert.BvToAvNumber(bv)), rpid);
    }

    /**
     * 置顶
     * @param type 评论区类型代码
     * @param oid 目标评论区id
     * @param rpid 目标评论rpid
     * @return
     * @throws IOException
     */
    public boolean top(CommentType type, String oid,long rpid) throws IOException {
        String result = topPage(type, oid, rpid);
        int code = JSONObject.parseObject(result).getInteger("code");
        return code == 0;
    }

    /**
     * 取消置顶
     * @param type 评论区类型代码
     * @param oid 目标评论区id
     * @param rpid 目标评论rpid
     * @return
     * @throws IOException
     */
    public boolean notTop(CommentType type, String oid,long rpid) throws IOException {
        String result = notTopPage(type, oid, rpid);
        int code = JSONObject.parseObject(result).getInteger("code");
        return code == 0;
    }

    /**
     * 置顶
     * @param type 评论区类型代码
     * @param oid 目标评论区id
     * @param rpid 目标评论rpid
     * @return
     */
    public String topPage(CommentType type, String oid,long rpid) throws IOException {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("oid", oid);
        params.put("rpid", String.valueOf(rpid));
        params.put("type", type.toString());
        params.put("action", "1");
        params.put("csrf",cookie.getCsrf());

        HttpClientResult result = HttpClientUtils.doPost(URL_like,cookie.toHeaderCookie(),params);
        return result.getContent();
    }

    /**
     * 取消置顶
     * @param type 评论区类型代码
     * @param oid 目标评论区id
     * @param rpid 目标评论rpid
     * @return
     */
    public String notTopPage(CommentType type, String oid,long rpid) throws IOException {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("oid", oid);
        params.put("rpid", String.valueOf(rpid));
        params.put("type", type.toString());
        params.put("action", "0");
        params.put("csrf",cookie.getCsrf());

        HttpClientResult result = HttpClientUtils.doPost(URL_like,cookie.toHeaderCookie(),params);
        return result.getContent();
    }
}
