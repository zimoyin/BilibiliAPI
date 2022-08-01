/**
 * Copyright 2022 json.cn
 */
package github.zimoyin.core.video.info.pojo.info.data;

import lombok.Data;

/**
 * Auto-generated: 2022-07-16 13:2:38
 *
 * @author json.cn (i@json.cn)
 * @website http://www.json.cn/java2pojo/
 * 当前分P分辨率	,部分较老视频无分辨率值
 */
@Data
public class Dimension {

    /**
     * 当前分P 宽度
     */
    private int width;
    /**
     * 当前分P 高度
     */
    private int height;
    /**
     * 是否将宽高对换	<br>
     * 0：正常<br>
     * 1：对换<br>
     */
    private int rotate;
}