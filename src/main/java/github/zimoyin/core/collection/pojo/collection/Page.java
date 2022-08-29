/**
  * Copyright 2022 json.cn 
  */
package github.zimoyin.core.collection.pojo.collection;

import lombok.Data;

/**
 * Auto-generated: 2022-08-29 22:30:20
 *
 * @author json.cn (i@json.cn)
 * @website http://www.json.cn/java2pojo/
 */
@Data
public class Page {
    /**
     * 当前页数
     */
    private int page_num;
    /**
     * 允许返回的数据条数
     */
    private int page_size;
    /**
     * 总共返回的数据条数
     */
    private int total;

}