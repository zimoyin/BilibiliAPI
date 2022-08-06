package github.zimoyin.core.comment.operation;

import com.alibaba.fastjson.JSON;
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
 * 点赞
 */
@Incompleteness
public class CommentLike extends CommentOperation{
    public CommentLike(Cookie cookie) {
        super(cookie);
    }

    /**
     * 给视频评论区的评论点赞
     * @param bv
     * @param rpid 目标评论rpid
     * @return
     * @throws IOException
     */
    public boolean likeVideo(String bv,long rpid) throws IOException {
        return like(CommentType.AV_ID, String.valueOf(IDConvert.BvToAvNumber(bv)), rpid);
    }

    /**
     * 点赞
     * @param type 评论区类型代码
     * @param oid 目标评论区id
     * @param rpid 目标评论rpid
     * @return
     * @throws IOException
     */
    public boolean like(CommentType type, String oid,long rpid) throws IOException {
        String result = likePage(type, oid, rpid);
        int code = JSONObject.parseObject(result).getInteger("code");
        return code == 0;
    }
    /**
     * 给视频评论区的评论取消点赞
     * @param bv
     * @param rpid 目标评论rpid
     * @return
     * @throws IOException
     */
    public boolean dislikeVideo(String bv,long rpid) throws IOException {
        return dislike(CommentType.AV_ID,String.valueOf(IDConvert.BvToAvNumber(bv)), rpid);
    }


    /**
     * 取消点赞
     * @param type 评论区类型代码
     * @param oid 目标评论区id
     * @param rpid 目标评论rpid
     * @return
     * @throws IOException
     */
    public boolean dislike(CommentType type, String oid,long rpid) throws IOException {
        String result = dislikePage(type, oid, rpid);
        int code = JSONObject.parseObject(result).getInteger("code");
        return code == 0;
    }


    /**
     * 点赞
     * @param type 评论区类型代码
     * @param oid 目标评论区id
     * @param rpid 目标评论rpid
     * @return
     */
    public String likePage(CommentType type, String oid,long rpid) throws IOException {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("oid", oid);
        params.put("rpid", String.valueOf(rpid));
        params.put("type", type.toString());
        params.put("action", "1");//点赞
        params.put("csrf",cookie.getCsrf());

        HttpClientResult result = HttpClientUtils.doPost(URL_like,cookie.toHeaderCookie(),params);
        return result.getContent();
    }

    /**
     * 取消点赞
     * @param type 评论区类型代码
     * @param oid 目标评论区id
     * @param rpid 目标评论rpid
     * @return
     */
    public String dislikePage(CommentType type, String oid,long rpid) throws IOException {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("oid", oid);
        params.put("rpid", String.valueOf(rpid));
        params.put("type", type.toString());
        params.put("action", "0");//点赞
        params.put("csrf",cookie.getCsrf());

        HttpClientResult result = HttpClientUtils.doPost(URL_like,cookie.toHeaderCookie(),params);
        return result.getContent();
    }
}
