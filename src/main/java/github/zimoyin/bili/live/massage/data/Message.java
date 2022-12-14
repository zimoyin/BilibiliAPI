package github.zimoyin.bili.live.massage.data;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import github.zimoyin.bili.barrage.data.Barrage;
import github.zimoyin.bili.live.massage.vertx.Package;
import github.zimoyin.bili.live.pojo.message.LiveMessageJsonRootBean;
import github.zimoyin.bili.live.pojo.message.MessageData;
import github.zimoyin.bili.live.pojo.message.UserJson;
import github.zimoyin.bili.utils.JsonSerializeUtil;
import lombok.Data;
import org.apache.logging.log4j.LogManager;

import java.util.ArrayList;
import java.util.List;


/**
 * 直播命令包解析
 * 注意如果包中没有则获取不到部分内容，请手动判断这部分内容
 */
@Data
public class Message {

    private Package page;
    /**
     * 存储所有的服务器命令，包括弹幕；原始数据未进行解析状态
     */
    private ArrayList<String> commands = new ArrayList<String>();
    /**
     * 人气
     */
    private long hot;
    private ArrayList<Barrage> barrages = new ArrayList<Barrage>();

    //    public final Logger logger = LoggerFactory.getLogger(this.getClass());
    public final org.apache.logging.log4j.Logger logger = LogManager.getLogger(this.getClass());

    public void addCommand(String command) {
        commands.add(command);
    }

    /**
     * 处理出全包进入直播间的信息
     * MessageType.INTERACT_WORD.name() 被其他方法使用
     */
    @Deprecated
    public ArrayList<MessageData> getEnterLive() {
        ArrayList<MessageData> list = new ArrayList<>();
        for (String command : commands) {
            LiveMessageJsonRootBean bean = JSONObject.parseObject(command, LiveMessageJsonRootBean.class);
            MessageData data = bean.getData();
            if (data == null || !MessageType.INTERACT_WORD.name().equalsIgnoreCase(bean.getCmd())) continue;
            //如果不是进场信息
            if (data.getMsg_type() != 1) continue;
            list.add(data);
        }
        return list;
    }

    /**
     * 处理出全包送出的礼物
     */
    public ArrayList<MessageData> getGift() {
        ArrayList<MessageData> list = new ArrayList<>();
        for (String command : commands) {
            LiveMessageJsonRootBean bean = JSONObject.parseObject(command, LiveMessageJsonRootBean.class);
            MessageData data = bean.getData();
            if (data == null || !MessageType.SEND_GIFT.name().equalsIgnoreCase(bean.getCmd())) continue;
            list.add(data);
        }
        return list;
    }

    /**
     * 获取包中的在线人数统计排名
     */
    public long getOnlineRankCount() {
        for (String command : commands) {
            LiveMessageJsonRootBean bean = JSONObject.parseObject(command, LiveMessageJsonRootBean.class);
            MessageData data = bean.getData();
            if (data == null || !MessageType.ONLINE_RANK_COUNT.name().equalsIgnoreCase(bean.getCmd())) continue;
            return data.getCount();
        }
        return -1;
    }

    /**
     * 获取包中高能用户列表 新版本
     */
    public ArrayList<UserJson> getUserRankCount() {
        for (String command : commands) {
            LiveMessageJsonRootBean bean = JSONObject.parseObject(command, LiveMessageJsonRootBean.class);
            MessageData data = bean.getData();
            if (data == null || !MessageType.ONLINE_RANK_V2.name().equalsIgnoreCase(bean.getCmd())) continue;
            return data.getList();
        }
        return null;
    }

    /**
     * 获取包中的热度排名
     */
    public long getHotRank() {
        for (String command : commands) {
            LiveMessageJsonRootBean bean = JSONObject.parseObject(command, LiveMessageJsonRootBean.class);
            MessageData data = bean.getData();
            if (data == null || !MessageType.HOT_RANK_CHANGED_V2.name().equalsIgnoreCase(bean.getCmd())) continue;
            return data.getRank();
        }
        return -1;
    }

    /**
     * 获取包中的热度排名 总结。
     * 如：主播进入前5，就会发包祝贺主播进入前5
     */
    public long getHotRankSettlement() {
        for (String command : commands) {
            LiveMessageJsonRootBean bean = JSONObject.parseObject(command, LiveMessageJsonRootBean.class);
            MessageData data = bean.getData();
            if (data == null || !MessageType.HOT_RANK_SETTLEMENT_V2.name().equalsIgnoreCase(bean.getCmd())) continue;
            return data.getRank();
        }
        return -1;
    }

