/**
 * Copyright 2022 json.cn
 */
package github.zimoyin.core.video.info.pojo.info;

import github.zimoyin.core.video.info.VideoINFOSummary;

/**
 * Auto-generated: 2022-07-16 13:2:38
 *
 * @author json.cn (i@json.cn)
 * @website http://www.json.cn/java2pojo/
 * 视频信息json pojo 根
 */
@lombok.Data
public class WEBVideoINFOJsonRootBean {

    /**
     * 0：成功
     * -400：请求错误
     * -403：权限不足
     * -404：无视频
     * 62002：稿件不可见
     * 62004：稿件审核中
     */
    private int code;
    /**
     * 错误信息,默认为0
     */
    private String message;
    private int ttl;
    /**
     * 信息本体
     */
    private Data data;

    public void setCode(int code) {
        this.code = code;
    }

    /**
     * 访问视频信息的状态信息
     * @return
     */
    public String getCodeInfo() {
        switch (getCode()) {
            case 0:
                return "成功";
            case -400:
                return "请求错误";
            case -403:
                return "权限不足";
            case -404:
                return "无视频";
            case 62002:
                return "稿件不可见";
            case 62004:
                return "稿件审核中";
        }
        return null;
    }


    /**
     * 返回视频信息的摘要信息
     * @return
     */
    public VideoINFOSummary getVideoInfoSummary(){
        return new VideoINFOSummary(this);
    }
}