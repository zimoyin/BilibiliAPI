package github.zimoyin.core.search.pojo.search.result;

import github.zimoyin.core.search.pojo.search.result.user.Official_verify;
import github.zimoyin.core.search.pojo.search.result.user.Res;
import lombok.Data;

import java.util.List;

@Data
public class ResultPhoto {
    /**
     * 结果类型	固定为 photo
     */
    private String type;
    /**
     * 用户mid
     */
    private long mid;
    /**
     * 用户昵称
     */
    private String uname;
    /**
     * 	收藏数
     */
    private int like;
    /**
     * 观看次数
     */
    private int view;
    /**
     * 相簿id
     */
    private int id;
    /**
     * 结果排序量化值
     */
    private int rank_score;
    /**
     * 相簿标题
     */
    private String title;
    /**
     * 相簿封面url
     */
    private String cover;
    /**
     * 搜索结果排名值
     */
    private int rank_offset;
    /**
     * 关键字匹配类型
     */
    private List<String> hit_columns;

}
