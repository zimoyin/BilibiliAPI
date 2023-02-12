import github.zimoyin.bili.barrage.barrage.video.RealTimeBarrage;
import github.zimoyin.bili.barrage.data.Barrage;
import github.zimoyin.bili.cookie.Cookie;
import github.zimoyin.bili.cookie.GlobalCookie;
import github.zimoyin.bili.cookie.WebCookie;
import github.zimoyin.bili.exception.CookieNotFoundException;
import github.zimoyin.bili.utils.net.httpclient.HttpClientUtils;
import github.zimoyin.bili.video.download.download.downloader.UserVideoDownload;
import github.zimoyin.bili.video.download.download.downloader.VideoDownload;
import github.zimoyin.bili.video.download.setting.UserVideoSetting;
import github.zimoyin.bili.video.download.setting.VideoSetting;
import github.zimoyin.bili.video.download.setting.info.DownloadVideoID;
import github.zimoyin.bili.video.url.param.Fnval;
import github.zimoyin.bili.video.url.param.ParamBuilder;
import github.zimoyin.bili.video.url.param.QN;

import java.util.HashMap;

public class Main {
    public static void main(String[] args) throws Exception {
//        for (Barrage barrage : new RealTimeBarrage(GlobalCookie.getInstance()).getBarrage("BV1L24y1C7ai")) {
//            System.out.println(barrage);
//        }

        WebCookie cookie = Cookie.parse("buvid3=F10A9E92-166F-D7B0-9492-7A294A427BF241784infoc; i-wanna-go-back=-1; _uuid=A1018A103F-9DFD-7F110-5166-9106B33CDDA10743106infoc; b_nut=100; fingerprint=17deff6fd7526e216ffda059d2f8e5b5; buvid_fp_plain=undefined; buvid_fp=9a65cb139bc7d5fce21895a86694fc41; fingerprint3=835fd079c23731c2fb89f1d9ef9f3bd6; b_ut=5; nostalgia_conf=-1; CURRENT_BLACKGAP=0; CURRENT_FNVAL=4048; bp_video_offset_102664436=729792824416927700; DedeUserID=701615499; DedeUserID__ckMd5=470d902031e88387; rpdid=|(k|mlmJYkuJ0J'uYY)~l)YRJ; CURRENT_QUALITY=127; buvid4=51636527-B758-B365-5058-48023AA5203012762-022062715-pi29DxjWV7cTCy1mnn%2FG7w%3D%3D; PVID=2; innersign=0; b_lsid=101C104BD7_184FB16E96B; SESSDATA=0c937883%2C1686211895%2C1de8e%2Ac2; bili_jct=d1ccd87cd930b6b271c444cff8adb8f2; sid=8ihaj319; bp_video_offset_701615499=737964729840959600");
        HashMap<String, String> headers = new HashMap<String, String>();
        GlobalCookie.getInstance().getCookie().toHeaderCookie(headers);
        System.out.println(HttpClientUtils.doGet("https://api.bilibili.com/x/web-interface/nav", cookie).getContent());
    }
}
