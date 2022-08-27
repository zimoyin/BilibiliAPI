/**
  * Copyright 2022 json.cn 
  */
package github.zimoyin.core.favorites.pojo.conter;
import java.util.List;

/**
 * Auto-generated: 2022-08-26 19:59:57
 *
 * @author json.cn (i@json.cn)
 * @website http://www.json.cn/java2pojo/
 */
@lombok.Data
public class Data {

    /**
     * 收藏夹元数据
     */
    private github.zimoyin.core.favorites.pojo.info.Data  info;
    /**
     * 收藏夹内容
     */
    private List<Medias> medias;
    private boolean has_more;
}