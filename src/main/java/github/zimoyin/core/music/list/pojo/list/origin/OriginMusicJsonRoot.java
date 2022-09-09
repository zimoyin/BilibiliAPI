package github.zimoyin.core.music.list.pojo.list.origin;


import github.zimoyin.core.music.list.pojo.list.my.MyList;

import java.util.List;

public class OriginMusicJsonRoot {
    private int code;
    private String msg;
    private Data data;

    @lombok.Data
    public class Data {

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
