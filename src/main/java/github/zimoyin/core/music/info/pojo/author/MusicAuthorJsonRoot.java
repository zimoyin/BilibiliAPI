/**
  * Copyright 2022 json.cn 
  */
package github.zimoyin.core.music.info.pojo.author;
import java.util.List;

/**
 * Auto-generated: 2022-08-30 10:52:44
 *
 * @author json.cn (i@json.cn)
 * @website http://www.json.cn/java2pojo/
 */
@lombok.Data
public class MusicAuthorJsonRoot {

    private int code;
    private String msg;
    private List<Data> data;
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

    public void setData(List<Data> data) {
         this.data = data;
     }
     public List<Data> getData() {
         return data;
     }

}