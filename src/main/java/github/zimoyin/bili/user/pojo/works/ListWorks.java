/**
  * Copyright 2022 json.cn
  */
package github.zimoyin.bili.user.pojo.works;
import lombok.Data;

import java.util.HashMap;
import java.util.List;

/**
 * Auto-generated: 2022-07-19 13:12:49
 *
 * @author json.cn (i@json.cn)
 * @website http://www.json.cn/java2pojo/
 */
@Data
public class ListWorks {
    /**
     * 分区
     */
    private HashMap<Integer,Tlist> tlist;
    /**
     * 作品列表
     */
    private List<Vlist> vlist;
}