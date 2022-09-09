/**
  * Copyright 2022 json.cn 
  */
package github.zimoyin.core.user.pojo.works;

/**
 * Auto-generated: 2022-07-19 13:12:49
 *
 * @author json.cn (i@json.cn)
 * @website http://www.json.cn/java2pojo/
 */
@lombok.Data
public class UserWorksRootBean {

    private int code;
    private String message;
    private int ttl;
    private Data data;


    @lombok.Data
    public class Data {

        /**
         * 作品
         */
        private ListWorks list;
        /**
         * 页数坐标
         */
        private Page page;
        private Episodic_button episodic_button;

    }
}