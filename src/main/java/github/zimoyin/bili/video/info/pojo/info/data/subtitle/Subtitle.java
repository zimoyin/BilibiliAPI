/**
  * Copyright 2022 json.cn 
  */
package github.zimoyin.bili.video.info.pojo.info.data.subtitle;
import lombok.Data;

import java.util.List;

/**
 * Auto-generated: 2022-07-16 13:2:38
 *
 * @author json.cn (i@json.cn)
 * @website http://www.json.cn/java2pojo/
 * 视频CC字幕信息
 */
@Data
public class Subtitle {

    /**
     * 是否允许提交字幕
     */
    private boolean allow_submit;
    /**
     * 字幕列表
     */
    private List<CC> list;

}