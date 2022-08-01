package github.zimoyin.core.video.operation.pojo;

import lombok.Data;

/**
 * 视频被我操作后的状态
 */
@Data
public class VideoStatusJsonRoot {
    /**
     * 是否收藏
     */
    private String isStar;
    /**
     * 是否点赞
     */
    private String isLike;
    /**
     * 是否投币
     */
    private String isCoin;
}
