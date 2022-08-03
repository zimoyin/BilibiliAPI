/**
  * Copyright 2022 json.cn 
  */
package github.zimoyin.core.live.pojo.statev2;

import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Auto-generated: 2022-08-03 16:57:25
 *
 * @author json.cn (i@json.cn)
 * @website http://www.json.cn/java2pojo/
 */
@lombok.Data
public class LiveStateV2JsonRootBean {

    private int code;
    private String msg;
    private String message;
    /**
     * 数据
     * key:用户的mid
     * value: data
     */
    private HashMap<Long, Data> data;
}