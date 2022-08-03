package github.zimoyin.core.live.massage.data;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import github.zimoyin.core.barrage.data.Barrage;
import github.zimoyin.core.live.massage.vertx.Package;
import github.zimoyin.core.live.pojo.message.LiveMessageJsonRootBean;
import github.zimoyin.core.utils.JsonSerializeUtil;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

@Data
public class Massage {

    private Package page;
    /**
     * 存储所有的服务器命令，包括弹幕
     */
    private ArrayList<String> commands = new ArrayList<String>();
    /**
     * 人气
     */
    private long hot;
    private ArrayList<Barrage> barrages = new ArrayList<Barrage>();
    public final Logger logger = LoggerFactory.getLogger(this.getClass());

    public void addCommand(String command) {
        commands.add(command);
    }


    /**
     * 处理全班弹幕
     * @return
     */
    public ArrayList<Barrage> getBarrages() {
        JsonSerializeUtil.Path path = JsonSerializeUtil.getJsonPath();
        for (String command : commands) {
            //如果不是json就跳过
            if (!path.isObject(command)) continue;
            LiveMessageJsonRootBean pojo = JSONObject.parseObject(command, LiveMessageJsonRootBean.class);
            //弹幕
            if (pojo.getCmd().equalsIgnoreCase("DANMU_MSG")) {
                try {
                    DANMU_MSG_Handle(pojo);
                }catch (Exception e){
                    logger.error("构建弹幕失败",e);
                }

            }
        }
        return barrages;
    }



    /**
     * 处理一个弹幕
     * @param pojo
     */
    private void DANMU_MSG_Handle(LiveMessageJsonRootBean pojo) {
        List<String> info = pojo.getInfo();

        Barrage barrage = new Barrage();
        //设置弹幕信息
        barrage.setText(info.get(1));
        //设置发送弹幕的人 和 他的mid
        String authorJson = info.get(2);
        JSONArray authorArray = JSONObject.parseArray(authorJson);
        barrage.setMid(authorArray.get(0).toString());
        barrage.setAuthor(authorArray.get(1).toString());
        barrages.add(barrage);
        try {
            // 设置弹幕属性
            JSONArray cas = JSONObject.parseArray(info.get(0));
            JSONObject casJson = (JSONObject) cas.get(cas.size() - 2);
            //extra
            String extraContent = casJson.get("extra").toString();
            JSONObject extra = JSONObject.parseObject(extraContent);
            //用户发送的时间
            barrage.setSendTime(Long.parseLong(cas.get(5).toString())*1000);
            //接收时的时间
            barrage.setShowTime(Long.parseLong(cas.get(4).toString()));
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

}