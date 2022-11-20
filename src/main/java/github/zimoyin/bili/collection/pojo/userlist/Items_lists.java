/**
 * Copyright 2022 json.cn
 */
package github.zimoyin.bili.collection.pojo.userlist;

import github.zimoyin.bili.collection.pojo.collection.Page;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Auto-generated: 2022-08-29 22:45:32
 *
 * @author json.cn (i@json.cn)
 * @website http://www.json.cn/java2pojo/
 */
@Data
public class Items_lists {
    /**
     * 此次查询用户的合集信息
     */
    private Page page;
    /**
     * 询用户的合集列表
     */
    private List<Seasons_list> seasons_list;
    /**
     * 询用户的合集列表
     */
    private List<Series_list> series_list;

    /**
     * 询用户的合集列表 推荐
     */
    private List<CInfo> list;

    /**
     * 询用户的合集列表
     * @return
     */
    public List<CInfo> getList() {
        ArrayList<CInfo> list = new ArrayList<CInfo>();
        list.addAll(seasons_list);
        list.addAll(series_list);
        return list;
    }
}