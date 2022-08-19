/**
  * Copyright 2022 json.cn 
  */
package github.zimoyin.core.search.pojo.sall;
import lombok.Data;

import java.util.List;

/**
 * Auto-generated: 2022-08-19 16:47:20
 *
 * @author json.cn (i@json.cn)
 * @website http://www.json.cn/java2pojo/
 */
@Data
public class Result {
    /**
     * 结果类型	与result数组对应的项相同
     */
    private String result_type;
    /**
     * 搜索结果	结果为该项所对应的对象条目格式
     * @SEE https://github.com/SocialSisterYi/bilibili-API-collect/blob/master/search/search_response.md
     */
    private List<String> data;

}