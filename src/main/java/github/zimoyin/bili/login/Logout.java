package github.zimoyin.bili.login;

import github.zimoyin.bili.cookie.Cookie;
import github.zimoyin.bili.utils.JsonSerializeUtil;
import github.zimoyin.bili.utils.net.httpclient.HttpClientResult;
import github.zimoyin.bili.utils.net.httpclient.HttpClientUtils;

import java.io.IOException;
import java.util.HashMap;

/**
 * 退出登录
 */
public class Logout {
    private static final String URL ="http://passport.bilibili.com/login/exit/v2";

    public boolean logout(Cookie cookie) throws IOException{
        return Logout.Logout(cookie);
    }

    public static boolean Logout(Cookie cookie) throws IOException {
        //参数
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("biliCSRF",cookie.getCsrf());
        params.put("gourl","https://www.bilibili.com");
        //请求头
        HashMap<String, String> header = cookie.toHeaderCookie();
        //访问
        HttpClientResult result = HttpClientUtils.doPost(URL,header, params,null);
        //结果校验是否成功
        String content = result.getContent();
        try{
            String read = JsonSerializeUtil.getJsonPath().read(content, "/code/");
            if ("0".equals(read)) return true;
        }catch (Exception e){
            return true;
        }
        return false;
    }
}
