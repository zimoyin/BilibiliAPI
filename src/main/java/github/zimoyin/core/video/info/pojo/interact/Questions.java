/**
  * Copyright 2022 json.cn 
  */
package github.zimoyin.core.video.info.pojo.interact;

import java.util.List;

/**
 * Auto-generated: 2022-07-20 20:44:49
 *
 * @author json.cn (i@json.cn)
 * @website http://www.json.cn/java2pojo/
 */
public class Questions {

    /**
     * 选项所跳转的模块id
     */
    private int id;
    private int type;
    private int start_time_r;
    private int duration;
    private int pause_video;
    /**
     * 模块标题
     */
    private String title;
    private List<Choices> choices;
    public void setId(int id) {
         this.id = id;
     }
     public int getId() {
         return id;
     }

    public void setType(int type) {
         this.type = type;
     }
     public int getType() {
         return type;
     }

    public void setStart_time_r(int start_time_r) {
         this.start_time_r = start_time_r;
     }
     public int getStart_time_r() {
         return start_time_r;
     }

    public void setDuration(int duration) {
         this.duration = duration;
     }
     public int getDuration() {
         return duration;
     }

    public void setPause_video(int pause_video) {
         this.pause_video = pause_video;
     }
     public int getPause_video() {
         return pause_video;
     }

    public void setTitle(String title) {
         this.title = title;
     }
     public String getTitle() {
         return title;
     }

    public void setChoices(List<Choices> choices) {
         this.choices = choices;
     }
     public List<Choices> getChoices() {
         return choices;
     }

}