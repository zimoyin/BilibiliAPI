package github.zimoyin.bili.video.download.setting;

import github.zimoyin.bili.collection.CollectionInfo;
import github.zimoyin.bili.collection.UserCollection;
import github.zimoyin.bili.collection.pojo.collection.Archives;
import github.zimoyin.bili.collection.pojo.collection.CollectionJsonRoot;
import github.zimoyin.bili.collection.pojo.collection.Meta;
import github.zimoyin.bili.collection.pojo.userlist.CInfo;
import github.zimoyin.bili.collection.pojo.userlist.UserCollectionListJsonRoot;
import github.zimoyin.bili.cookie.Cookie;
import github.zimoyin.bili.user.up.SearchUser;
import github.zimoyin.bili.video.download.setting.info.DownloadVideoInfo;
import github.zimoyin.bili.video.download.setting.info.DownloadVideoInfoList;
import github.zimoyin.bili.video.url.param.ParamBuilder;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;

/**
 * 集合视频下载设置
 * 根据 MID 与 SID 定位到视频合集从而进行维护
 */
public class CollectionVideoSetting extends DownloadVideoSettingAbs {
    /**
     * 视频合集映射<sid,合集名称>
     */
    @Getter
    private final HashMap<Long, String> CollectionMap = new HashMap<>();
    /**
     * 视频信息映射<sid,List<视频信息>> 与 mapArch 为 共同维护链表
     */
    private final HashMap<Long, ArrayList<DownloadVideoInfoList>> mapInfo = new HashMap<>();
    /**
     * 视频信息映射<sid,List<视频信息>> 与 mapInfo 为 共同维护链表
     */
    private final HashMap<Long, List<Archives>> mapArch = new HashMap<>();
    private final ParamBuilder Param;
    @Getter
    private final long Mid;

    @Getter
    @Setter
    private long Sid;
    private int page = 1;
    @Getter
    @Setter
    private Cookie cookie;

    /**
     * @param mid   用户ID
     * @param param 视频参数
     */
    public CollectionVideoSetting(long mid, ParamBuilder param) {
        Param = param;
        try {
            Mid = mid;
            UserCollectionListJsonRoot pojo = new UserCollection().getJsonPojo(mid);
            for (CInfo info : pojo.getData().getList()) {
                Meta meta = info.getMeta();
                CollectionMap.put(meta.getSeason_id(), meta.getName());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @param userName 用户名称
     * @param param    视频参数
     */
    public CollectionVideoSetting(String userName, ParamBuilder param) {
        Param = param;
        try {
            Mid = new SearchUser().getMid2(userName);
            UserCollectionListJsonRoot pojo = new UserCollection().getJsonPojo(Mid);
            for (CInfo info : pojo.getData().getList()) {
                Meta meta = info.getMeta();
                CollectionMap.put(meta.getSeason_id(), meta.getName());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @param userName 用户名称
     * @param sid      合集ID
     * @param param    视频参数
     */
    public CollectionVideoSetting(String userName, long sid, ParamBuilder param) {
        Param = param;
        try {
            Mid = new SearchUser().getMid2(userName);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @param mid   用户ID
     * @param sid   合集ID
     * @param param 视频参数
     */
    public CollectionVideoSetting(long mid, long sid, ParamBuilder param) {
        Param = param;
        Mid = mid;
        Sid = sid;
    }


    /**
     * 获取当前 SID（合集下）所有视频的信息
     * @return
     */
    public List<Archives> getPageInfos() {
        lazyInit();
        return mapArch.get(getSid());
    }

    /**
     * 初始化
     */
    private void lazyInit() {
        if (Sid <= 0) throw new NullPointerException("Sid 小于等于 0，无法定位到视频合集");
        ArrayList<DownloadVideoInfoList> list = mapInfo.get(Sid);
        try {
            if (list == null) {
                list = new ArrayList<>();
                //遍历出所有的合集视频
                List<Archives> archives = initArchives(getMid(), getSid());
                //根据合集视频数量进行占位操作
                for (Archives archive : archives) {
                    list.add(null); //占位
                }
                mapInfo.put(getSid(), list);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private List<Archives> initArchives(long mid,long sid) throws IOException {
        CollectionJsonRoot.Data data = new CollectionInfo().getJsonPojo(mid, sid).getData();
        int total = data.getMeta().getTotal();
        List<Archives> archives = data.getArchives();
        mapArch.put(getSid(),archives);
        for (int i = 0; i < (total / 100)+1; i++) {
            archives.addAll( new CollectionInfo().getJsonPojo(mid, sid,i+2).getData().getArchives());
        }
        return archives;
    }

    @Override
    public void updateDownloadPage(int page) {
        lazyInit();
        this.page = page;
    }

    @Override
    public DownloadVideoInfo getPage() {
        return getPage(this.page);
    }

    @Override
    public DownloadVideoInfo getPage(int page) {
        lazyInit();
        //获取page，如果为null，则初始化一个出来
        ArrayList<DownloadVideoInfoList> infoLists = mapInfo.get(getSid());
        DownloadVideoInfoList list = infoLists.get(page - 1);
        if (list == null){
            String bvid = mapArch.get(getSid()).get(page - 1).getBvid();
            list = new DownloadVideoInfoList(bvid,Param);
            infoLists.set(page-1,list);
        }
        return list.getPage(1);
    }

    @Override
    public int getSize() {
        lazyInit();
        return mapInfo.get(getSid()).size();
    }


    @Override
    @Deprecated
    public void forEach(Consumer<? super DownloadVideoInfo> action) {
        throw new IllegalArgumentException("这是一个没有被实现的方法");
    }
}