    /**
     * 主播下播，开播？
     */
    public String getPreparing() {
        for (String command : commands) {
            LiveMessageJsonRootBean bean = JSONObject.parseObject(command, LiveMessageJsonRootBean.class);
            MessageData data = bean.getData();
            if (data == null || !MessageType.PREPARING.name().equalsIgnoreCase(bean.getCmd())) continue;
            return command;
        }
        return null;
    }

    /**
     * 有大佬进入直播间？
     */
    public String getEnter_effect() {
        for (String command : commands) {
            LiveMessageJsonRootBean bean = JSONObject.parseObject(command, LiveMessageJsonRootBean.class);
            MessageData data = bean.getData();
            if (data == null || !MessageType.ENTRY_EFFECT.name().equalsIgnoreCase(bean.getCmd())) continue;
            return command;
        }
        return null;
    }

    /**
     * 广播、注意？
     */
    public String getCommonNoticeDanaku() {
        for (String command : commands) {
            LiveMessageJsonRootBean bean = JSONObject.parseObject(command, LiveMessageJsonRootBean.class);
            MessageData data = bean.getData();
            if (data == null || !MessageType.COMMON_NOTICE_DANMAKU.name().equalsIgnoreCase(bean.getCmd())) continue;
            return command;
        }
        return null;
    }

    /**
     * 广播、系统条幅？
     */
    public String getWidgetBanner() {
        for (String command : commands) {
            LiveMessageJsonRootBean bean = JSONObject.parseObject(command, LiveMessageJsonRootBean.class);
            MessageData data = bean.getData();
            if (data == null || !MessageType.WIDGET_BANNER.name().equalsIgnoreCase(bean.getCmd())) continue;
            return command;
        }
        return null;
    }

    /**
     * 广播、注意信息？
     */
    public String getNoticeMessage() {
        for (String command : commands) {
            LiveMessageJsonRootBean bean = JSONObject.parseObject(command, LiveMessageJsonRootBean.class);
            MessageData data = bean.getData();
            if (data == null || !MessageType.NOTICE_MSG.name().equalsIgnoreCase(bean.getCmd())) continue;
            return command;
        }
        return null;
    }

    /**
     * 获取包中的统计出的有多少人看过直播
     */
    public long getWatchedCount() {
        for (String command : commands) {
            LiveMessageJsonRootBean bean = JSONObject.parseObject(command, LiveMessageJsonRootBean.class);
            MessageData data = bean.getData();
            if (data == null || !MessageType.WATCHED_CHANGE.name().equalsIgnoreCase(bean.getCmd())) continue;
            return data.getNum();
        }
        return -1;
    }


    /**
     * 获取包中的实时信息
     */
    public String getRealMessage() {
        for (String command : commands) {
            LiveMessageJsonRootBean bean = JSONObject.parseObject(command, LiveMessageJsonRootBean.class);
            MessageData data = bean.getData();
            if (data == null || !MessageType.ROOM_REAL_TIME_MESSAGE_UPDATE.name().equalsIgnoreCase(bean.getCmd())) continue;
            return command;
        }
        return null;
    }


    /**
     * 获取包中的实时粉丝数
     * MessageType.ROOM_REAL_TIME_MESSAGE_UPDATE.name() 被其他方法使用
     */
    @Deprecated //信息主键不符合
    public long getRealFans() {
        for (String command : commands) {
            LiveMessageJsonRootBean bean = JSONObject.parseObject(command, LiveMessageJsonRootBean.class);
            MessageData data = bean.getData();
            if (data == null || !MessageType.ROOM_REAL_TIME_MESSAGE_UPDATE.name().equalsIgnoreCase(bean.getCmd())) continue;
            return data.getFans();
        }
        return -1;
    }

    /**
     * 直播停止列表
     */
    public ArrayList<Long> getStopLiveRoomList() {
        for (String command : commands) {
            LiveMessageJsonRootBean bean = JSONObject.parseObject(command, LiveMessageJsonRootBean.class);
            MessageData data = bean.getData();
            if (data == null || !"STOP_LIVE_ROOM_LIST".equalsIgnoreCase(bean.getCmd())) continue;
            return data.getRoom_id_list();
        }
        return null;
    }

