package github.zimoyin.bili.favorites;


import github.zimoyin.bili.cookie.Cookie;
import github.zimoyin.bili.exception.CodeException;
import github.zimoyin.bili.favorites.info.FavoriteContentList;
import github.zimoyin.bili.favorites.info.FavoriteInfo;
import github.zimoyin.bili.favorites.info.UserFavorites;
import github.zimoyin.bili.favorites.operation.ClearInvalidFavorite;
import github.zimoyin.bili.favorites.operation.CreateFavorite;
import github.zimoyin.bili.favorites.operation.DeleteFavorite;
import github.zimoyin.bili.favorites.operation.ModifyFavorite;
import github.zimoyin.bili.favorites.pojo.conter.FavoriteContentListJsonRoot;
import github.zimoyin.bili.favorites.pojo.conter.Medias;
import github.zimoyin.bili.favorites.pojo.info.FavoriteInfoJsonRoot;
import github.zimoyin.bili.favorites.pojo.userfavorites.FavList;
import github.zimoyin.bili.favorites.pojo.userfavorites.UserFavoritesJsonRoot;
import github.zimoyin.bili.user.pojo.search.Result;
import github.zimoyin.bili.user.up.SearchUser;
import github.zimoyin.bili.video.download.download.DownloadResult;
import org.apache.logging.log4j.LogManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Future;

/**
 * 收藏夹便捷工具
 */
public class FavoriteUtil {
    private Cookie cookie;
    /**
     * 当下载完成当前视频后就停止下载，注意下载方法是阻塞式的请开启一个线程开控制它
     * 如果你想要更加自由的下载方法请自行实现
     */
    public volatile boolean isStop;
//    private Logger logger = LoggerFactory.getLogger(FavoriteUtil.class);
    private final org.apache.logging.log4j.Logger logger = LogManager.getLogger(FavoriteUtil.class);

    public FavoriteUtil() {
    }

    public FavoriteUtil(Cookie cookie) {
        this.cookie = cookie;
    }


    /**
     * 获取用户的所有收藏
     *
     * @param userName 用户的名称
     * @return
     * @throws IOException
     */
    public HashMap<FavoriteInfoJsonRoot.Data, List<Medias>> getUserFavorites(String userName) throws IOException {
//        SearchUser searchUser = new SearchUser();
//        Result search = searchUser.search(userName);
//        long mid = search.getMid();
        long mid = getUserID(userName);
        return getUserFavorites(mid);
    }

    /**
     * 获取用户的所有收藏
     *
     * @param mid 用户的id
     * @return
     * @throws IOException
     */
    public HashMap<FavoriteInfoJsonRoot.Data, List<Medias>> getUserFavorites(long mid) throws IOException {
        HashMap<FavoriteInfoJsonRoot.Data, List<Medias>> favoritesMap = new HashMap<>();
        //获取用户的所有文件夹
        UserFavorites favorites = new UserFavorites(cookie);
        UserFavoritesJsonRoot.Data data = favorites.getJsonPojo(mid).getData();
        //遍历所有收藏文件夹
        for (FavList favList : data.getList()) {
            long id = favList.getId();//完整id
            int media_count = favList.getMedia_count();//收藏的视频数量
            FavoriteContentList contentList = new FavoriteContentList(cookie);
            //遍历出文件夹中所有的收藏
            ArrayList<Medias> list0 = new ArrayList<>();
            FavoriteInfoJsonRoot.Data info = null;
            //20个收藏为一组
            double length = (double) media_count / 20;
            if (length <= 0 || length > (int) length) length++;
            for (int i = 1; i <= length; i++) {
                FavoriteContentListJsonRoot.Data list = contentList.getJsonPojo(id, i).getData();
                if (list.getInfo() != null) info = list.getInfo();
                List<Medias> medias = list.getMedias();
                list0.addAll(medias);
            }
            //保存到集合
            favoritesMap.put(info, list0);
        }
        return favoritesMap;
    }

    /**
     * 获取用户的指定的收藏
     *
     * @param userName 用户的名称
     * @param title    收藏的名称
     * @return
     * @throws IOException
     */
    public ArrayList<Medias> getUserFavorites(String userName, String title) throws IOException {
//        SearchUser searchUser = new SearchUser();
//        Result search = searchUser.search(userName);
//        long mid = search.getMid();
        long mid = getUserID(userName);
        return getUserFavorites(mid, title);
    }

