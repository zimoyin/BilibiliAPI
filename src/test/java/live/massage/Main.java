package live.massage;

import com.alibaba.fastjson.JSONObject;


import github.zimoyin.core.live.massage.LiveMessage;
import github.zimoyin.core.live.pojo.message.LiveMessageJsonRootBean;
import github.zimoyin.core.live.video.LiveVideoURL;


import java.util.HashMap;

public class Main {


    public static void main(String[] args) {
        LiveMessage massage = new LiveMessage();
        JSONObject params = new JSONObject();
        massage.run(22711711, msg -> {
        });
    }
}
