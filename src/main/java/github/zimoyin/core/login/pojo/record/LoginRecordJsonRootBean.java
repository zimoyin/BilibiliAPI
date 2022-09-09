/**
  * Copyright 2022 json.cn 
  */
package github.zimoyin.core.login.pojo.record;

import java.util.Date;

/**
 * Auto-generated: 2022-08-04 21:34:28
 *
 * @author json.cn (i@json.cn)
 * @website http://www.json.cn/java2pojo/
 */
@lombok.Data
public class LoginRecordJsonRootBean {

    private int code;
    private String message;
    private int ttl;
    private Data data;



    @lombok.Data
    public class Data {

        private long mid;
        /**
         * 登录设备	依靠操作登录接口时的UA决定
         */
        private String device_name;
        /**
         * 登录方式	根据登录接口决定
         */
        private String login_type;
        /**
         * 登录时间	YYYY-MM-DD hh:mm:ss
         */
        private Date login_time;
        /**
         * 登录位置	依靠ip决定
         */
        private String location;
        /**
         * 登录ip	部分用*打码
         */
        private String ip;
    }

}