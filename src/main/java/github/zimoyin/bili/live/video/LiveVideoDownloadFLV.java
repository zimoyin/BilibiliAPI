package github.zimoyin.bili.live.video;

import java.io.IOException;
import java.net.URL;

/**
 * 将直播保存到本地FLV格式（注意磁盘大小，会撑爆的）
 * 暂时不实现
 */
public abstract class LiveVideoDownloadFLV {
    /**
     * 将视频保存到本地
     * @param url
     * @throws IOException
     */
    public abstract void download(URL url,String filePath) throws IOException;
}
