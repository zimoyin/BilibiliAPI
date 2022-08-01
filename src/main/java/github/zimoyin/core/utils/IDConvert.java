package github.zimoyin.core.utils;

import github.zimoyin.core.exception.Code;
import github.zimoyin.core.exception.CodeException;
import github.zimoyin.core.utils.net.httpclient.HttpClientResult;
import github.zimoyin.core.utils.net.httpclient.HttpClientUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 实现 BV 转 AV
 * AV 转 BV
 * BV/AV 转CID
 */
public class IDConvert {
    private static String table="fZodR9XQDSUm21yCkr6zBqiveYah8bt4xsWpHnJE7jL5VG3guMTKNPAwcF";
    private static ArrayList<Map<String,Object>> tr=new ArrayList();
    private static int s[] ={11,10,3,8,4,6};
    private static double xor=177451812.0;
    private static double add= 8728348608.0;


    /**
     * BV转AV
     * @param bv
     * @return
     */
    public static String BvToAv(String bv){
        return String.valueOf("AV"+BvToAvNumber(bv));
    }

    /**
     * BV转AV
     * @param bv
     * @return
     */
    public static int BvToAvNumber(String bv){
        char a[]=table.toCharArray();
        for (int i=0;i<58;i++){
            Map<String,Object> map=new HashMap<>();
            map.put(String.valueOf(a[i]) ,i);
            tr.add(map);
        }

        double r=0;
        char xe[]=bv.toCharArray();
        for(int i=0;i<6;i++){
            for(Map c:tr){
                for(Object key : c.keySet()){
                    if(key.equals(String.valueOf(xe[s[i]])) ){
                        Object value = c.get(key);
                        r+=Integer.valueOf(value.toString())*Math.pow(58,i);
                    }
                }
            }
        }
        int ru = (int)(r-add)^(int)(xor);
        return ru;
    }

    /**
     * Av 转 BV
     * @param av
     * @return
     */
    public static String AvToBv(String av){
        av = av.replaceAll("\\D.","");
        int temp= Integer.parseInt(av);
        char a[]=table.toCharArray();
        char arr[]={'B','V','1',' ',' ','4',' ','1',' ','7',' ',' '};
        double r=(Integer.valueOf(av)^(int)(xor))+add;
        for(int i=0;i<6;i++){
            arr[s[i]]=a[(int) (Math.floor(r/(Math.pow(58,i))) %58)];
            int dd= (int) (Math.floor(r/(Math.pow(58,i))) %58);
        }
        return String.valueOf(arr);
    }

    /**
     * Av转为CID
     * @return
     */
    public static String AvToCID(String av) throws CodeException, IOException, NoSuchAlgorithmException, KeyStoreException, URISyntaxException, KeyManagementException {
        return BvToCID(AvToBv(av));
    }

    /**
     * BV转为CID
     * @param bv
     * @return
     * @throws Exception
     */
    public static String BvToCID(String bv) throws IOException, NoSuchAlgorithmException, KeyStoreException, URISyntaxException, KeyManagementException, CodeException {
        final String CID_URL_TEMP = "https://api.bilibili.com/x/player/pagelist?bvid=%s&jsonp=jsonp";
        String url = String.format(CID_URL_TEMP, bv);
        HttpClientResult httpClientResult = HttpClientUtils.doGet(url);
        String content = httpClientResult.getContent();
        //code 为 0 说明请求成功，如果不是就抛出异常
        Code.throwCodeException(content,"服务器接口异常 code 不为 0 (成功)\r\n 访问URL："+url+"\r\n");
        String cid = JsonSerializeUtil.getJsonPath().read(content, "/data/0/cid");
        return cid;
    }


}