    /**
     * 获取用户的指定的收藏
     *
     * @param mid   用户的id
     * @param title 收藏的名称
     * @return
     * @throws IOException
     */
    public ArrayList<Medias> getUserFavorites(long mid, String title) throws IOException {
        ArrayList<Medias> list0 = new ArrayList<>();
        //获取用户的所有文件夹
        UserFavorites favorites = new UserFavorites(cookie);
        List<FavList> lists = favorites.getJsonPojo(mid).getData().getList();
        //找到指定的文件夹并遍历出所有的收藏
        for (FavList fav : lists) {
            if (!fav.getTitle().trim().equalsIgnoreCase(title.trim())) continue;
            long id = fav.getId();//完整id
            int media_count = fav.getMedia_count();//收藏的视频数量
            FavoriteContentList contentList = new FavoriteContentList(cookie);
            //遍历出文件夹中所有的收藏
            //20个收藏为一组
            double length = (double) media_count / 20;
            if (length <= 0 || length > (int) length) length++;
            for (int i = 1; i <= length; i++) {
                FavoriteContentListJsonRoot.Data list = contentList.getJsonPojo(id, i).getData();
                List<Medias> medias = list.getMedias();
                list0.addAll(medias);
            }
        }
        return list0;
    }

    /**
     * 获取用户的指定的收藏内的内容
     *
     * @param username 收藏夹的id
     * @param title    收藏的名称
     * @return
     * @throws IOException
     */
    public ArrayList<Medias> getUserFavoriteList(String username, String title) throws IOException {
        long id = getFavoriteID(username, title);
        return getUserFavoriteList(id);
    }


    /**
     * 获取用户的指定的收藏内的内容
     *
     * @param fid 收藏夹的id
     * @return
     * @throws IOException
     */
    public ArrayList<Medias> getUserFavoriteList(long fid) throws IOException, CodeException {
        ArrayList<Medias> list0 = new ArrayList<>();
        //收藏信息
        FavoriteInfo favoriteInfo = new FavoriteInfo(cookie);
        FavoriteInfoJsonRoot info = favoriteInfo.getJsonPojo(fid);
        if (info.getCode() != 0) {
            throw new CodeException("无法获取的收藏夹信息  fid：" + fid);
        }
        int count = info.getData().getMedia_count();
        //收藏列表
        FavoriteContentList contentList = new FavoriteContentList(cookie);
        //遍历出文件夹中所有的收藏
        //20个收藏为一组
        double length = (double) count / 20;
        if (length <= 0 || length > (int) length) length++;
        for (int i = 1; i <= length; i++) {
            FavoriteContentListJsonRoot.Data list = contentList.getJsonPojo(fid, i).getData();
            List<Medias> medias = list.getMedias();
            list0.addAll(medias);
        }
        return list0;
    }

    /**
     * 获取指定收藏（名称）的ID
     *
     * @param userName 用户的id
     * @param title    收藏夹名称
     * @return
     * @throws IOException
     */
    public long getFavoriteID(String userName, String title) throws IOException {
//        SearchUser searchUser = new SearchUser(cookie);
//        Result search = searchUser.search(userName);
//        System.out.println(search);
//        long mid = search.getMid();
        long mid = getUserID(userName);
        return getFavoriteID(mid, title);
    }

    /**
     * 获取指定收藏（名称）的ID
     *
     * @param mid   用户的id
     * @param title 收藏夹名称
     * @return
     * @throws IOException
     */
    public long getFavoriteID(long mid, String title) throws IOException {
        //获取用户的所有文件夹
        UserFavorites favorites = new UserFavorites(cookie);
        List<FavList> lists = favorites.getJsonPojo(mid).getData().getList();
        //找到指定的文件夹并遍历出所有的收藏
        for (FavList fav : lists) {
            if (!fav.getTitle().trim().equalsIgnoreCase(title.trim())) continue;
            long id = fav.getId();//完整id
            return id;
        }
        return -1;
    }

    /**
     * 创建个收藏文件夹
     *
     * @param title 收藏夹标题
     * @return
     */
    public boolean create(String title) throws IOException {
        CreateFavorite favorite = new CreateFavorite(cookie);
        return favorite.create(title);
    }

    /**
     * 创建个收藏文件夹
     *
     * @param title 收藏夹标题
     * @param intro 收藏夹简介
     * @return
     */
    public boolean create(String title, String intro) throws IOException {
        CreateFavorite favorite = new CreateFavorite(cookie);
        return favorite.create(title, intro);
    }

    /**
     * 创建个收藏文件夹
     *
     * @param title   收藏夹标题
     * @param privacy 是否公开
     * @return
     */
    public boolean create(String title, boolean privacy) throws IOException {
        CreateFavorite favorite = new CreateFavorite(cookie);
        return favorite.create(title, privacy);
    }

    /**
     * 创建个收藏文件夹
     *
     * @param title   收藏夹标题
     * @param intro   收藏夹简介
     * @param privacy 是否公开
     * @return
     */
    public boolean create(String title, String intro, boolean privacy) throws IOException {
        CreateFavorite favorite = new CreateFavorite(cookie);
        return favorite.create(title, intro, privacy);
    }

