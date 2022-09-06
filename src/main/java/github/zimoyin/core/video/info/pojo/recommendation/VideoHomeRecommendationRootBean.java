/**
  * Copyright 2022 json.cn 
  */
package github.zimoyin.core.video.info.pojo.recommendation;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import github.zimoyin.core.video.info.pojo.info.Data;

import java.util.List;

/**
 * Auto-generated: 2022-07-18 12:41:36
 *
 * @author json.cn (i@json.cn)
 * @website http://www.json.cn/java2pojo/
 */
@lombok.Data
public class VideoHomeRecommendationRootBean {

    private int code;
    private String message;
    private Data0 data;

    public List<Data> getData() {
        return data.getItem();
    }
}
@lombok.Data
class Data0{
    private List<Data> item;
}