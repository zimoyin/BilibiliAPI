package github.zimoyin.core.utils.net.httpclient;

import github.zimoyin.core.utils.JsonSerializeUtil;

import java.io.IOException;
import java.util.HashMap;

public class ShortURL {
    /**
     *
     * @param url
     * @return
     * @throws IOException
     */
    public String getShortURL(String url){
        HashMap<String, String> params = new HashMap<String, String>();

        params.put("url",url);
        params.put("api_key","9a90f918dbfaa7b6bbfcbc97e7f85a22");
        try {
            HttpClientResult result = HttpClientUtils.doPost("http://dlj.bz/api/v2/short", params);
            String content = result.getContent();
            String read = JsonSerializeUtil.getJsonPath().read(content, "short");
            return read;
        }catch (Exception e){
            return url;
        }
    }
}
