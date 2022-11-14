/**
  * Copyright 2022 json.cn 
  */
package github.zimoyin.bili.comment.operation.pojo.publish;

import github.zimoyin.bili.comment.pojo.table.Emote;
import github.zimoyin.bili.comment.pojo.table.Replies;

/**
 * Auto-generated: 2022-08-06 20:22:0
 *
 * @author json.cn (i@json.cn)
 * @website http://www.json.cn/java2pojo/
 */
@lombok.Data
public class PublishJsonRoot {

    /**
     * 0：成功
     * -101：账号未登录
     * -102：账号被封停
     * -111：csrf校验失败
     * -400：请求错误
     * -404：无此项
     * -509：请求过于频繁
     * 12001：已经存在评论主题
     * 12002：评论区已关闭
     * 12003：禁止回复
     * 12006：没有该评论
     * 12009：评论主体的type不合法
     * 12015：需要评论验证码
     * 12016：评论内容包含敏感信息
     * 12025：评论字数过多
     * 12035：该账号被UP主列入评论黑名单
     * 12051：重复评论，请勿刷屏
     * 12052：评论区已关闭
     */
    private int code;
    private String message;
    private int ttl;
    private Data data;


    @lombok.Data
    public static class Data{

        /**
         * 评论是否需要验证码
         */
        private boolean need_captcha;
        private String url;
        private boolean need_captcha_v2;
        private String url_v2;
        private int success_action;
        /**
         * 状态文字
         */
        private String success_toast;
        private String success_animation;
        /**
         * 评论rpid
         */
        private long rpid;
        /**
         * 评论rpid
         */
        private String rpid_str;
        /**
         * 回复对方rpid
         * 若为一级评论则为0
         * 若为二级评论则为该评论id
         * 大于二级评论为上一级评论id
         */
        private int dialog;
        /**
         * 回复对方rpid
         * 若为一级评论则为0
         * 若为二级评论则为该评论id
         * 大于二级评论为上一级评论id
         */
        private String dialog_str;
        /**
         * 根评论rpid
         * 若为一级评论则为0
         * 大于一级评论则为根评论id
         */
        private int root;
        /**
         * 根评论rpid
         * 若为一级评论则为0
         * 大于一级评论则为根评论id
         */
        private String root_str;
        /**
         * 回复父评论rpid
         * 若为一级评论则为0
         * 若为二级评论则为根评论id
         * 大于二级评论为上一级评论id
         */
        private int parent;
        /**
         * 回复父评论rpid
         * 若为一级评论则为0
         * 若为二级评论则为根评论id
         * 大于二级评论为上一级评论id
         */
        private String parent_str;
        private Replies reply;
        /**
         * 表情转义符信息
         */
        private Emote emote;
    }
}