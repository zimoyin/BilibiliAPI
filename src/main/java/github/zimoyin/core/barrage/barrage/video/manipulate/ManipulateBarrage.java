package github.zimoyin.core.barrage.barrage.video.manipulate;


import github.zimoyin.core.cookie.Cookie;

/**
 * 所有弹幕操作的抽象类
 * 包含公共方法，点赞弹幕，举报弹幕，检测是否有高级弹幕权限，撤回弹幕，购买高级弹幕发送权限，查询弹幕点赞数
 * 子类： 高级弹幕构建，普通弹幕构建，互动弹幕构建，维护稿件弹幕
 * 工厂模式
 */
@Deprecated
public abstract class ManipulateBarrage{
    private Cookie cookie;

    public ManipulateBarrage() {
    }

    public ManipulateBarrage(Cookie cookie) {
        this.cookie = cookie;
    }


    /**
     * 撤回值得弹幕但是每天只有三次机会
     * @param bv 视频的bv号
     * @param dmid 弹幕的id
     */
    public void revokeBarrage(String bv,String dmid){
//        new WebCookie().get
    }
}
