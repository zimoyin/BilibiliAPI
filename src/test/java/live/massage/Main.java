package live.massage;

import com.alibaba.fastjson.JSONObject;
import github.zimoyin.core.live.massage.LiveMassage;

import github.zimoyin.core.live.pojo.message.LiveMessageJsonRootBean;
import github.zimoyin.core.live.video.LiveVideoURL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

public class Main {
    private static Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        LiveMassage massage = new LiveMassage();
        JSONObject params = new JSONObject();
        massage.run(22711711, msg -> {
        });
    }
}
