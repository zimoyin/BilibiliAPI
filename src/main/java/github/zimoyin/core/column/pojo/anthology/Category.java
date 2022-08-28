/**
  * Copyright 2022 json.cn 
  */
package github.zimoyin.core.column.pojo.anthology;

import lombok.Data;

/**
 * Auto-generated: 2022-08-28 18:54:44
 *
 * @author json.cn (i@json.cn)
 * @website http://www.json.cn/java2pojo/
 */
@Data
public class Category {

    private int id;
    private int parent_id;
    private String name;
    public void setId(int id) {
         this.id = id;
     }
     public int getId() {
         return id;
     }

    public void setParent_id(int parent_id) {
         this.parent_id = parent_id;
     }
     public int getParent_id() {
         return parent_id;
     }

    public void setName(String name) {
         this.name = name;
     }
     public String getName() {
         return name;
     }

}