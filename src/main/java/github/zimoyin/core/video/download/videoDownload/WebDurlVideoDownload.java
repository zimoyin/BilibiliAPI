package github.zimoyin.core.video.download.videoDownload;

import github.zimoyin.core.cookie.Cookie;
import github.zimoyin.core.utils.net.httpclient.HttpClientResult;
import github.zimoyin.core.utils.net.httpclient.HttpClientUtils;

import github.zimoyin.core.utils.net.httpclient.NetFileUtil;
import github.zimoyin.core.utils.net.httpclient.VideoDownloadUtil;
import github.zimoyin.core.video.download.DownloadControl;
import github.zimoyin.core.video.download.DownloadResult;
import github.zimoyin.core.video.url.pojo.*;

import java.io.*;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.*;

/**
 *
 * Durl 格式视频下载（禁止用户创建该类实例，应该通过VideoDownload类 使用该类）
 */
@Deprecated
@lombok.Data
public class WebDurlVideoDownload{
    private volatile VideoURLJsonRoot json;
    private volatile VideoURLJsonRoot.Data data;
    private volatile HashMap<String, String> header = new HashMap<>();

    /**
     * 登录的Cookie
     */
    private volatile Cookie cookie;
    /**
     * 文件保存路径，路径必须为一个文件夹
     */
    private volatile String path="./download";


    /**
     * 下载线程数
     */
    private volatile int threadCount = 32;

    public WebDurlVideoDownload(VideoURLJsonRoot json, String path) {
        header.put("referer", "https://www.bilibili.com");
        this.json = json;
        this.data = json.getData();
        this.path = path;
    }


    /**
     * 下载durl 类型视频
     * 如果：产生分段情况，将$.data.durl[1-n].url或$.data.durl[1-n].backup_url[0]中的内容作为url进行GET操作, 如果有多个视频, 需要手动合并处理
     *
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws KeyStoreException
     * @throws URISyntaxException
     * @throws KeyManagementException
     */
    public boolean durlDownload(String fileName) throws IOException, NoSuchAlgorithmException, KeyStoreException, URISyntaxException, KeyManagementException {
        return durlDownload(fileName, null);
    }

    /**
     * 下载durl 类型视频
     * 如果：产生分段情况，将$.data.durl[1-n].url或$.data.durl[1-n].backup_url[0]中的内容作为url进行GET操作, 如果有多个视频, 需要手动合并处理
     *
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws KeyStoreException
     * @throws URISyntaxException
     * @throws KeyManagementException
     */
    public boolean durlDownload(String fileName, DownloadControl control) throws IOException, NoSuchAlgorithmException, KeyStoreException, URISyntaxException, KeyManagementException {
        //视频的格式
        String format = data.getFormat();
        if (format.contains("flv")) format = "flv";
        if (format.contains("mp4")) format = "mp4";
        //文件保存位置
        String filePath = path + "//" + fileName + "." + format;
        //flv部分视频有分段，这里将分段一并下载，如果有的话
        //目前作者并没有找到分段视频，所以这里不提供视频合并(目前统计数据从2022 - 2014，时长》30+，每年稿件分别》6 )
        //flv视频下载速率不到 1kb，平均在650 Byte
        int count = 0;//统计下载的视频分段
        int countLe = data.getDurl().size();//统计下载的视频分段(视频个数通常为1
        for (Durl durl : data.getDurl()) {
            //设置文件保存路径
            if (countLe != 1) filePath = path + "//" + fileName + "." + count + "." + format;
            //文件的URL
            String url = durl.getUrl().toString();
            //文件大小放入控制器中
//            long fileSize = NetFileUtil.getFileLength2(url, header);//获取文件大小
            long fileSize = durl.getSize();//获取文件大小
            control.setFileSize(fileSize);
            HttpClientResult result = HttpClientUtils.doGet(url,header,new HashMap<>());
            result.toFile(filePath,control);
            count++;
        }
        return true;
    }

    /**
     * 多线程下载 durl 类型视频,并阻塞main线程
     * 如果：产生分段情况，将$.data.durl[1-n].url或$.data.durl[1-n].backup_url[0]中的内容作为url进行GET操作, 如果有多个视频, 需要手动合并处理
     *
     * @param isWaitTakeFinish 是否在任务未下载完成前阻塞主线程，直到任务下载完毕
     * @return
     * @throws IOException
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public ArrayList<Future<DownloadResult>> durlDownloadThread(boolean isWaitTakeFinish, int threadCount, String fileName) throws IOException, ExecutionException, InterruptedException, NoSuchAlgorithmException, KeyStoreException, URISyntaxException, KeyManagementException {
        return durlDownloadThread(isWaitTakeFinish,threadCount,fileName,null);
    }


    /**
     * 多线程下载 durl 类型视频,并阻塞main线程
     * 如果：产生分段情况，将$.data.durl[1-n].url或$.data.durl[1-n].backup_url[0]中的内容作为url进行GET操作, 如果有多个视频, 需要手动合并处理
     *
     * @param isWaitTakeFinish 是否在任务未下载完成前阻塞主线程，直到任务下载完毕
     * @return
     * @throws IOException
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public ArrayList<Future<DownloadResult>> durlDownloadThread(boolean isWaitTakeFinish,int threadCount,String fileName,DownloadControl control) throws IOException, ExecutionException, InterruptedException, NoSuchAlgorithmException, KeyStoreException, URISyntaxException, KeyManagementException {
        this.threadCount = threadCount;
        //创建线程池
        ExecutorService executorService = Executors.newFixedThreadPool(32);
        //创建任务结果队列
        ArrayList<Future<DownloadResult>> tasks = new ArrayList<>();
        //视频的格式
        String format = data.getFormat();
        if (format.contains("flv")) format = "flv";
        if (format.contains("mp4")) format = "mp4";
        //统计下载的视频分段
        int count = 1;
        //总共有多少视频段
        int countLe = data.durl.size();
        //多线程下载文件
        //flv部分视频有分段，这里将分段一并下载，如果有的话
        for (Durl durl : data.getDurl()) {
            //文件路径
            String filePath = path + "//" + fileName + "." + format;
            if (countLe != 1) filePath = path + "//" + fileName + "." + count + "." + format;
            String finalFilePath = filePath;

            String url = durl.getUrl().toString();
            long fileSize = durl.getSize();
            control.setFileSize(fileSize);
            //下载文件
            ArrayList<Future<DownloadResult>> futures = VideoDownloadUtil.downloadFileThread(executorService, url, filePath, fileSize,threadCount,header,json.getBv(),control);
            tasks.addAll(futures);
            //视频文件+1
            count++;
        }
        //等待所有的任务完成,完成后才执行结束线程池代码
        if (isWaitTakeFinish) for (Future<DownloadResult> task : tasks) task.get();
        //结束线程池
        executorService.shutdown();
        return tasks;
    }


    public void setCookie(Cookie cookie) {
        this.cookie = cookie;
        header.clear();
        cookie.setCookieToHeader(header);
        header.put("referer", "https://www.bilibili.com");
    }
}
