package github.zimoyin.core.utils.net.httpclient;

import org.apache.http.Header;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class NetFileUtil {
    /**
     * 获取网络文件大小
     *
     * @return
     * @throws IOException
     */
    public static long getFileLength(String url1) throws IOException {
        int length = 0;
        URL url;
        try {
            url = new URL(url1);
            HttpURLConnection urlcon = (HttpURLConnection) url.openConnection();
            //根据响应获取文件大小
            length = urlcon.getContentLength();
            urlcon.disconnect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return length;
    }

    /**
     * 获取网络文件大小
     *
     * @return
     * @throws IOException
     */
    public static long getFileLength2(String url, Map<String, String> headers) throws IOException, NoSuchAlgorithmException, KeyStoreException, URISyntaxException, KeyManagementException {
        HttpClientResult result = HttpClientUtils.doGet(url, headers, new HashMap<>());
        Header[] headers1 = result.getResponse().getHeaders("Content-Length");
        for (Header header : headers1) {
            return Long.parseLong(header.getValue());
        }
        return 0;
    }
}
