/**
 * Copyright 2022 json.cn
 */
package github.zimoyin.core.collection.pojo.userlist;

import github.zimoyin.core.collection.pojo.collection.Archives;
import github.zimoyin.core.collection.pojo.collection.Meta;
import github.zimoyin.core.collection.pojo.collection.Page;
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

    private Page page;
    /**
     * 合集 分不清他们之间的区别
     */
    private List<Seasons_list> seasons_list;
    /**
     * 视频列表 分不清他们之间的区别
     */
    private List<Series_list> series_list;

    private List<CInfo> list;

    /**
     * 获取所有的合集信息
     * @return
     */
    public List<CInfo> getList() {
        ArrayList<CInfo> list = new ArrayList<CInfo>();
        list.addAll(seasons_list);
        list.addAll(series_list);
        return list;
    }
}