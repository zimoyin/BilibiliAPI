/**
  * Copyright 2022 json.cn 
  */
package github.zimoyin.core.comment.pojo.area.lazy;

import github.zimoyin.core.comment.pojo.table.*;

import java.util.List;

/**
 * Auto-generated: 2022-08-06 17:51:5
 *
 * @author json.cn (i@json.cn)
 * @website http://www.json.cn/java2pojo/
 */
@lombok.Data
public class CommentAreaLazyLoadJsonRoot {

    /**
     * 0：成功
     * -400：请求错误
     * -404：无此项
     * 12002：评论区已关闭
     * 12009：评论主体的type不合法
     */
    private int code;
    private String message;
    private int ttl;
    private Data data;


    @lombok.Data
    public static class Data{

        /**
         * 游标信息
         */
        private Cursor cursor;
        /**
         * 对话树(非对话树API获取的没有此字段):对话楼层信息
         */
        private Dialog dialog;

        /**
         * 评论区公告信息
         */
        private Notice notice;
        /**
         * 评论列表
         */
        private List<Replies> replies;
        /**
         * 置顶信息
         */
        private Top top;
        /**
         * 置顶评论
         */
        private Replies top_replies;
        /**
         * 评论折叠信息
         */
        private Folder folder;
        private Up_selection up_selection;
        /**
         * 广告
         */
        private String cm;
        /**
         * 	广告控制
         */
        private String cm_info;
        private Effects effects;
        private int assist;
        private int blacklist;
        private int vote;
        /**
         * 评论区显示控制
         */
        private Config config;
        private String hots;
        /**
         * UP主信息
         */
        private Upper upper;
        /**
         * 评论区输入属性
         */
        private Control control;
        private int note;
        private String callbacks;
    }
}