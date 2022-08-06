/**
 * Copyright 2022 json.cn
 */
package github.zimoyin.core.comment.pojo.table;

import lombok.Data;

/**
 * Auto-generated: 2022-08-05 21:6:5
 *
 * @author json.cn (i@json.cn)
 * @website http://www.json.cn/java2pojo/
 */
@Data
public class Replies {

    /**
     * 评论 rpid
     */
    private long rpid;
    /**
     * 评论区对象 id
     */
    private long oid;
    /**
     * 评论区类型代码
     */
    private int type;
    /**
     * 发送者 mid
     */
    private long mid;
    /**
     * 根评论 rpid
     * 若为一级评论则为 0
     * 大于一级评论则为根评论 id
     */
    private long root;
    /**
     * 回复父评论 rpid
     * 若为一级评论则为 0
     * 若为二级评论则为根评论 rpid
     * 大于二级评论为上一级评 论 rpid
     */
    private long parent;
    /**
     * 回复对方 rpid
     * 若为一级评论则为 0
     * 若为二级评论则为该评论 rpid
     * 大于二级评论为上一级评论 rpid
     */
    private long dialog;
    /**
     * 二级评论条数
     */
    private int count;
    /**
     * 回复评论条数
     */
    private int rcount;
    /**
     * 评论楼层号
     * 注：若不支持楼层则为-1
     */
    private int floor = -1;

    private int state;
    /**
     * 是否具有粉丝标签
     * 0：无
     * 1：有
     */
    private int fansgrade;
    private int attr;
    /**
     * 评论发送时间	时间戳
     */
    private long ctime;
    /**
     * 评论rpid	字串格式
     */
    private String rpid_str;
    /**
     * 根评论rpid	字串格式
     */
    private String root_str;
    /**
     * 回复父评论rpid	字串格式
     */
    private String parent_str;
    /**
     * 评论获赞数
     */
    private int like;
    /**
     * 当前用户操作状态(需要Cookie)
     * 否则恒为 0
     * 0：无
     * 1：已点赞
     * 2：已点踩
     */
    private int action;
    /**
     * 评论发送者信息
     */
    private Member member;
    /**
     * 评论信息
     */
    private Content content;
    /**
     * 评论回复条目预览
     * 仅嵌套一层
     * 否则为 null
     */
    private String replies;
    private int assist;
    /**
     * 折叠信息
     */
    private Folder folder;
    /**
     * 评论 UP 主操作信息
     */
    private Up_action up_action;
    private boolean show_follow;
    private boolean invisible;
    /**
     * 评论提示文案信息
     */
    private Reply_control reply_control;

    /**
     * 获取发送者的名称
     * @return
     */
    public String getName(){
        return member.getUname();
    }
}