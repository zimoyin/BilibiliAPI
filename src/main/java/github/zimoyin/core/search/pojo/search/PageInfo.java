package github.zimoyin.core.search.pojo.search;

import github.zimoyin.core.search.pojo.sall.Live_room;
import github.zimoyin.core.search.pojo.sall.Pgc;
import lombok.Data;

@Data
public class PageInfo {
    /**
     * 	直播间信息
     */
    private Live_room live_room;
    /**
     * 主播信息
     */
    private Live_room live_user;
}
