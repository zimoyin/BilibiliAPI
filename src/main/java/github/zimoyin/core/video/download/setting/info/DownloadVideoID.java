package github.zimoyin.core.video.download.setting.info;

import github.zimoyin.core.utils.IDConvert;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class DownloadVideoID {
    private String ID;
    private String BV;
    private long CID;
    private String SSID;
    private String EP;

    public DownloadVideoID(String id) {
        initID(id);
    }

    public DownloadVideoID(String id, long CID) {
        this.ID = id;
        this.CID = CID;
        initID(id);
    }



    public DownloadVideoID setCID(long CID) {
        this.CID = CID;
        return this;
    }

    private void initID(String id) {
        this.ID = id;
        if (id.trim().length() <= 2) throw new IllegalArgumentException("不合法的id，无法判断的id类型");
        String pr = id.trim().substring(0, 2).toUpperCase();
        switch (pr.toUpperCase()) {
            case "BV":
                this.BV = id;
                break;
            case "AV":
                this.BV = IDConvert.AvToBv(id);
                break;
            case "EP":
                this.EP = id;
                break;
            case "SS":
                this.SSID = id;
                break;
            default:
                throw new IllegalArgumentException("不合法的id，无法判断的id类型");
        }
    }

    /**
     * 是否是番剧
     */
    public boolean isEpisode() {
        return EP != null || SSID != null;
    }

    public boolean isVideo() {
        return !isEpisode();
    }

    public DownloadVideoID setEP(long epId) {
        this.EP = "ep"+epId;
        return this;
    }

    public DownloadVideoID setSSID(long season_id) {
        this.SSID = "ss"+season_id;
        return this;
    }
}
