/**
  * Copyright 2022 json.cn 
  */
package github.zimoyin.bili.video.info.pojo.interact;


import java.util.List;

/**
 * Auto-generated: 2022-07-20 20:44:49
 *
 * @author json.cn (i@json.cn)
 * @website http://www.json.cn/java2pojo/
 */
@lombok.Data
public class InteractVideoRootBean {

    private int code;
    private String message;
    private int ttl;
    private Data data;


    @lombok.Data
    public static class Data{

        /**
         * 视频模块（分P）标题
         */
        private String title;
        /**
         * 当前模块id(同时作为节点的ID组成之一，ID组成由模块id与节点名称的散列值作为id)
         */
        private long edge_id;
        /**
         * 进度回溯信息 :未登录仅有起始模块
         */
        private List<Story_list> story_list;
        /**
         * 预加载的分P
         */
        private Edges edges;

        /**
         * 预加载的分P
         */
        private Preload preload;
        /**
         * 变量列表
         */

        private List<Hidden_vars> hidden_vars;
        /**
         * 是否为结束模块
         * 0：当前模块为普通模块
         * 1：当前模块为结束模块
         */
        private int is_leaf;
        /**
         * 禁止记录选择
         * 1：禁止
         * 非禁止时无此项
         */
        private int no_tutorial;
        /**
         * 禁止进度回溯
         * 1：禁止
         * 非禁止时无此项
         */
        private int no_backtracking;
        /**
         * 禁止结尾评分	1：禁止
         * 非禁止时无此项
         */
        private int no_evaluation;
    }
}