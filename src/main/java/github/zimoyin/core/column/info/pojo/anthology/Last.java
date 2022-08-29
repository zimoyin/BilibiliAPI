/**
  * Copyright 2022 json.cn 
  */
package github.zimoyin.core.column.info.pojo.anthology;
import lombok.Data;

import java.util.List;

/**
 * Auto-generated: 2022-08-28 18:54:44
 *
 * @author json.cn (i@json.cn)
 * @website http://www.json.cn/java2pojo/
 */
@Data
public class Last {

    private int id;
    private String title;
    private int state;
    private int publish_time;
    private int words;
    private List<String> image_urls;
    private Category category;
    private List<String> categories;
    private String summary;
    public void setId(int id) {
         this.id = id;
     }
     public int getId() {
         return id;
     }

    public void setTitle(String title) {
         this.title = title;
     }
     public String getTitle() {
         return title;
     }

    public void setState(int state) {
         this.state = state;
     }
     public int getState() {
         return state;
     }

    public void setPublish_time(int publish_time) {
         this.publish_time = publish_time;
     }
     public int getPublish_time() {
         return publish_time;
     }

    public void setWords(int words) {
         this.words = words;
     }
     public int getWords() {
         return words;
     }

    public void setImage_urls(List<String> image_urls) {
         this.image_urls = image_urls;
     }
     public List<String> getImage_urls() {
         return image_urls;
     }

    public void setCategory(Category category) {
         this.category = category;
     }
     public Category getCategory() {
         return category;
     }

    public void setCategories(List<String> categories) {
         this.categories = categories;
     }
     public List<String> getCategories() {
         return categories;
     }

    public void setSummary(String summary) {
         this.summary = summary;
     }
     public String getSummary() {
         return summary;
     }

}