/**
  * Copyright 2022 json.cn 
  */
package github.zimoyin.core.video.info.pojo.online;

/**
 * Auto-generated: 2022-07-18 12:4:40
 *
 * @author json.cn (i@json.cn)
 * @website http://www.json.cn/java2pojo/
 */
@lombok.Data
public class Data {

    /**
     * 所有终端总计人数
     */
    private String total;
    /**
     * web端实时在线人数
     */
    private String count;
    /**
     * 数据显示控制
     */
    private Show_switch show_switch;
    private Abtest abtest;

}