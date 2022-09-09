/**
  * Copyright 2022 json.cn 
  */
package github.zimoyin.core.live.pojo.area;
import java.util.List;

/**
 * Auto-generated: 2022-08-03 17:26:38
 *
 * @author json.cn (i@json.cn)
 * @website http://www.json.cn/java2pojo/
 */
@lombok.Data
public class AreaListJsonRoot {

    private int code;
    private String msg;
    private String message;
    /**
     * 父分区列表
     */
    private List<Data> data;

    public class Data {

        /**
         * 父分区id
         */
        private int id;
        /**
         * 父分区名
         */
        private String name;
        /**
         * 子分区列表
         */
        private List<Subarea> list;
    }
}