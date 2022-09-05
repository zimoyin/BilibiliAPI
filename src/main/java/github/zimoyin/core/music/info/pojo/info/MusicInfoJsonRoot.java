/**
  * Copyright 2022 json.cn 
  */
package github.zimoyin.core.music.info.pojo.info;

/**
 * Auto-generated: 2022-08-30 10:37:33
 *
 * @author json.cn (i@json.cn)
 * @website http://www.json.cn/java2pojo/
 */
@lombok.Data
public class MusicInfoJsonRoot {

    private int code;
    private String msg;
    private Data data;
    public void setCode(int code) {
         this.code = code;
     }
     public int getCode() {
         return code;
     }

    public void setMsg(String msg) {
         this.msg = msg;
     }
     public String getMsg() {
         return msg;
     }

    public void setData(Data data) {
         this.data = data;
     }
     public Data getData() {
         return data;
     }

}