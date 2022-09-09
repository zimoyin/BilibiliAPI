/**
  * Copyright 2022 json.cn 
  */
package github.zimoyin.core.music.info.pojo.author;
import java.util.List;

/**
 * Auto-generated: 2022-08-30 10:52:44
 *
 * @author json.cn (i@json.cn)
 * @website http://www.json.cn/java2pojo/
 */
@lombok.Data
public class MusicAuthorJsonRoot {

    private int code;
    private String msg;
    private List<Data> data;


    @lombok.Data
    public class Data {
        /**
         * 成员列表
         */
        private List<AuthorList> list;
        /**
         * 成员类型代码
         * 1：歌手
         * 2：作词
         * 3：作曲
         * 4：编曲
         * 5：后期/混音
         * 7：封面制作
         * 8：音源
         * 9：调音
         * 10：演奏
         * 11：乐器
         * 127：UP主
         */
        private int type;
    }
}