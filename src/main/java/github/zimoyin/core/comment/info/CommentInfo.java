package github.zimoyin.core.comment.info;

import com.alibaba.fastjson.JSONObject;
import github.zimoyin.core.Incompleteness;
import github.zimoyin.core.comment.enums.CommentType;
import github.zimoyin.core.comment.pojo.table.Replies;
import github.zimoyin.core.utils.net.httpclient.HttpClientResult;
import github.zimoyin.core.utils.net.httpclient.HttpClientUtils;

import java.io.IOException;
import java.util.HashMap;

/**
 * 获取指定评论信息
 */
@Incompleteness
public class CommentInfo {
    private static final String URL = "http://api.bilibili.com/x/v2/reply/info";

    /**
     * 获取指定评论信息
     *
     * @param rpid 目标评论 rpid
     * @return
     * @throws IOException
     */
    public Replies getPojo(String rpid) throws IOException {
        String page = getPage(rpid);
        JSONObject jsonObject = JSONObject.parseObject(page);
        String dataPage = jsonObject.get("data").toString();

        Replies replies = JSONObject.parseObject(dataPage, Replies.class);
        return replies;
    }

    /**
     * 获取指定评论信息
     *
     * @param type 评论区类型
     * @param oid  评论区的oid，每个type对影一个oid，oid具体是什么见该type的注释中“：”后面的内容
     * @param rpid 目标评论 rpid
     * @return
     * @throws IOException
     */
    public Replies getPojo(CommentType type, String oid, String rpid) throws IOException {
        String page = getPage(type, oid, rpid);
        JSONObject jsonObject = JSONObject.parseObject(page);
        String dataPage = jsonObject.get("data").toString();

        Replies replies = JSONObject.parseObject(dataPage, Replies.class);
        return replies;
    }


    /**
     * 获取指定评论信息
     *
     * @param rpid 目标评论 rpid
     * @return
     * @throws IOException
     */
    public String getPage(String rpid) throws IOException {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("rpid", rpid);

        HttpClientResult result = HttpClientUtils.doGet(URL, params);
        return result.getContent();
    }

    /**
     * 获取指定评论信息
     *
     * @param type 评论区类型
     * @param oid  评论区的oid，每个type对影一个oid，oid具体是什么见该type的注释中“：”后面的内容
     * @param rpid 目标评论 rpid
     * @return
     * @throws IOException
     */
    public String getPage(CommentType type, String oid, String rpid) throws IOException {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("type", type.toString());
        params.put("oid", oid);
        params.put("rpid", rpid);

        HttpClientResult result = HttpClientUtils.doGet(URL, params);
        return result.getContent();
    }
}
