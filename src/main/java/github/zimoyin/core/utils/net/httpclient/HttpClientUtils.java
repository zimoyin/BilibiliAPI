package github.zimoyin.core.utils.net.httpclient;

import github.zimoyin.core.cookie.GlobalCookie;
import github.zimoyin.core.exception.CookieNotFoundException;
import org.apache.http.*;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContextBuilder;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.*;
import java.util.Map.Entry;

/**
 * Description: httpClient工具类
 *
 * @author JourWon
 * @date Created on 2018年4月19日
 */
public class HttpClientUtils {
    /**
     * 在没有Cookie的情况下，是否携带全局Cookie，与防盗链
     */
    public static boolean isGlobalCookie = false;

    // 编码格式。发送编码格式统一用UTF-8
    private static final String ENCODING = "UTF-8";

    // 设置连接超时时间，单位毫秒。
    private static final int CONNECT_TIMEOUT = 6 * 1000;

    // 请求获取数据的超时时间(即响应时间)，单位毫秒。
    private static final int SOCKET_TIMEOUT = 12 * 1000;

    /**
     * 发送get请求；不带请求头和请求参数
     *
     * @param url 请求地址
     * @return
     * @throws Exception
     */
    public static HttpClientResult doGet(String url) throws IOException, NoSuchAlgorithmException, KeyStoreException, URISyntaxException, KeyManagementException {
        return doGet(url, null, null);
    }

    /**
     * 发送get请求；带请求参数
     *
     * @param url    请求地址
     * @param params 请求参数集合
     * @return
     * @throws Exception
     */
    public static HttpClientResult doGet(String url, Map<String, String> params) throws IOException, NoSuchAlgorithmException, KeyStoreException, URISyntaxException, KeyManagementException {
        return doGet(url, null, params);
    }

    /**
     * 发送get请求；带请求头和请求参数
     *
     * @param url     请求地址
     * @param headers 请求头集合
     * @param params  请求参数集合
     * @return
     * @throws Exception
     */
    public static HttpClientResult doGet(String url, Map<String, String> headers, Map<String, String> params) throws IOException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException, URISyntaxException {
        if (headers == null) {
            headers = new HashMap<>();
        }
        //判断是否携带cookie，如果没有就获取全局cookie
        setGlobalCookie(headers);

        //使用 loadTrustMaterial() 方法实现一个信任策略，信任所有证书
        SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
            // 信任所有
            public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                return true;
            }
        }).build();
        //NoopHostnameVerifier类:  作为主机名验证工具，实质上关闭了主机名验证，它接受任何
        //有效的SSL会话并匹配到目标主机。
        HostnameVerifier hostnameVerifier = NoopHostnameVerifier.INSTANCE;
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext, hostnameVerifier);


        // 创建httpClient对象
//        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(sslsf).build();

        // 创建访问的地址
        URIBuilder uriBuilder = new URIBuilder(url);
        if (params != null) {
            Set<Entry<String, String>> entrySet = params.entrySet();
            for (Entry<String, String> entry : entrySet) {
                uriBuilder.setParameter(entry.getKey(), entry.getValue());
            }
        }

        // 创建http对象
        HttpGet httpGet = new HttpGet(uriBuilder.build());
        /**
         * setConnectTimeout：设置连接超时时间，单位毫秒。
         * setConnectionRequestTimeout：设置从connect Manager(连接池)获取Connection
         * 超时时间，单位毫秒。这个属性是新加的属性，因为目前版本是可以共享连接池的。
         * setSocketTimeout：请求获取数据的超时时间(即响应时间)，单位毫秒。 如果访问一个接口，多少时间内无法返回数据，就直接放弃此次调用。
         */
        RequestConfig requestConfig = RequestConfig.custom()
                .setProxy(setProxy())
                .setConnectTimeout(CONNECT_TIMEOUT)
                .setSocketTimeout(SOCKET_TIMEOUT)
                .build();
        httpGet.setConfig(requestConfig);

        httpGet.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/97.0.4692.71 Safari/537.36");
        // 设置请求头
        packageHeader(headers, httpGet);

        // 创建httpResponse对象
        CloseableHttpResponse httpResponse = null;

//        try {
        // 执行请求并获得响应结果
        return getHttpClientResult(httpResponse, httpClient, httpGet);
//        } finally {
        // 释放资源
