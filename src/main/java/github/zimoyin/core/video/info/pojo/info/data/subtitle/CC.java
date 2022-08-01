/**
 * Copyright 2022 json.cn
 */
package github.zimoyin.core.video.info.pojo.info.data.subtitle;

import lombok.Data;

import java.net.URL;

/**
 * Auto-generated: 2022-07-16 13:44:12
 *
 * @author json.cn (i@json.cn)
 * @website http://www.json.cn/java2pojo/
 * CC字幕具体信息
 */
@Data
public class CC {

    /**
     * 字幕id
     */
    private long id;
    /**
     * 字幕语言
     */
    private String lan;
    /**
     * 字幕语言名称
     */
    private String lan_doc;
    /**
     * 是否锁定
     */
    private boolean is_lock;
    /**
     * json格式字幕文件url
     */
    private URL subtitle_url;
    /**
     * 作用尚不明确
     */
    private int type;
    /**
     * 作用尚不明确：推测上传者ID
     */
    private String id_str;
    /**
     * 作用尚不明确：推测AI类型
     */
    private int ai_type;
    /**
     * 作用尚不明确：推测AI状态
     */
    private int ai_status;
    /**
     * 字幕上传者信息
     */
    private Author author;
    /**
     * 字幕上传者mid
     */
    private long author_mid;
}