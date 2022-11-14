/**
  * Copyright 2022 json.cn 
  */
package github.zimoyin.bili.video.info.pojo.interact;
import java.util.List;

/**
 * Auto-generated: 2022-07-20 20:44:49
 *
 * @author json.cn (i@json.cn)
 * @website http://www.json.cn/java2pojo/
 */
public class Edges {

    private Dimension dimension;
    private List<Questions> questions;
    private Skin skin;
    public void setDimension(Dimension dimension) {
         this.dimension = dimension;
     }
     public Dimension getDimension() {
         return dimension;
     }

    public void setQuestions(List<Questions> questions) {
         this.questions = questions;
     }
     public List<Questions> getQuestions() {
         return questions;
     }

    public void setSkin(Skin skin) {
         this.skin = skin;
     }
     public Skin getSkin() {
         return skin;
     }

}