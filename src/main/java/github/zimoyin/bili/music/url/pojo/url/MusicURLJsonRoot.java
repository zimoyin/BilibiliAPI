/**
  * Copyright 2022 json.cn 
  */
package github.zimoyin.bili.music.url.pojo.url;

import java.util.List;

/**
 * Auto-generated: 2022-08-30 11:12:18
 *
 * @author json.cn (i@json.cn)
 * @website http://www.json.cn/java2pojo/
 */
@lombok.Data
public class MusicURLJsonRoot {

    private int code;
    private String msg;
    private Data data;



    @lombok.Data
    public static class Data{
        /**
         * 音频auid
         */
        private int sid;
        /**
         * 音质标识
         * -1：试听片段（192K）
         * 0：128K
         * 1：192K
         * 2：320K
         * 3：FLAC
         */
        private int type;
        private String info;
        /**
         * 有效时长
         * 单位为秒
         * 一般为3h
         */
        private int timeout;
        /**
         * 文件大小	单位为字节
         * 当type为-1时size为0
         */
        private long size;
        /**
         * 	音频流url
         */
        private List<String> cdns;
        /**
         * 音质列表
         */
        private List<Qualities> qualities;
        /**
         * 音频标题
         */
        private String title;
        /**
         * 音频封面url
         */
        private String cover;

    }
}