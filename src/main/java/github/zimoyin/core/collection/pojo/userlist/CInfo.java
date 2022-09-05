package github.zimoyin.core.collection.pojo.userlist;

import github.zimoyin.core.collection.pojo.collection.Archives;
import github.zimoyin.core.collection.pojo.collection.Meta;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class CInfo {
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
}
