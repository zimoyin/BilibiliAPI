package github.zimoyin.bili.music.list.pojo.list.hot;


import github.zimoyin.bili.music.list.pojo.list.my.MyList;

import java.util.List;

public class HotMusicJsonRoot {
    private int code;
    private String msg;
    private Data data;


    @lombok.Data
    public static class Data{

        /**
         * 当前页码
         */
        private int curPage;
        /**
         * 总计页数
         */
        private int pageCount;
        /**
         * 总计收藏夹数
         */
        private int totalSize;
        /**
         * 当前页面项数
         */
        private int pageSize;
        /**
         * 歌单列表
         */
        private List<MyList> data;
    }
}
