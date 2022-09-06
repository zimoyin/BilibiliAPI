/**
  * Copyright 2022 json.cn 
  */
package github.zimoyin.core.music.list.pojo.list.my;
import java.util.List;

/**
 * Auto-generated: 2022-09-03 17:4:36
 *
 * @author json.cn (i@json.cn)
 * @website http://www.json.cn/java2pojo/
 */
@lombok.Data
public class Data {

    /**
     * 当前页码
     */
    private int curPage;
    /**
     * 总计页数
     */
    private int pageCount;
    /**
     * 总计收藏夹数
     */
    private int totalSize;
    /**
     * 当前页面项数
     */
    private int pageSize;
    /**
     * 歌单列表
     */
    private List<MyList> data;
}