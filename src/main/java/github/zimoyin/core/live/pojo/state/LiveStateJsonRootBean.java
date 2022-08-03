/**
  * Copyright 2022 json.cn 
  */
package github.zimoyin.core.live.pojo.state;

/**
 * Auto-generated: 2022-08-03 12:40:43
 *
 * @author json.cn (i@json.cn)
 * @website http://www.json.cn/java2pojo/
 */
@lombok.Data
public class LiveStateJsonRootBean {

    private int code;
    private String message;
    private int ttl;
    private Data data;
    public void setCode(int code) {
         this.code = code;
     }
     public int getCode() {
         return code;
     }

    public void setMessage(String message) {
         this.message = message;
     }
     public String getMessage() {
         return message;
     }

    public void setTtl(int ttl) {
         this.ttl = ttl;
     }
     public int getTtl() {
         return ttl;
     }

    public void setData(Data data) {
         this.data = data;
     }
     public Data getData() {
         return data;
     }

}