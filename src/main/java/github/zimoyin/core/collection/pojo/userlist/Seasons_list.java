/**
  * Copyright 2022 json.cn 
  */
package github.zimoyin.core.collection.pojo.userlist;
import github.zimoyin.core.collection.pojo.collection.Archives;
import github.zimoyin.core.collection.pojo.collection.Meta;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Auto-generated: 2022-08-29 22:45:32
 *
 * @author json.cn (i@json.cn)
 * @website http://www.json.cn/java2pojo/
 */
@Data
public class Seasons_list extends CInfo{
    /**
     * 稿件
     */
    private List<Archives> archives;
    /**
     * 合集信息
     */
    private Meta meta;
    /**
     * 合集内所有稿件的av id
     */
    private List<Long> recent_aids;

    public Seasons_list() {
    }

    public Seasons_list(List<Archives> archives, Meta meta, List<Long> recent_aids) {
        super(archives, meta, recent_aids);
    }
}