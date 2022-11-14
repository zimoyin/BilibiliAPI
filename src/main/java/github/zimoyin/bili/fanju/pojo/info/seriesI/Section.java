/**
  * Copyright 2022 json.cn 
  */
package github.zimoyin.bili.fanju.pojo.info.seriesI;
import lombok.Data;

import java.util.List;

/**
 * Auto-generated: 2022-07-31 20:29:51
 *
 * @author json.cn (i@json.cn)
 * @website http://www.json.cn/java2pojo/
 */
@Data
public class Section {

    private int attr;
    /**
     * 板块内容
     */
    private int episode_id;
    private List<String> episode_ids;
    private List<Episodes> episodes;
    /**
     * 疑似：板块id
     */
    private long id;
    /**
     * 板块标题
     */
    private String title;
    private int type;


}