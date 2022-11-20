package github.zimoyin.bili.video.download.setting.info;

import java.util.ArrayList;

public interface VideoInfoListInterface {
    DownloadVideoInfo getPage(int page);
    ArrayList<DownloadVideoInfo> getPages();
}
