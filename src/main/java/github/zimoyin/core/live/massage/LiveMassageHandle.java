package github.zimoyin.core.live.massage;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import github.zimoyin.core.barrage.data.Barrage;
import github.zimoyin.core.live.massage.vertx.Package;
import github.zimoyin.core.live.pojo.barrage.LiveMessageJsonRootBean;
import github.zimoyin.core.utils.JsonSerializeUtil;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

public interface LiveMassageHandle {
    public void handle(Massage massage);


    @Data
    class Massage {
        private Package page;
        /**
         * 存储所有的服务器命令，包括弹幕
         */
        private ArrayList<String> commands = new ArrayList<String>();
        private long hot;
        private ArrayList<Barrage> barrages = new ArrayList<Barrage>();


        public void addCommand(String command) {
            commands.add(command);
        }

        public void init() {
            JsonSerializeUtil.Path path = JsonSerializeUtil.getJsonPath();
            for (String command : commands) {
                //如果不是json就跳过
                if (!path.isObject(command)) continue;
                //处理
                handle(command);
            }
        }

        private void handle(String json) {
//            JsonSerializeUtil.Path path = JsonSerializeUtil.getJsonPath();
//            String cmd = path.read(json, "/cmd/");
            LiveMessageJsonRootBean pojo = JSONObject.parseObject(json, LiveMessageJsonRootBean.class);
            //弹幕
            if (pojo.getCmd().equalsIgnoreCase("DANMU_MSG")) {
                System.out.println("弹幕");
                DANMU_MSG_Handle(json);

            }
        }

        private void DANMU_MSG_Handle(String json) {
            System.out.println("1");
            LiveMessageJsonRootBean pojo = JSONObject.parseObject(json, LiveMessageJsonRootBean.class);
            List<String> info = pojo.getInfo();

            Barrage barrage = new Barrage();
            //设置弹幕信息
            barrage.setText(info.get(1));
            System.out.println(2);
            //设置发送弹幕的人 和 他的mid
            String authorJson = info.get(2);
            JSONArray authorArray = JSONObject.parseArray(authorJson);
            System.out.println(3);
            barrage.setMid(authorArray.get(0).toString());
            barrage.setAuthor(authorArray.get(1).toString());
            System.out.println(4);
            System.out.println(barrage);
            barrages.add(barrage);
            System.out.println("end");
            try {
                //设置弹幕属性
//                JSONArray cas = JSONObject.parseArray(info.get(0));
//                JSONObject casJson = (JSONObject) cas.get(cas.size() - 2);
//                JSONObject extra = (JSONObject) casJson.get("extra");
//                //颜色
//                barrage.setRbg(Integer.parseInt(extra.get("color").toString()));
//                //字体大小
//                barrage.setSize(Integer.parseInt(extra.get("font_size").toString()));
//                //弹幕类型
//                barrage.setType(Integer.parseInt(extra.get("dm_type").toString()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

}

