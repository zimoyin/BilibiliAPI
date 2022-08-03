/**
  * Copyright 2022 json.cn 
  */
package github.zimoyin.core.live.pojo.url;

import java.net.URL;
import java.util.ArrayList;

/**
 * Auto-generated: 2022-08-03 20:18:38
 *
 * @author json.cn (i@json.cn)
 * @website http://www.json.cn/java2pojo/
 */
@lombok.Data
public class LiveVideoURLJsonRoot {

    /**
     * 0：成功
     * -400：参数错误
     * 19002003：房间信息不存在
     */
    private int code;
    private String message;
    private int ttl;
    private Data data;

    public ArrayList<URL> getURL(){
        ArrayList<URL> urls = new ArrayList<>();
        for (Durl durl : this.getData().getDurl()) {
            urls.add(durl.getUrl());
        }
        return urls;
    }
}