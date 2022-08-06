package github.zimoyin.core.comment.operation;

import com.alibaba.fastjson.JSONObject;
import github.zimoyin.core.comment.enums.CommentType;
import github.zimoyin.core.comment.operation.pojo.publish.PublishJsonRoot;
import github.zimoyin.core.cookie.Cookie;
import github.zimoyin.core.cookie.GlobalCookie;
import github.zimoyin.core.exception.CookieNotFoundException;
import github.zimoyin.core.utils.IDConvert;
import github.zimoyin.core.utils.net.httpclient.HttpClientResult;
import github.zimoyin.core.utils.net.httpclient.HttpClientUtils;

import java.io.IOException;
import java.util.HashMap;

/**
 * 发送弹幕
 */
public class CommentPublish extends CommentOperation {


    public CommentPublish(Cookie cookie) {
        super(cookie);
    }

    /**
     * 发送视频的评论
     * @param bv bvid
     * @param message  发送评论内容：最大1000字符，表情使用表情转义符（被英文中括号包裹，如：[泠鸢yousa_awsl]）
     * @return 响应 pojo
     */
    public boolean publishVideoComment(String bv,String message) throws IOException {
        PublishJsonRoot jsonRoot = publishPojo(CommentType.AV_ID, IDConvert.BvToAvNumber(bv), -1, -1, message, 1);
        if (jsonRoot.getCode() == 0) {
            return true;
        }
        return false;
    }

    /**
     * 发送视频的评论
     * @param bv bvid
     * @param root     根评论rpid，非必要可以传入-1（注意：二级评论以上必须使用）
     * @param parent   父评论rpid，非必要可以传入-1（注意：二级评论以上必须使用）
     * @param message  发送评论内容：最大1000字符，表情使用表情转义符（被英文中括号包裹，如：[泠鸢yousa_awsl]）
     * @return 响应 pojo
     */
    public boolean publishVideoComment(String bv,long root, long parent, String message) throws IOException {
        PublishJsonRoot jsonRoot = publishPojo(CommentType.AV_ID, IDConvert.BvToAvNumber(bv), root, parent, message, 1);
        if (jsonRoot.getCode() == 0) {
            return true;
        }
        return false;
    }


    /**
     * 发送评论
     *
     * @param type     评论区类型代码
     * @param oid      目标评论区id
     * @param root     根评论rpid，非必要可以传入-1（注意：二级评论以上必须使用）
     * @param parent   父评论rpid，非必要可以传入-1（注意：二级评论以上必须使用）
     * @param message  发送评论内容：最大1000字符，表情使用表情转义符（被英文中括号包裹，如：[泠鸢yousa_awsl]）
     * @param platform 发送平台标识，非必要，默认为web
     *                 1：web端
     *                 2：安卓客户端
     *                 3：ios客户端
     *                 4：wp客户端
     *                 默认为1
     * @return 响应 pojo
     */
    public PublishJsonRoot publishPojo(CommentType type, long oid,
                                       long root, long parent,
                                       String message,
                                       int platform)
            throws IOException {
        String page = publishPage(type, oid, root, parent, message, platform);
        PublishJsonRoot jsonRoot = JSONObject.parseObject(page, PublishJsonRoot.class);
        return jsonRoot;
    }

    /**
     * 发送评论
     *
     * @param type     评论区类型代码
     * @param oid      目标评论区id
     * @param root     根评论rpid，非必要可以传入-1（注意：二级评论以上必须使用）
     * @param parent   父评论rpid，非必要可以传入-1（注意：二级评论以上必须使用）
     * @param message  发送评论内容：最大1000字符，表情使用表情转义符（被英文中括号包裹，如：[泠鸢yousa_awsl]）
     * @param platform 发送平台标识，非必要，默认为web
     *                 1：web端
     *                 2：安卓客户端
     *                 3：ios客户端
     *                 4：wp客户端
     *                 默认为1
     * @return 响应 text
     */
    public String publishPage(CommentType type, long oid,
                              long root, long parent,
                              String message,
                              int platform)
            throws IOException {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("type", type.toString());
        params.put("oid", String.valueOf(oid));
        if (root > -1) params.put("root", String.valueOf(root));
        if (parent > -1) params.put("parent", String.valueOf(parent));
        params.put("message", message);
        if (platform != 0) params.put("plat", String.valueOf(platform));
        params.put("csrf", cookie.getCsrf());


        HttpClientResult result = HttpClientUtils.doPost(URL_publish, cookie.toHeaderCookie(), params);
        return result.getContent();
    }
}
