/**
  * Copyright 2022 json.cn 
  */
package github.zimoyin.core.comment.pojo.area.page;

import github.zimoyin.core.comment.pojo.table.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Auto-generated: 2022-08-05 21:6:5
 *
 * @author json.cn (i@json.cn)
 * @website http://www.json.cn/java2pojo/
 */
@lombok.Data
public class CommentAreaPageTurningLoadreaJRoot {

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

    /**
     * 获取评论列表
     * @return
     */
    public List<Replies> getComments(){
        return data.getReplies();
    }


    @lombok.Data
    public class Data {

        /**
         * 页信息
         */
        private Page page;
        /**
         * 评论区显示控制
         */
        private Config config;
        /**
         * 评论列表
         */
        private List<Replies> replies;
        /**
         * 置顶评论
         */
        private Upper upper;


        private String hots;

        private String top;
        /**
         * 评论区公告信息
         */
        private Notice notice;
        /**
         * 投票评论?
         */
        private int vote;
        private int blacklist;
        private int assist;
        /**
         * 评论区类型id
         */
        private int mode;
        /**
         * 评论区支持的类型id
         */
        private ArrayList<Integer> support_mode;
        /**
         * 折叠相关信息
         */
        private Folder folder;
        /**
         * 显示bvid?
         */
        private boolean show_bvid;
        /**
         * 评论区输入属性
         */
        private Control control;
    }
}