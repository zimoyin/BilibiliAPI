/**
  * Copyright 2022 json.cn 
  */
package github.zimoyin.bili.live.pojo.barrage;

import java.util.List;

/**
 * Auto-generated: 2022-08-04 18:55:21
 *
 * @author json.cn (i@json.cn)
 * @website http://www.json.cn/java2pojo/
 */
@lombok.Data
public class LiveBarrageJsonRootBean {

    private int code;
    private Data data;
    private String message;
    private String msg;


    @lombok.Data
    public static class Data{
        /**
         * 为当前直播间内管理员最新的10条弹幕消息
         */
        private List<Barrage> admin;
        /**
         * 则是普通用户的10条弹幕信息
         */
        private List<Barrage> room;
    }
}