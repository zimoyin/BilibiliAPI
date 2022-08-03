/**
  * Copyright 2022 json.cn 
  */
package github.zimoyin.core.live.pojo.message.host;
import java.util.List;

/**
 * Auto-generated: 2022-08-01 18:39:29
 *
 * @author json.cn (i@json.cn)
 * @website http://www.json.cn/java2pojo/
 */
@lombok.Data
public class Data {

    private String group;
    private int business_id;
    private double refresh_row_factor;
    private int refresh_rate;
    private int max_delay;
    /**
     * 认证秘钥
     */
    private String token;
    /**
     * 信息流服务器节点列表
     */
    private List<Host> host_list;

}