/**
  * Copyright 2022 json.cn 
  */
package github.zimoyin.core.video.info.pojo.snapshot;
import java.net.URL;
import java.util.List;

/**
 * Auto-generated: 2022-07-18 12:25:16
 *
 * @author json.cn (i@json.cn)
 * @website http://www.json.cn/java2pojo/
 */
@lombok.Data
public class Data {

    /**
     * bin格式截取时间表url
     */
    private String pvdata;
    /**
     * 每行图片数
     */
    private int img_x_len;
    /**
     * 每列图片数
     */
    private int img_y_len;
    /**
     * 每张图片长
     */
    private int img_x_size;
    /**
     * 每张图片宽
     */
    private int img_y_size;
    /**
     * 图片拼版
     */
    private List<String> image;
    /**
     * json数组格式截取时间表
     */
    private List<Integer> index;
}