//			release(httpResponse, httpClient);
//        }
    }

    /**
     * 发送post请求；不带请求头和请求参数
     *
     * @param url 请求地址
     * @return
     * @throws Exception
     */
    public static HttpClientResult doPost(String url) throws IOException {
        return doPost(url, null, null, null);
    }

    /**
     * 发送post请求；带请求参数
     *
     * @param url    请求地址
     * @param params 参数集合
     * @return
     * @throws Exception
     */
    public static HttpClientResult doPost(String url, Map<String, String> params) throws IOException {
        return doPost(url, null, params, null);
    }

    /**
     * 发送post请求；带请求头和请求参数
     *
     * @param url     请求地址
     * @param headers 请求头集合
     * @param params  请求参数集合
     *  HttpEntity reqEntity = MultipartEntityBuilder.create().addPart("file", bin).addPart("comment", comment).build();
     * @return
     * @throws Exception
     * @body 二进制文件参数，用于上传二进制文件，构建方式<br>
     *
     */
    public static HttpClientResult doPost(String url, Map<String, String> headers, Map<String, String> params, HttpEntity body) throws IOException {
//        if (headers == null) {
//            headers = new HashMap<>();
//        }
//        //判断是否携带cookie，如果没有就获取全局cookie
//        setGlobalCookie(headers);



        // 创建httpClient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();

        // 创建http对象
        HttpPost httpPost = new HttpPost(url);
        /**
         * setConnectTimeout：设置连接超时时间，单位毫秒。
         * setConnectionRequestTimeout：设置从connect Manager(连接池)获取Connection
         * 超时时间，单位毫秒。这个属性是新加的属性，因为目前版本是可以共享连接池的。
         * setSocketTimeout：请求获取数据的超时时间(即响应时间)，单位毫秒。 如果访问一个接口，多少时间内无法返回数据，就直接放弃此次调用。
         */
        RequestConfig requestConfig = RequestConfig.custom()
                .setProxy(setProxy())
                .setConnectTimeout(CONNECT_TIMEOUT)
                .setSocketTimeout(SOCKET_TIMEOUT)
                .build();

        httpPost.setConfig(requestConfig);

        // 设置请求头
		/*httpPost.setHeader("Cookie", "");
		httpPost.setHeader("Connection", "keep-alive");
		httpPost.setHeader("Accept", "application/json");
		httpPost.setHeader("Accept-Language", "zh-CN,zh;q=0.9");
		httpPost.setHeader("Accept-Encoding", "gzip, deflate, br");*/
        httpPost.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/97.0.4692.71 Safari/537.36");
        packageHeader(headers, httpPost);

        // 封装请求参数
        packageParam(params, httpPost);
        //封装body
        if (body != null) httpPost.setEntity(body);

        // 创建httpResponse对象
        CloseableHttpResponse httpResponse = null;

//        try {
        // 执行请求并获得响应结果
        return getHttpClientResult(httpResponse, httpClient, httpPost);
//        } finally {
        // 释放资源
//			release(httpResponse, httpClient);
//        }
    }

    /**
     * 发送put请求；不带请求参数
     *
     * @param url 请求地址
     * @return
     * @throws Exception
     */
    public static HttpClientResult doPut(String url) throws IOException {
        return doPut(url);
    }

    /**
     * 发送put请求；带请求参数
     *
     * @param url    请求地址
     * @param params 参数集合
     * @return
     * @throws Exception
     */
    public static HttpClientResult doPut(String url, Map<String, String> params) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPut httpPut = new HttpPut(url);
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(CONNECT_TIMEOUT).setSocketTimeout(SOCKET_TIMEOUT).build();
        httpPut.setConfig(requestConfig);

        packageParam(params, httpPut);

        CloseableHttpResponse httpResponse = null;

//        try {
        return getHttpClientResult(httpResponse, httpClient, httpPut);
//        } finally {
//			release(httpResponse, httpClient);
//        }
    }

    /**
     * 发送delete请求；不带请求参数
     *
     * @param url 请求地址
     * @return
     * @throws IOException
     */
    public static HttpClientResult doDelete(String url) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpDelete httpDelete = new HttpDelete(url);
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(CONNECT_TIMEOUT).setSocketTimeout(SOCKET_TIMEOUT).build();
        httpDelete.setConfig(requestConfig);

        CloseableHttpResponse httpResponse = null;
//        try {
        return getHttpClientResult(httpResponse, httpClient, httpDelete);
