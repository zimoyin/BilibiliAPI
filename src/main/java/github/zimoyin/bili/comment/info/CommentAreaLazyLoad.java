package github.zimoyin.bili.comment.info;

import com.alibaba.fastjson.JSONObject;
import github.zimoyin.bili.comment.enums.CommentType;
import github.zimoyin.bili.comment.enums.Mode;
import github.zimoyin.bili.comment.pojo.area.lazy.CommentAreaLazyLoadJsonRoot;
import github.zimoyin.bili.cookie.Cookie;
import github.zimoyin.bili.utils.IDConvert;
import github.zimoyin.bili.utils.net.httpclient.HttpClientResult;
import github.zimoyin.bili.utils.net.httpclient.HttpClientUtils;
import lombok.Data;

import java.io.IOException;
import java.util.HashMap;

/**
 * 评论区懒加载
 */
@Data
public class CommentAreaLazyLoad {
    private static final String URL = "http://api.bilibili.com/x/v2/reply/main";
    private Cookie cookie;

    /**
     * 视频评论区懒加载
     *
     * @param bv 视频bv
     * @return
     * @throws IOException
     */
    public CommentAreaLazyLoadJsonRoot getVideoCommentPojo(String bv) throws IOException {
        return getVideoCommentPojo(bv, 0);
    }

    /**
     * 视频评论区懒加载
     *
     * @param bv   视频bv
     * @param next 评论页选择(默认为 0)
     *             按热度时：热度顺序页码（0 为第一页）
     *             按时间时：时间倒序楼层号
     * @return
     * @throws IOException
     */
    public CommentAreaLazyLoadJsonRoot getVideoCommentPojo(String bv, int next) throws IOException {
        return getVideoCommentPojo(bv, Mode.HOT, next);
    }

    /**
     * 视频评论区懒加载
     *
     * @param bv   视频bv
     * @param mode 排序方式
     * @param next 评论页选择(默认为 0)
     *             按热度时：热度顺序页码（0 为第一页）
     *             按时间时：时间倒序楼层号
     * @return
     * @throws IOException
     */
    public CommentAreaLazyLoadJsonRoot getVideoCommentPojo(String bv, Mode mode, int next) throws IOException {
        String avid = String.valueOf(IDConvert.BvToAvNumber(bv));
        return getPojo(CommentType.AV_ID, avid, mode, next, 30);
    }


    /**
     * 获取评论区内容
     *
     * @param type 评论区类型
     * @param oid  评论区的oid，每个type对影一个oid，oid具体是什么见该type的注释中“：”后面的内容
     * @param mode 排序方式
     * @param next 评论页选择(默认为 0)
     *             按热度时：热度顺序页码（0 为第一页）
     *             按时间时：时间倒序楼层号
     * @param ps   每页项数(定义域：1-30)
     * @return
     * @throws IOException
     */
    public CommentAreaLazyLoadJsonRoot getPojo(CommentType type, String oid, Mode mode, int next, int ps) throws IOException {
        String page = getPage(type, oid, mode, next, ps);
        CommentAreaLazyLoadJsonRoot json = JSONObject.parseObject(page, CommentAreaLazyLoadJsonRoot.class);
        return json;
    }

    /**
     * 视频评论区懒加载
     *
     * @param bv   视频的bv号
     * @param next 评论页选择(默认为 0)
     *             按热度时：热度顺序页码（0 为第一页）
     *             按时间时：时间倒序楼层号
     * @return
     * @throws IOException
     */
    public String getVideoCommentPage(String bv, int next) throws IOException {
        String avid = String.valueOf(IDConvert.BvToAvNumber(bv));
        return getPage(CommentType.AV_ID, avid, Mode.HOT, next, 30);
    }

    /**
     * 视频评论区懒加载
     *
     * @param bv   视频bv
     * @param mode 排序方式
     * @param next 评论页选择(默认为 0)
     *             按热度时：热度顺序页码（0 为第一页）
     *             按时间时：时间倒序楼层号
     * @return
     * @throws IOException
     */
    public String getVideoCommentPage(String bv, Mode mode, int next) throws IOException {
        String avid = String.valueOf(IDConvert.BvToAvNumber(bv));
        return getPage(CommentType.AV_ID, avid, mode, next, 30);
    }

    /**
     * 评论区懒加载
     *
     * @param type 评论区类型
     * @param oid  评论区的oid，每个type对影一个oid，oid具体是什么见该type的注释中“：”后面的内容
     * @param mode 排序方式
     * @param next "评论页选择"(默认为 0)
     *             按热度时：热度顺序页码（0 为第一页）
     *             按时间时：时间倒序楼层号
     * @param ps   每页项数(定义域：1-30)
     * @return 原数据
     * @throws IOException
     * @ps 每页项数
     */
    public String getPage(CommentType type, String oid, Mode mode, int next, int ps) throws IOException {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("type", type.toString());
        params.put("oid", oid);
        params.put("mode", mode.toString());
        params.put("next", String.valueOf(next));
        params.put("ps", String.valueOf(ps));

        HttpClientResult result = HttpClientUtils.doGet(URL, cookie != null ? cookie.toHeaderCookie() : null, params);
        return result.getContent();
    }
}
