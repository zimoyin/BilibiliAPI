/**
  * Copyright 2022 json.cn 
  */
package github.zimoyin.core.test.pojo.res.pojo;
import java.util.List;

/**
 * Auto-generated: 2022-09-09 11:26:59
 *
 * @author json.cn (i@json.cn)
 * @website http://www.json.cn/java2pojo/
 */
public class Data {

    private List<Item> item;
    private String business_card;
    private String floor_info;
    private String user_feature;
    private double preload_expose_pct;
    private double preload_floor_expose_pct;
    public void setItem(List<Item> item) {
         this.item = item;
     }
     public List<Item> getItem() {
         return item;
     }

    public void setBusiness_card(String business_card) {
         this.business_card = business_card;
     }
     public String getBusiness_card() {
         return business_card;
     }

    public void setFloor_info(String floor_info) {
         this.floor_info = floor_info;
     }
     public String getFloor_info() {
         return floor_info;
     }

    public void setUser_feature(String user_feature) {
         this.user_feature = user_feature;
     }
     public String getUser_feature() {
         return user_feature;
     }

    public void setPreload_expose_pct(double preload_expose_pct) {
         this.preload_expose_pct = preload_expose_pct;
     }
     public double getPreload_expose_pct() {
         return preload_expose_pct;
     }

    public void setPreload_floor_expose_pct(double preload_floor_expose_pct) {
         this.preload_floor_expose_pct = preload_floor_expose_pct;
     }
     public double getPreload_floor_expose_pct() {
         return preload_floor_expose_pct;
     }

}