package github.zimoyin.bili.search.pojo.search;

import github.zimoyin.bili.search.pojo.sall.Live_room;
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
