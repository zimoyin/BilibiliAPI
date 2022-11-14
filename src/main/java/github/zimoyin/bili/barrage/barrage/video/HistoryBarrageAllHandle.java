package github.zimoyin.bili.barrage.barrage.video;


import github.zimoyin.bili.barrage.data.Barrage;

import java.util.ArrayList;


/**
 * 当遍历所有历史弹幕的时候，可以用这个类对遍历中的弹幕进行处理
 */
public abstract class HistoryBarrageAllHandle {
    /**
     * 处理函数
     * @param date 获取的哪天的弹幕
     * @param barrages 弹幕列表
     */
    public abstract void handle(String date, ArrayList<Barrage> barrages);
}
