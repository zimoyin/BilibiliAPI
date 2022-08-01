/**
  * Copyright 2022 json.cn 
  */
package github.zimoyin.core.user.pojo.state.contribution;

/**
 * Auto-generated: 2022-07-23 19:35:58
 *
 * @author json.cn (i@json.cn)
 * @website http://www.json.cn/java2pojo/
 */
public class Favourite {

    /**
     * 全部收藏夹数
     * 需要登录(SESSDATA)
     * 只能查看自己的
     */
    private int master;
    /**
     * 公开收藏夹数
     * 无视隐私设置
     */
    private int guest;
}