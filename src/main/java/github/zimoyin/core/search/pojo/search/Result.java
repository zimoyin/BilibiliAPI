/**
 * Copyright 2022 json.cn
 */
package github.zimoyin.core.search.pojo.search;

import github.zimoyin.core.search.pojo.search.result.*;
import github.zimoyin.core.search.pojo.search.result.live.LiveRoom;
import github.zimoyin.core.search.pojo.search.result.live.LiveUser;
import github.zimoyin.core.search.pojo.search.result.user.ResultUser;
import lombok.Data;

import java.util.List;

/**
 * 下面注释每一列对应的对象类型：
 */
@Data
public class Result  {
    List<ResultVideo> videoType = null;
    List<ResultMovie> movieType = null;
    List<LiveRoom> liveType = null;
    List<LiveUser> liveUserType = null;
    List<ResultColumn> columnType = null;
    List<ResultTopic> topicType = null;
    List<ResultUser> userType = null;
    List<ResultPhoto> photoType = null;
}