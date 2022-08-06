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
 * 删除
 */
@Incompleteness
public class CommentDelete extends CommentOperation{
    public CommentDelete(Cookie cookie) {
        super(cookie);
    }

    /**
     * 给视频评论区的评论删除
     * @param bv
     * @param rpid 目标评论rpid
     * @return
     * @throws IOException
     */
    public boolean deletePage(String bv,long rpid) throws IOException {
        return delete(CommentType.AV_ID, String.valueOf(IDConvert.BvToAvNumber(bv)), rpid);
    }

    /**
     * 删除
     * @param type 评论区类型代码
     * @param oid 目标评论区id
     * @param rpid 目标评论rpid
     * @return
     * @throws IOException
     */
    public boolean delete(CommentType type, String oid,long rpid) throws IOException {
        String result = deletePage(type, oid, rpid);
        int code = JSONObject.parseObject(result).getInteger("code");
        return code == 0;
    }

    /**
     * 删除评论
     * @param type 评论区类型代码
     * @param oid 目标评论区id
     * @param rpid 目标评论rpid
     * @return
     */
    public String deletePage(CommentType type, String oid,long rpid) throws IOException {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("oid", oid);
        params.put("rpid", String.valueOf(rpid));
        params.put("type", type.toString());
        params.put("csrf",cookie.getCsrf());

        HttpClientResult result = HttpClientUtils.doPost(URL_like,cookie.toHeaderCookie(),params);
        return result.getContent();
    }
}
