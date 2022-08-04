package github.zimoyin.core.login;

import github.zimoyin.core.cookie.Cookie;
import github.zimoyin.core.cookie.GlobalCookie;
import github.zimoyin.core.exception.CookieNotFoundException;
import github.zimoyin.core.utils.JsonSerializeUtil;
import github.zimoyin.core.utils.net.httpclient.HttpClientResult;
import github.zimoyin.core.utils.net.httpclient.HttpClientUtils;

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

    public static void main(String[] args) throws IOException, CookieNotFoundException {
        Logout.Logout(GlobalCookie.getInstance());
    }
}
