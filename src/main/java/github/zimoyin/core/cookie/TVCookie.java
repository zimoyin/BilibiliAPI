package github.zimoyin.core.cookie;

import com.alibaba.fastjson.JSONObject;

import java.io.IOException;
import java.util.HashMap;

public class TVCookie extends Cookie {
    private static final long serialVersionUID = 2168152194164783950L;

//    mid	num	登录用户mid
//    access_token	str	APP登录Token
//    refresh_token	str	APP刷新Token
//    expires_in	num	有效时间	单位为秒 一般为30天

    //登录用户mid
    private String mid = "mid";
    //APP登录Token
    private String access_token = "access_token";
    //APP刷新Token
    private String refresh_token = "refresh_token";
    //有效时间	单位为秒 一般为30天
    private String expires_in = "expires_in";


    public TVCookie() {
    }

    public TVCookie(String mid, String access_token, String refresh_token, String expires_in) {
        this.mid = mid;
        this.access_token = access_token;
        this.refresh_token = refresh_token;
        this.expires_in = expires_in;
    }

    public String getMid() {
        return get(mid);
    }

    public void setMid(String mid) {
        put(this.mid, mid);
    }

    public String getAccess_token() {
        return get(access_token);
    }

    public void setAccess_token(String access_token) {
        put(this.access_token, access_token);
    }

    public String getRefresh_token() {
        return get(refresh_token);
    }

    public void setRefresh_token(String refresh_token) {
        put(this.refresh_token, refresh_token);
    }

    public String getExpires_in() {
        return get(expires_in);
    }

    public void setExpires_in(String expires_in) {
        put(this.expires_in, expires_in);
    }


    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public JSONObject toJSONString() {
        return super.toJSONString();
    }

    @Override
    public void writeCookieToJson(String filePath) throws Exception {
        super.writeCookieToJson(filePath);
    }

    @Override
    public void serializable(String filePath) throws IOException {
        super.serializable(filePath);
    }

    @Override
    public void setCookieToHeader(HashMap<String, String> header) {
        super.setCookieToHeader(header);
    }
}
