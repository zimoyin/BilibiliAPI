package github.zimoyin.bili.favorites.info;

import com.alibaba.fastjson.JSONObject;
import github.zimoyin.bili.cookie.Cookie;
import github.zimoyin.bili.favorites.pojo.userfavorites.FavList;
import github.zimoyin.bili.favorites.pojo.userfavorites.UserFavoritesJsonRoot;
import github.zimoyin.bili.utils.net.httpclient.HttpClientResult;
import github.zimoyin.bili.utils.net.httpclient.HttpClientUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * 获取指定用户创建的所有收藏夹信息
 */
public class UserFavorites {
    private static final String URL = "https://api.bilibili.com/x/v3/fav/folder/created/list-all";
    private Cookie cookie;

    public UserFavorites() {
    }

    public UserFavorites(Cookie cookie) {
        this.cookie = cookie;
    }

    /**
     * 获取指定用户创建的所有收藏夹信息,列表
     *
     * @param mid 用户的ID
     * @return
     */
    public List<FavList> getFavoritesInfoList(long mid) throws IOException {
        return getJsonPojo(mid).getData().getList();
    }

    /**
     * 获取指定用户创建的所有收藏夹信息
     *
     * @param mid 用户的ID
     * @return
     */
    public UserFavoritesJsonRoot getJsonPojo(long mid) throws IOException {
        String page = getPage(mid);
        return JSONObject.parseObject(page, UserFavoritesJsonRoot.class);
    }

    /**
     * 获取指定用户创建的所有收藏夹信息
     *
     * @param mid  用户的ID
     * @param type 目标内容属性
     * @return
     */
    public UserFavoritesJsonRoot getJsonPojo(long mid, Type type) throws IOException {
        String page = getPage(mid, type);
        return JSONObject.parseObject(page, UserFavoritesJsonRoot.class);
    }

    /**
     * 获取指定用户创建的所有收藏夹信息
     *
     * @param mid  用户的ID
     * @param type 目标内容属性
     * @param rid  目标内容id，可位视频avid
     * @return
     */
    public UserFavoritesJsonRoot getJsonPojo(long mid, Type type, long rid) throws IOException {
        String page = getPage(mid, type, rid);
        return JSONObject.parseObject(page, UserFavoritesJsonRoot.class);
    }


    /**
     * 获取指定用户创建的所有收藏夹信息
     *
     * @param mid 用户的ID
     * @return
     */
    public String getPage(long mid) throws IOException {
        return getPage(mid, null);
    }

    /**
     * 获取指定用户创建的所有收藏夹信息
     *
     * @param mid  用户的ID
     * @param type 目标内容属性
     * @return
     */
    public String getPage(long mid, Type type) throws IOException {
        return getPage(mid, type, -1L);
    }

    /**
     * 获取指定用户创建的所有收藏夹信息
     *
     * @param mid  用户的ID
     * @param type 目标内容属性
     * @param rid  目标内容id，可位视频avid
     * @return
     */
    public String getPage(long mid, Type type, long rid) throws IOException {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("up_mid", String.valueOf(mid));
        if (type != null) params.put("type", String.valueOf(type.getType()));
        else params.put("type", String.valueOf(Type.Video.getType()));
        if (rid > -1) params.put("rid", String.valueOf(rid));

        HttpClientResult httpClientResult = HttpClientUtils.doGet(URL, cookie != null ? cookie.toHeaderCookie() : null, params);
        return httpClientResult.getContent();
    }

    /**
     * 目标内容属性
     */
    static enum Type {
        All(0),
        Video(2);
        private int type;

        Type(int type) {
            this.type = type;
        }

        public int getType() {
            return type;
        }
    }
}
