package github.zimoyin.bili.music.download;

import github.zimoyin.bili.cookie.Cookie;
import github.zimoyin.bili.music.url.MusicURLFormat;
import github.zimoyin.bili.music.url.pojo.url.MusicURLJsonRoot;
import github.zimoyin.bili.utils.net.httpclient.HttpClientResult;
import github.zimoyin.bili.utils.net.httpclient.HttpClientUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.File;
import java.io.IOException;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MusicDownload {
    private long sid;
    private Cookie cookie;
    private MusicURLFormat.QN qn;
    private String path;

    public static void download(long sid) throws IOException {
        //获取下载链接
        MusicURLJsonRoot jsonPojo = new MusicURLFormat().getJsonPojo(sid);
        String url = jsonPojo.getData().getCdns().get(0);
        //合法化下载地址
        String path = "./download";
        File file = new File(path);
        file.mkdirs();
        HttpClientResult result = HttpClientUtils.doGet(url);
        result.toFile(path + "/" + jsonPojo.getData().getTitle()+".flac");
    }

    public void download() throws IOException {
        //获取下载链接
        MusicURLJsonRoot jsonPojo = null;
        if (qn == null) jsonPojo = new MusicURLFormat(cookie).getJsonPojo(sid);
        else jsonPojo = new MusicURLFormat(cookie).getJsonPojo(sid, 255, qn);
        String url = jsonPojo.getData().getCdns().get(0);
        //合法化下载地址
        if (path != null) {
            File file = new File(path);
            file.mkdirs();
        } else {
            path = "./download";
            File file = new File(path);
            file.mkdirs();
        }

        HttpClientResult result = HttpClientUtils.doGet(url);
        result.toFile(path + "/" +  jsonPojo.getData().getTitle()+".flac");
    }
}
