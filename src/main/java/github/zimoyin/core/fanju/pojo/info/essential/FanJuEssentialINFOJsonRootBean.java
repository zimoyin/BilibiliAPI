/**
  * Copyright 2022 json.cn 
  */
package github.zimoyin.core.fanju.pojo.info.essential;

import lombok.Data;

/**
 * Auto-generated: 2022-07-31 20:6:22
 *
 * @author json.cn (i@json.cn)
 * @website http://www.json.cn/java2pojo/
 */
@Data
public class FanJuEssentialINFOJsonRootBean {

    private int code;
    /**
     * 错误信息
     * 如果成功则为：success
     */
    private String message;
    /**
     * 信息本体
     */
    private Result result;
}