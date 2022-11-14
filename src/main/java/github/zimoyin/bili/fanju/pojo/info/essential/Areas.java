/**
  * Copyright 2022 json.cn 
  */
package github.zimoyin.bili.fanju.pojo.info.essential;

import lombok.Data;

/**
 * Auto-generated: 2022-07-31 20:6:22
 *
 * @author json.cn (i@json.cn)
 * @website http://www.json.cn/java2pojo/
 */
@Data
public class Areas {

    /**
     * 所属地区编号
     * 1：中国大陆
     * 2：日本
     * 3：美国
     * 4：英国
     * 6：中国香港
     * 8：韩国
     * 9：法国
     * 10：泰国
     * 13：西班牙
     * 15：德国
     * 35：意大利
     * 39：新西兰
     * 43：澳大利亚
     */
    private int id;
    /**
     * 所属地区名称
     */
    private String name;

}