    /**
     * 处理出全包关注直播的信息
     */
    public ArrayList<MessageData> getFollowLive() {
        ArrayList<MessageData> list = new ArrayList<>();
        for (String command : commands) {
            LiveMessageJsonRootBean bean = JSONObject.parseObject(command, LiveMessageJsonRootBean.class);
            MessageData data = bean.getData();
            if (data == null || !MessageType.INTERACT_WORD.name().equalsIgnoreCase(bean.getCmd())) continue;
            //如果不是进场信息
            if (data.getMsg_type() != 2) continue;
            list.add(data);
        }
        return list;
    }

    /**
     * 处理出全包弹幕
     *
     * @return
     */
    public ArrayList<Barrage> getBarrages() {
        JsonSerializeUtil.Path path = JsonSerializeUtil.getJsonPath();
        for (String command : commands) {
            //如果不是json就跳过
            if (!path.isObject(command)) continue;
            LiveMessageJsonRootBean pojo = JSONObject.parseObject(command, LiveMessageJsonRootBean.class);
            //弹幕
            if (pojo.getCmd().equalsIgnoreCase(MessageType.DANMU_MSG.name())) {
                try {
                    DANMU_MSG_Handle(pojo);
                } catch (Exception e) {
                    logger.error("构建弹幕失败", e);
                }

            }
        }
        return barrages;
    }


    /**
     * 处理一个弹幕
     *
     * @param pojo
     */
    private void DANMU_MSG_Handle(LiveMessageJsonRootBean pojo) {
        List<String> info = pojo.getInfo();

        Barrage barrage = new Barrage();
        //设置弹幕信息
        barrage.setText(info.get(1));
        //设置发送弹幕的人 和 他的mid
        String authorJson = info.get(2);
        //弹幕发送的时间
        String tsJson = info.get(9);
        Long ts = JSONObject.parseObject(tsJson).getLong("ts");
        //解析发送者
        JSONArray authorArray = JSONObject.parseArray(authorJson);
        barrage.setMid(authorArray.get(0).toString());
        barrage.setAuthor(authorArray.get(1).toString());
        barrages.add(barrage);
        try {
            // 设置弹幕属性（获取弹幕数组中第一个元素(数组)的倒数第二个元素并解析）
            JSONArray cas = JSONObject.parseArray(info.get(0));
            JSONObject casJson = (JSONObject) cas.get(cas.size() - 2);
            //extra
            String extraContent = casJson.get("extra").toString();
            JSONObject extra = JSONObject.parseObject(extraContent);
            //用户发送的时间
            barrage.setSendTime(ts*1000);
            //接收时的时间
            barrage.setShowTime(ts*1000);
            //颜色
            barrage.setRbg(Integer.parseInt(extra.get("color").toString()));
            //字体大小
            barrage.setSize(Integer.parseInt(extra.get("font_size").toString()));
            //弹幕类型
            barrage.setType(Integer.parseInt(extra.get("dm_type").toString()));
        } catch (Exception e) {
            logger.warn("构建详细弹幕信息失败", e);
        }
    }

    public static enum MessageType {
        /**
         * 处理出全包弹幕
         *
         * @return
         */
        DANMU_MSG,
        /**
         * 处理出全包关注直播的信息
         */
        INTERACT_WORD,
        /**
         * 直播停止列表
         */
        STOP_LIVE_ROOM_LIST,
        /**
         * 获取包中的实时信息 || 获取包中的实时粉丝数????
         */
        ROOM_REAL_TIME_MESSAGE_UPDATE,
        /**
         * 获取包中的统计出的有多少人看过直播
         */
        WATCHED_CHANGE,
        /**
         * 广播、注意信息？
         */
        NOTICE_MSG,
        /**
         * 广播、系统条幅？
         */
        WIDGET_BANNER,
        /**
         * 广播、注意？
         */
        COMMON_NOTICE_DANMAKU,
        /**
         * 有大佬进入直播间？
         */
        ENTRY_EFFECT,
        /**
         * 主播下播，开播？
         */
        PREPARING,
        /**
         * 获取包中的热度排名 总结。
         * 如：主播进入前5，就会发包祝贺主播进入前5
         */
        HOT_RANK_SETTLEMENT_V2,
        /**
         * 获取包中的热度排名
         */
        HOT_RANK_CHANGED_V2,
        /**
         * 获取包中高能用户列表 新版本
         */
        ONLINE_RANK_V2,
        /**
         * 获取包中的在线人数统计排名
         */
        ONLINE_RANK_COUNT,
        /**
         * 处理出全包送出的礼物
         */
        SEND_GIFT,
    }
}