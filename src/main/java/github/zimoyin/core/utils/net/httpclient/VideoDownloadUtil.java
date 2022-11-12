package github.zimoyin.core.utils.net.httpclient;

import github.zimoyin.core.exception.DownloadException;
import github.zimoyin.core.utils.AllocateBytes;
import github.zimoyin.core.video.download.DownloadControl;
import github.zimoyin.core.video.download.DownloadResult;
import lombok.Data;
import org.apache.logging.log4j.LogManager;


import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;


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
     * 多线程下载文件，不阻断main线程
     *
     * @param executorService 线程池
     * @param url             文件的URL
     * @param filePath        文件保存位置
     * @param fileSize        文件大小
     * @param threadCount     线程数
     * @param header          请求头
     * @param bv              视频的bv
     * @return 未来结果列表
     * @throws IOException
     */
    public static ArrayList<Future<DownloadResult>> downloadFileThread(ExecutorService executorService, String url, String filePath, long fileSize,
                                                                       int threadCount, HashMap<String, String> header, String bv,
                                                                       DownloadControl downloadControl) throws IOException {
//        logger.debug("=========================================================");
//        logger.debug("开启一个多线程下载任务");
//        logger.debug("URL:{}",url);
//        logger.debug("保存路径：{}",filePath);
//        logger.debug("文件大小：{}",fileSize);
//        logger.debug("开启线程数：{}",threadCount);
//        logger.debug("控制器描述线程数：{}",downloadControl.getThreadCount());
//        logger.debug("控制器描述实际线程数：{}",downloadControl.getFinalThreadCount());
//        logger.debug("=========================================================");

        if (threadCount != downloadControl.getThreadCount() &&
                downloadControl.getFinalThreadCount() >= downloadControl.getThreadCount()) {
            throw new DownloadException("禁止下载 开启文件下载的线程数 " + threadCount + " 与控制器中描述的线程数" + downloadControl.getThreadCount() +
                    "不符,并且控制器中描述的最终线程数" + downloadControl.getFinalThreadCount() + "小于描述线程数 " + downloadControl.getThreadCount());
        }

        //将文件下载线程池放入控制器中进行托管
        downloadControl.setDownloadExecutorService(executorService);
        //创建任务结果队列
        ArrayList<Future<DownloadResult>> tasks = new ArrayList<>();
        //创建随机文件
        createRandFile(filePath, fileSize);
        //为视频文件分段文件段
        HashMap<Long, Long> index = AllocateBytes.CalculationBytes(threadCount, fileSize);
        //为这个文件进行分段下载
        index.forEach((start, end) -> {
            //开始下载文件段
            Future future = executorService.submit(new Callable<DownloadResult>() {
                @Override
                public DownloadResult call() throws Exception {
                    return downloadPartFile(url, filePath, start, end, header, bv, downloadControl);
                }
            });
            //添加未来任务到集合
            tasks.add(future);
        });

        return tasks;
    }


    /**
     * 从断点处下载文件
     *
     * @param url      文件的URL地址
     * @param filePath 文件保存路径
     * @param start    开始位置
     * @param end      结束位置
     * @param header   请求头
     * @param bv       视频的bv
     * @param control  下载控制器
     * @return DownloadResult 下载结果
     */
    public static DownloadResult downloadPartFile(String url, String filePath,
                                                  long start, long end,
                                                  HashMap<String, String> header, String bv,
                                                  DownloadControl control) throws IOException {
        //构建单次请求头
        HashMap<String, String> headerCope = new HashMap<>(header);
        headerCope.put("Range", "bytes=" + start + "-" + end);
        //下载结果对象
        DownloadResult downloadResult = new DownloadResult();
        downloadResult.setStart0(start);
        downloadResult.setEnd(end);
        downloadResult.setThreadName(Thread.currentThread().getName());
        if (bv == null) {
            bv = "";
        }
        downloadResult.setBv(bv);
        downloadResult.setFilePath(filePath);

        //创建随机文件对象
        RandomAccessFile randFile = null;
        InputStream inputStream = null;
        HttpClientResult result = null;
        //文件开始写的位置
        long len = start;
        try {
            randFile = new RandomAccessFile(filePath, "rw");
            //设置字节输出的开始位置
            randFile.seek(len);
            //获取网络流
            result = HttpClientUtils.doGet(url, headerCope, new HashMap<>());
            inputStream = result.getInputStream();
            //返回的数据大小
            int code = 0;
            //统计下载了多少字节
            int count = 0;
            //缓存区：缓冲区大小为最大数据包大小的三分之二。因为通常数据包负载不会达到65535
            byte b[] = new byte[65535 / 3 * 2];
            //写入文件
            while ((code = inputStream.read(b)) != -1) {

                randFile.write(b, 0, code);
                len += code;
                count += code;
                if (control != null) {
                    //如果出现停止下载标准就停止下载
                    if (control.isStop()) break;
                    control.setLength(code, count, end - start);
                }
            }
        } catch (Exception e) {
            downloadResult.setDownloadFinish(false);
            downloadResult.setException(e);
            return downloadResult;
        } finally {
            downloadResult.setStart(len);
            //告诉线程池结束了一样线程
            if (control != null) control.finish();
            //要保证资源被关闭
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
        downloadResult.setDownloadFinish(true);
        return downloadResult;
    }

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


    public static void downloadFile(String path, URL url) throws IOException {
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
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }finally {
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
