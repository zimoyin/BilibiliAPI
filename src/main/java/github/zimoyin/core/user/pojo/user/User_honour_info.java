/**
  * Copyright 2022 json.cn 
  */
package github.zimoyin.core.user.pojo.user;
import java.util.List;

/**
 * Auto-generated: 2022-07-22 9:39:59
 *
 * @author json.cn (i@json.cn)
 * @website http://www.json.cn/java2pojo/
 */
public class User_honour_info {

    private int mid;
    private String colour;
    private List<String> tags;
    public void setMid(int mid) {
         this.mid = mid;
     }
     public int getMid() {
         return mid;
     }

    public void setColour(String colour) {
         this.colour = colour;
     }
     public String getColour() {
         return colour;
     }

    public void setTags(List<String> tags) {
         this.tags = tags;
     }
     public List<String> getTags() {
         return tags;
     }

}