//        } finally {
//			release(httpResponse, httpClient);
//        }
    }

    /**
     * 发送delete请求；带请求参数
     *
     * @param url    请求地址
     * @param params 参数集合
     * @return
     * @throws IOException
     */
    public static HttpClientResult doDelete(String url, Map<String, String> params) throws IOException {
        if (params == null) {
            params = new HashMap<String, String>();
        }

        params.put("_method", "delete");
        return doPost(url, params);
    }

    /**
     * Description: 封装请求头
     *
     * @param params
     * @param httpMethod
     */
    public static void packageHeader(Map<String, String> params, HttpRequestBase httpMethod) {
        // 封装请求头
        if (params != null) {
            Set<Entry<String, String>> entrySet = params.entrySet();
            for (Entry<String, String> entry : entrySet) {
                // 设置到请求头到HttpRequestBase对象中
                httpMethod.setHeader(entry.getKey(), entry.getValue());
            }
        }
    }

    /**
     * Description: 封装请求参数
     *
     * @param params
     * @param httpMethod
     * @throws UnsupportedEncodingException
     */
    public static void packageParam(Map<String, String> params, HttpEntityEnclosingRequestBase httpMethod)
            throws UnsupportedEncodingException {
        // 封装请求参数
        if (params != null) {
            List<NameValuePair> nvps = new ArrayList<NameValuePair>();
            Set<Entry<String, String>> entrySet = params.entrySet();
            for (Entry<String, String> entry : entrySet) {
                nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }

            // 设置到请求的http对象中
            httpMethod.setEntity(new UrlEncodedFormEntity(nvps, ENCODING));
        }
    }


    /**
     * 设置代理
     *
     * @return
     */
    private static HttpHost setProxy() {
//        HttpHost proxy= new HttpHost("139.99.237.62",80,"http");
        HttpHost proxy = null;
//        proxy = new HttpHost("39.98.45.168", 8081, "http");
        proxy = null;
        return proxy;
    }

    /**
     * Description: 获得响应结果
     *
     * @param httpResponse
     * @param httpClient
     * @param httpMethod
     * @return
     * @throws Exception
     */
    public static HttpClientResult getHttpClientResult(CloseableHttpResponse httpResponse,
                                                       CloseableHttpClient httpClient,
                                                       HttpRequestBase httpMethod) throws IOException {
        boolean isRelease = false;
        try {
            // 执行请求
            httpResponse = httpClient.execute(httpMethod);
            InputStream input = null;

            // 获取返回结果
            if (httpResponse != null && httpResponse.getStatusLine() != null) {
                HttpClientResult httpClientResult = new HttpClientResult(httpResponse.getStatusLine().getStatusCode(), httpResponse, httpClient);
                httpClientResult.setURI(httpMethod.getURI());
                return httpClientResult;
            }
            //此时是出错了
            isRelease = true;
            HttpClientResult httpClientResult = new HttpClientResult(HttpStatus.SC_INTERNAL_SERVER_ERROR);
            httpClientResult.setURI(httpMethod.getURI());
            return httpClientResult;
        } finally {
            //释放出错的链接
            if (isRelease)
                release(httpResponse, httpClient);
        }
    }

    /**
     * Description: 释放资源
     *
     * @param httpResponse
     * @param httpClient
     * @throws IOException
     */
    public static void release(CloseableHttpResponse httpResponse, CloseableHttpClient httpClient) throws IOException {
        // 释放资源
        if (httpResponse != null) {
            httpResponse.close();
        }
        if (httpClient != null) {
            httpClient.close();
        }
    }

    /**
     * get调用接口
     *
     * @param url
     * @return 字符串
     */
    public static String httpGet(String url) throws IOException {
        return httpGet(url, "utf-8");
    }

    /**
     * get调用接口
     *
     * @param url
     * @param charsetName
     * @return 字符串
     */
    public static String httpGet(String url, String charsetName) throws IOException {
        //创建HttpClient实例
        CloseableHttpClient httpClient = HttpClients.createDefault();
        StringBuffer stringBuffer = new StringBuffer();

        InputStream inputStream = null;
        try {
            //创建get方法连接实例，在get方法中传入待连接地址
            HttpGet httpGet = new HttpGet(url);
            //发起请求，并返回请求响应
            HttpResponse httpResponse = httpClient.execute(httpGet);
            //得到响应实体
            HttpEntity entity = httpResponse.getEntity();
            //得到实体中文件
            inputStream = entity.getContent();

            //读取返回内容
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] b = new byte[6666];
            int code = 0;
            while ((code = inputStream.read(b)) != -1) {
                byteArrayOutputStream.write(b, 0, code);
            }

            return byteArrayOutputStream.toString(charsetName);
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
            httpClient.getConnectionManager().shutdown();
        }
//        return null;  删除可用： throws IOException
    }


    /**
     * 如果请求没有携带Cookie就去获取全局Cookie
     */
    private static void setGlobalCookie(Map<String, String> header) {
        //添加Cookie
        if (header == null) {
            header = new HashMap<>();
        }
        boolean isCookie = header.containsKey("Cookie");
        if (!isCookie && isGlobalCookie) {
            try {
                Object s = GlobalCookie.getInstance().getCookie();
                if (s != null) header.put("Cookie", GlobalCookie.getInstance().getCookie().toString());
            } catch (CookieNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        //添加防盗链
        boolean isReferer = header.containsKey("referer");
        if (!isReferer) header.put("referer", "https://www.bilibili.com");
    }


}