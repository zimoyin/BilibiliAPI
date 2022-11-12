package github.zimoyin.core.download.setting;

import github.zimoyin.core.video.url.data.QN;
import lombok.Data;

@Data
public abstract class Setting {
    private String id;
    private String bvid;
    private String cid;
    private String ep;
    private String ssid;
    private QN qn;
}
