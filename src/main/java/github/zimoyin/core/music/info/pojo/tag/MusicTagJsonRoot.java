/**
  * Copyright 2022 json.cn 
  */
package github.zimoyin.core.music.info.pojo.tag;
import java.util.List;

/**
 * Auto-generated: 2022-08-30 10:46:59
 *
 * @author json.cn (i@json.cn)
 * @website http://www.json.cn/java2pojo/
 */
@lombok.Data
public class MusicTagJsonRoot {

    private int code;
    private String msg;
    private List<Data> data;


    @lombok.Data
    public static class Data{
        /**
         * 类型
         */
        private String type;
        /**
         * 子分区类型id
         */
        private int subtype;
        private int key;
        /**
         * TAG名
         */
        private String info;
    }
}