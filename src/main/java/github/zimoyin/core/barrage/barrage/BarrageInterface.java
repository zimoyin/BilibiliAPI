package github.zimoyin.core.barrage.barrage;



import github.zimoyin.core.barrage.data.Barrage;
import github.zimoyin.core.exception.Code;
import github.zimoyin.core.utils.IDConvert;
import github.zimoyin.core.utils.JsonSerializeUtil;
import github.zimoyin.core.utils.net.httpclient.HttpClientResult;
import github.zimoyin.core.utils.net.httpclient.HttpClientUtils;

import java.util.ArrayList;


/**
 * 获取弹幕的公共接口
 * 实现类：历史弹幕，快照弹幕，实时弹幕，最近弹幕
 * 这些类应都都由一个类来统一调用，类中每一个方法都去创建一个子类
 */
public interface BarrageInterface {
    /**
     * 返回弹幕集合
     * @param bv
     * @return
     * @throws Exception
     */
    public  ArrayList<Barrage> getBarrage(String bv) throws Exception;

    /**
     * 返回弹幕原始数据
     * @param bv
     * @return
     * @throws Exception
     */
    public  String getPage(String bv) throws Exception;

    /**
     * 获取视频的CID
     *
     * @param bv
     * @return
     * @throws Exception
     */
    public default String getCID(String bv) throws Exception {
        return IDConvert.BvToCID(bv);
    }


}
