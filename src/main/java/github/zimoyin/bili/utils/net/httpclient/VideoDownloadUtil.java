package github.zimoyin.bili.utils.net.httpclient;

import github.zimoyin.bili.video.download.download.DownloadResult;
import github.zimoyin.bili.video.download.download.event.DownloadingINFO;
import lombok.Data;
import org.apache.logging.log4j.LogManager;


import java.io.*;
import java.net.URL;
import java.util.HashMap;


/**
 * 多线程文件下载
 */
@Data
public class VideoDownloadUtil {

    private String bv;

    public VideoDownloadUtil() {
    }

    public VideoDownloadUtil(String bv) {
        this.bv = bv;
    }

    //    private static Logger logger = LoggerFactory.getLogger(VideoDownloadUtil.class);
    private final static org.apache.logging.log4j.Logger logger = LogManager.getLogger(VideoDownloadUtil.class);

    /**
     * 创建一个指定大小的随机文件
     *
     * @param path
     * @param size
     */
    public static void createRandFile(String path, long size) throws IOException {
        //创建文件
        RandomAccessFile randomAccessFile = null;
        try {
            randomAccessFile = new RandomAccessFile(path, "rw");
            randomAccessFile.setLength(size);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            if (randomAccessFile != null) randomAccessFile.close();
        }
    }



    /**
     * 从断点处下载文件
     *
     * @param url      文件的URL地址
     * @param filePath 文件保存路径
     * @param start    开始位置
     * @param end      结束位置
     * @param info
     * @return DownloadResult 下载结果
     */
    public static DownloadResult downloadPartFile(String url,
                                                  String filePath,
                                                  long start,
                                                  long end,
                                                  DownloadingINFO info
    ) throws IOException {
        if (info == null) throw new NullPointerException("无法获取到下载信息");
        String bv = info.getPage().getID().getBV();
        //构建单次请求头
        HashMap<String, String> headerCope = new HashMap<>();
        headerCope.put("referer","http://www.bilibili.com");
        headerCope.put("Range", "bytes=" + start + "-" + end);
        //下载结果对象
        DownloadResult downloadResult = new DownloadResult();
        downloadResult.setStart0(start);
        downloadResult.setEnd(end);
        downloadResult.setThreadName(Thread.currentThread().getName());
        downloadResult.setBv(bv);
        downloadResult.setFilePath(filePath);

        //创建随机文件对象
        RandomAccessFile randFile = null;
        InputStream inputStream = null;
        HttpClientResult result = null;
        try {
            randFile = new RandomAccessFile(filePath, "rw");
            //设置字节输出的开始位置
            randFile.seek(start);
            //获取网络流
            result = HttpClientUtils.doGet(url, headerCope, new HashMap<>());
            inputStream = result.getInputStream();
            //返回的数据大小
            int code = 0;
            //缓存区：缓冲区大小为最大数据包大小的三分之二。因为通常数据包负载不会达到65535
            byte[] bytes = new byte[65535 / 3 * 2];
            //写入文件
            while ((code = inputStream.read(bytes)) != -1) {
                if (info.isStop()) break;
                randFile.write(bytes, 0, code);
                info.addFileSize(code);
            }
            info.threadFinish();
        } catch (Exception e) {
            downloadResult.setDownloadFinish(false);
            downloadResult.setException(e);
            return downloadResult;
        } finally {
            info.threadFinish();
            downloadResult.setPointer(start);
            //要保证资源被关闭
            close(randFile, inputStream, result);
        }
        downloadResult.setDownloadFinish(true);
        return downloadResult;
    }

    private static void close(RandomAccessFile randFile, InputStream inputStream, HttpClientResult result) throws IOException {
        if (result != null) result.release();
        try {
            if (inputStream != null) inputStream.close();
        } catch (IOException e) {
            throw e;
        }
        try {
            if (randFile != null) randFile.close();
        } catch (IOException e) {
            throw e;
        }
    }

    /**
     * 下载文件
     * @param path
     * @param url
     * @param info
     * @throws IOException
     */
    public static void downloadFile(String path, URL url, DownloadingINFO info) throws IOException {
        //TODO
        InputStream inputStream = null;
        HttpClientResult result;
        try {
            result = HttpClientUtils.doGet(url.toString());
            inputStream = result.getInputStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try (FileOutputStream outputStream = new FileOutputStream(path)) {
            int code = 0;
            byte[] bytes = new byte[1024 * 1021 * 3];
            while ((code = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, code);
                info.addFileSize(code);
                if (info.isStop()) break;
            }
            info.threadFinish();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }finally {
            info.threadFinish();
            try {
                result.release();
            } catch (IOException e) {
                throw e;
            }
            try {
                inputStream.close();
            } catch (IOException e) {
                throw e;
            }
        }

    }
}
