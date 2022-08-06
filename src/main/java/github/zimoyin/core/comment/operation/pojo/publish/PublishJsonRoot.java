/**
  * Copyright 2022 json.cn 
  */
package github.zimoyin.core.comment.operation.pojo.publish;

/**
 * Auto-generated: 2022-08-06 20:22:0
 *
 * @author json.cn (i@json.cn)
 * @website http://www.json.cn/java2pojo/
 */
@lombok.Data
public class PublishJsonRoot {

    /**
     * 0：成功
     * -101：账号未登录
     * -102：账号被封停
     * -111：csrf校验失败
     * -400：请求错误
     * -404：无此项
     * -509：请求过于频繁
     * 12001：已经存在评论主题
     * 12002：评论区已关闭
     * 12003：禁止回复
     * 12006：没有该评论
     * 12009：评论主体的type不合法
     * 12015：需要评论验证码
     * 12016：评论内容包含敏感信息
     * 12025：评论字数过多
     * 12035：该账号被UP主列入评论黑名单
     * 12051：重复评论，请勿刷屏
     * 12052：评论区已关闭
     */
    private int code;
    private String message;
    private int ttl;
    private Data data;

}