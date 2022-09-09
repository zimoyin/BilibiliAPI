/**
  * Copyright 2022 json.cn 
  */
package github.zimoyin.core.live.pojo.message;
import com.alibaba.fastjson.JSONObject;
import lombok.Data;

import java.util.List;

/**
 * Auto-generated: 2022-08-02 16:4:52
 *
 * @author json.cn (i@json.cn)
 * @website http://www.json.cn/java2pojo/
 */
@Data
public class LiveMessageJsonRootBean {
    /**
     * 命令类型
     */
    private String cmd;
    /**
     * 弹幕信息
     */
    private List<String> info;
    /**
     * 其他信息
     */
    private MessageData data;
}