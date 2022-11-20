import github.zimoyin.bili.video.download.download.downloader.UserVideoDownload;
import github.zimoyin.bili.video.download.download.downloader.VideoDownload;
import github.zimoyin.bili.video.download.setting.UserVideoSetting;
import github.zimoyin.bili.video.download.setting.VideoSetting;

public class Main {
    public static void main(String[] args) {

        UserVideoSetting setting = new UserVideoSetting(37974444);
        UserVideoDownload download = new UserVideoDownload(setting);
        download.setListener(info -> System.out.print("\r" + info.getPage().getTitle() + ": " + info.getFileSize() + "/" + info.getFileFinalSize()));
        download.downloadAll();

    }
}
