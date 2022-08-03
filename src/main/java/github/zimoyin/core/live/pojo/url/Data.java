/**
  * Copyright 2022 json.cn 
  */
package github.zimoyin.core.live.pojo.url;
import java.util.List;

/**
 * Auto-generated: 2022-08-03 20:18:38
 *
 * @author json.cn (i@json.cn)
 * @website http://www.json.cn/java2pojo/
 */
@lombok.Data
public class Data {

    /**
     * 当前画质代码qn
     */
    private int current_quality;
    /**
     * 可选画质数参数
     */
    private List<String> accept_quality;
    /**
     * 当前画质代码quality
     */
    private int current_qn;
    /**
     * 可选画质参数quality
     */
    private List<Quality_description> quality_description;
    /**
     * 直播流url组
     * 第一给为主线服务器，其余的为备用服务器
     */
    private List<Durl> durl;
}