/**
 * Copyright 2022 json.cn
 */
package github.zimoyin.core.search.pojo.search;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import github.zimoyin.core.search.pojo.sall.Cost_time;
import github.zimoyin.core.search.pojo.sall.Pageinfo;
import github.zimoyin.core.search.pojo.search.result.ResultColumn;
import github.zimoyin.core.search.pojo.search.result.ResultMovie;
import github.zimoyin.core.search.pojo.search.result.ResultPhoto;
import github.zimoyin.core.search.pojo.search.result.ResultVideo;
import github.zimoyin.core.search.pojo.search.result.live.LiveRoom;
import github.zimoyin.core.search.pojo.search.result.live.LiveUser;
import github.zimoyin.core.search.pojo.search.result.live.ResultLive;
import github.zimoyin.core.search.pojo.search.result.user.ResultUser;
import github.zimoyin.core.utils.JsonSerializeUtil;


import java.util.List;

/**
 * Auto-generated: 2022-08-19 19:12:0
 *
 * @author json.cn (i@json.cn)
 * @website http://www.json.cn/java2pojo/
 */
@lombok.Data
public class Data {

    /**
     * 搜索seid
     */
    private String seid;
    /**
     * 当前页码
     */
    private int page;
    /**
     * 每页条数
     */
    private int pagesize;
    /**
     * 总条数
     */
    private int numResults;
    /**
     * 总计分页数
     */
    private int numPages;
    private String suggest_keyword;
    /**
     * search
     */
    private String rqt_type;
    /**
     * 详细搜索用时
     */
    private Cost_time cost_time;
    private String exp_list;
    private int egg_hit;
    /**
     * 结果列表
     */
    private String result;
    private int show_column;
    private int in_black_key;
    private int in_white_key;
    /**
     * 副分页信息	只在搜索类型为直播间及主播有效
     */
    private PageInfo pageinfo;

    /**
     * 返回结果
     *
     * @return
     */
    public Result getResult() {
        boolean isObj = JsonSerializeUtil.getJsonPath().isObject(result);
        Result result0 = new Result();
        if (isObj) {
            ResultLive resultLive = JSONObject.parseObject(result, ResultLive.class);
            result0.setLiveType(resultLive.getLive_room());
            result0.setLiveUserType(resultLive.getLive_user());
        } else {
            JSONArray array = JSONObject.parseArray(result);
            if (array.size() <= 0) return result0;
            String type = array.getJSONObject(0).getString("type");
            switch (type) {
                case "video"://视频
                    result0.setVideoType(JSONObject.parseArray(result, ResultVideo.class));
                    break;
                case "media_bangumi"://番剧
                    result0.setMovieType(JSONObject.parseArray(result, ResultMovie.class));
                    break;
                case "media_ft"://影视
                    result0.setMovieType(JSONObject.parseArray(result, ResultMovie.class));
                    break;
                case "live_room"://直播间
                    result0.setLiveType(JSONObject.parseArray(result, LiveRoom.class));
                    break;
                case "live_user"://直播up
                    result0.setLiveUserType(JSONObject.parseArray(result, LiveUser.class));
                    break;
                case "article"://专栏
                    result0.setColumnType(JSONObject.parseArray(result, ResultColumn.class));
                    break;
                case "photo"://相簿
                    result0.setPhotoType(JSONObject.parseArray(result, ResultPhoto.class));
                    break;
                case "bili_user"://用户
                    result0.setUserType(JSONObject.parseArray(result, ResultUser.class));
                    break;
            }
        }

        return result0;
    }
}