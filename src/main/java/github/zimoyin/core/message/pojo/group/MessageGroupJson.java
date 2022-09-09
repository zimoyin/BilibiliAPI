/**
  * Copyright 2022 json.cn 
  */
package github.zimoyin.core.message.pojo.group;

import java.util.List;

/**
 * Auto-generated: 2022-08-17 10:52:23
 *
 * @author json.cn (i@json.cn)
 * @website http://www.json.cn/java2pojo/
 */
public class MessageGroupJson {

    private int code;
    private String msg;
    private String message;
    private int ttl;
    private Data data;



    @lombok.Data
    public class Data {

        /**
         * 会话信息
         */
        private List<Session_list> session_list;
        private int has_more;
        private boolean anti_disturb_cleaning;
        private int is_address_list_empty;
        private boolean show_level;
    }

}