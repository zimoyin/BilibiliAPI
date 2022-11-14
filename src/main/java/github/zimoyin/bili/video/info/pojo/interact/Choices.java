/**
  * Copyright 2022 json.cn 
  */
package github.zimoyin.bili.video.info.pojo.interact;

/**
 * Auto-generated: 2022-07-20 20:44:49
 *
 * @author json.cn (i@json.cn)
 * @website http://www.json.cn/java2pojo/
 */
public class Choices {

    private long id;
    private String platform_action;
    private String native_action;
    private String condition;
    private long cid;
    private String option;
    private int is_default;
    public void setId(long id) {
         this.id = id;
     }
     public long getId() {
         return id;
     }

    public void setPlatform_action(String platform_action) {
         this.platform_action = platform_action;
     }
     public String getPlatform_action() {
         return platform_action;
     }

    public void setNative_action(String native_action) {
         this.native_action = native_action;
     }
     public String getNative_action() {
         return native_action;
     }

    public void setCondition(String condition) {
         this.condition = condition;
     }
     public String getCondition() {
         return condition;
     }

    public void setCid(long cid) {
         this.cid = cid;
     }
     public long getCid() {
         return cid;
     }

    public void setOption(String option) {
         this.option = option;
     }
     public String getOption() {
         return option;
     }

    public void setIs_default(int is_default) {
         this.is_default = is_default;
     }
     public int getIs_default() {
         return is_default;
     }

}