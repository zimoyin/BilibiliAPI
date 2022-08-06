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
public class Control {

    /**
     * 是否禁止新增评论
     * 用户涉及合约争议，
     * 锁定该用户所有稿件、动态的评论区，
     * 不允许新增评论，
     * root_input_text和child_input_text值为“当前评论区不可新增评论”
     */
    private boolean input_disable;
    /**
     * 评论框文字
     */
    private String root_input_text;
    /**
     * 	评论框文字
     */
    private String child_input_text;
    private String giveup_input_text;
    /**
     * 答题页面链接文字
     */
    private String answer_guide_text;
    /**
     * 答题页面图标 url
     */
    private String answer_guide_icon_url;
    /**
     * 答题页面 ios url
     */
    private String answer_guide_ios_url;
    /**
     * 答题页面安卓 url
     */
    private String answer_guide_android_url;
    /**
     * 空评论区文字
     */
    private String bg_text;
    private int show_type;
    private String show_text;
    /**
     * 评论是否筛选后可见
     * false：无需筛选
     * true：需要筛选
     */
    private boolean web_selection;
    private boolean disable_jump_emote;

}