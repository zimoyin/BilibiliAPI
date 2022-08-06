import com.alibaba.fastjson.JSONObject;
import github.zimoyin.core.comment.pojo.table.Emote;
import org.junit.Test;

import java.util.HashMap;
import java.util.function.BiConsumer;

public class T {
    @Test
    public void s(){
       String str ="{\n" +
               "  \"[笑哭]\": {\n" +
               "    \"id\": 509,\n" +
               "    \"package_id\": 1,\n" +
               "    \"state\": 0,\n" +
               "    \"type\": 1,\n" +
               "    \"attr\": 0,\n" +
               "    \"text\": \"[笑哭]\",\n" +
               "    \"url\": \"http://i0.hdslb.com/bfs/emote/c3043ba94babf824dea03ce500d0e73763bf4f40.png\",\n" +
               "    \"meta\": {\n" +
               "      \"size\": 1\n" +
               "    },\n" +
               "    \"mtime\": 1645206695,\n" +
               "    \"jump_title\": \"笑哭\"\n" +
               "  }\n" +
               "}";

        HashMap<String, Emote> hashMap = JSONObject.parseObject(str, HashMap.class);
        hashMap.forEach(new BiConsumer() {
            @Override
            public void accept(Object o, Object o2) {

                System.out.println(o+":"+o2);
            }
        });


//        JSONObject jsonObject = JSONObject.parseObject(str);
//        System.out.println(jsonObject);
    }
}