    /**
     * 创建个收藏文件夹
     *
     * @param title   收藏夹标题
     * @param intro   收藏夹简介
     * @param privacy 是否公开
     * @param cover   封面图url(封面会被审核)
     * @return
     */
    public boolean create(String title, String intro, boolean privacy, String cover) throws IOException {
        CreateFavorite favorite = new CreateFavorite(cookie);
        return favorite.create(title, intro, privacy, cover);
    }


    /**
     * 删除收藏的文件夹
     *
     * @param id 收藏文件夹的id
     * @return
     * @throws IOException
     */
    public boolean delete(long id) throws IOException {
        DeleteFavorite deleteFavorite = new DeleteFavorite(id, cookie);
        return deleteFavorite.delete();
    }

    /**
     * 删除收藏的文件夹
     *
     * @param username 用户的昵称
     * @param title    收藏文件夹的名称
     * @return
     * @throws IOException
     */
    public boolean delete(String username, String title) throws IOException {
        DeleteFavorite deleteFavorite = new DeleteFavorite(username, title, cookie);
        return deleteFavorite.delete();
    }


    /**
     * 删除收藏的文件夹
     *
     * @param id 收藏文件夹的id
     * @return
     * @throws IOException
     */
    public boolean clearInvalid(long id) throws IOException {
        ClearInvalidFavorite favorite = new ClearInvalidFavorite(id, cookie);
        return favorite.clear();
    }

    /**
     * 清理收藏文件夹内所有失效的内容
     *
     * @param username 用户的昵称
     * @param title    收藏文件夹的名称
     * @return
     * @throws IOException
     */
    public boolean clearInvalid(String username, String title) throws IOException {
        ClearInvalidFavorite favorite = new ClearInvalidFavorite(username, title, cookie);
        return favorite.clear();
    }


    /**
     * 修改收藏文件夹
     *
     * @param username 用户的昵称
     * @param oldTitle 收藏文件夹的标题
     * @param newTitle 收藏文件夹的新标题
     * @param privacy  是否公开
     * @return
     * @throws IOException
     */
    public boolean modify(String username, String oldTitle, String newTitle, boolean privacy) throws IOException {
        ModifyFavorite favorite = new ModifyFavorite(username, oldTitle, cookie);
        return favorite.modify(newTitle, privacy);
    }

    /**
     * 修改收藏文件夹
     *
     * @param id       收藏文件夹的id
     * @param newTitle 文件夹新标题
     * @param privacy  是否公开
     * @return
     * @throws IOException
     */
    public boolean modify(long id, String newTitle, boolean privacy) throws IOException {
        ModifyFavorite favorite = new ModifyFavorite(id, cookie);
        return favorite.modify(newTitle, privacy);
    }

    /**
     * 修改收藏文件夹 是否可见
     *
     * @param username 用户昵称
     * @param title    收藏文件夹名称
     * @param privacy  是否公开
     * @return
     * @throws IOException
     */
    public boolean modify(String username, String title, boolean privacy) throws IOException {
        ModifyFavorite favorite = new ModifyFavorite(username, title, cookie);
        return favorite.modify(title, privacy);
    }

    /**
     * 下载收藏夹内的所有视频,但是当文件夹中存在同名称的文件时不能覆盖他
     *  阻塞方法，且不提供视频下载时终止下载
     * @param username 用户名称
     * @param title    收藏夹标题
     * @throws IOException
     */
    @Deprecated
    public ArrayList<Future<DownloadResult>> download(String username, String title) throws IOException {
        return download(getFavoriteID(username, title));
    }

    /**
     * 下载收藏夹内的所有视频,但是当文件夹中存在同名称的文件时不能覆盖他
     *阻塞方法，且不提供视频下载时终止下载
     * @param mid   用户的mid
     * @param title 收藏夹标题
     * @throws IOException
     */
    @Deprecated
    public ArrayList<Future<DownloadResult>> download(long mid, String title) throws IOException {
        return download(getFavoriteID(mid, title));
    }

    /**
     * 下载收藏夹内的所有视频,但是当文件夹中存在同名称的文件时不能覆盖他
     *阻塞方法，且不提供视频下载时终止下载
     * @param id
     * @throws IOException
     */
    @Deprecated
    public ArrayList<Future<DownloadResult>> download(long id) throws IOException {
        throw new IllegalStateException("未实现");
    }


    /**
     * 停止下载下一个视频的下载（不会立刻暂停的）
     */
    public void stopDownload(){
        this.isStop=true;
    }

    private long getUserID(String username) throws IOException, CodeException {
        SearchUser searchUser = new SearchUser(cookie);
        long mid = -1;
        try {
            Result search = searchUser.search(username);
            mid = search.getMid();
        } catch (Exception e) {
            try {
                mid = searchUser.getMid2(username);
            } catch (NullPointerException exception) {
                throw new CodeException("无法获取到该名称所对应的mid：" + username);
            } catch (IOException ex) {
                throw new IOException(ex);
            }

        }
        return mid;
    }
}
