/**
  * Copyright 2022 json.cn 
  */
package github.zimoyin.core.comment.operation.pojo.publish;

import github.zimoyin.core.comment.pojo.table.Emote;
import github.zimoyin.core.comment.pojo.table.Replies;

/**
 * Auto-generated: 2022-08-06 20:22:0
 *
 * @author json.cn (i@json.cn)
 * @website http://www.json.cn/java2pojo/
 */
@lombok.Data
public class Data {

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