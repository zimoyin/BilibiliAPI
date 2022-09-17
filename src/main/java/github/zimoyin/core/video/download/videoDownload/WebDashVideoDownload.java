package github.zimoyin.core.video.download.videoDownload;

import github.zimoyin.core.cookie.Cookie;
import github.zimoyin.core.utils.net.httpclient.HttpClientResult;
import github.zimoyin.core.utils.net.httpclient.HttpClientUtils;
import github.zimoyin.core.utils.net.httpclient.NetFileUtil;
import github.zimoyin.core.utils.net.httpclient.VideoDownloadUtil;
import github.zimoyin.core.utils.shell.FFmpeg;
import github.zimoyin.core.video.download.DashTemplate;
import github.zimoyin.core.video.download.DownloadControl;
import github.zimoyin.core.video.download.DownloadResult;
import github.zimoyin.core.video.url.pojo.*;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


/**
 * dash格式视频下载器，并提供了合并方法 （禁止用户创建该类实例，应该通过VideoDownload类 使用该类）
 */
@lombok.Data
public class WebDashVideoDownload {
    private volatile VideoURLJsonRoot json;
    private volatile VideoURLJsonRoot.Data data;
    private volatile HashMap<String, String> header = new HashMap<>();

    private volatile ArrayList<String> filePaths = new ArrayList<>();

    /**
     * 登录的Cookie
     */
    private volatile Cookie cookie;
    /**
     * 文件保存路径，路径必须为一个文件夹
     */
    private volatile String path;

    /**
     * 下载线程数
     */
    private volatile int threadCount = 32;

    public WebDashVideoDownload(VideoURLJsonRoot json, String path) {
        header.put("referer", "https://www.bilibili.com");
        this.json = json;
        this.data = json.getData();
        this.path = path;
    }

    public void setCookie(Cookie cookie) {
        this.cookie = cookie;
        header.clear();
        cookie.setCookieToHeader(header);
        header.put("referer", "https://www.bilibili.com");
    }


    /**
     * 下载
     *
     * @param tem DashTemplate 预期类型视频
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws KeyStoreException
     * @throws URISyntaxException
     * @throws KeyManagementException
     */
    public boolean dashDownload(DashTemplate tem, String fileName) throws IOException, NoSuchAlgorithmException, KeyStoreException, URISyntaxException, KeyManagementException {
        return dashDownload(tem, fileName, null);
    }

    /**
     * 下载
     *
     * @param tem DashTemplate 预期类型视频
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws KeyStoreException
     * @throws URISyntaxException
     * @throws KeyManagementException
     */
    public boolean dashDownload(DashTemplate tem, String fileName, DownloadControl control) throws IOException, NoSuchAlgorithmException, KeyStoreException, URISyntaxException, KeyManagementException {
        //根据模板挑选预期的音频和视频
        ArrayList<Media> select = select(tem);
        //因为要下载两个文件所以要把线程数开到2倍，但是实际线程数还是不变。这里只是为了统计完成下载的线程数为2倍才结束线程池
        //设置线程完成任务次数
        control.setFinalThreadCount(control.getThreadCount());
        control.setThreadCount(control.getThreadCount() * 2);
        //下载音频和视频
        for (Media media : select) {
            String format = media.getMimeType().replaceAll("/", "_");
            if (media.getBackupUrl() != null) {
                URL url = media.getBackupUrl().get(0);
                String filePath = path + "//" + fileName + "." + format + ".m4s";
                control.setFileSize(NetFileUtil.getFileLength2(url.toString(), header));
                filePaths.add(filePath);
                downloadMedia(url.toString(), filePath, control);
            } else {
                URL url = media.getBaseUrl();
                String filePath = path + "//" + fileName + "." + format + ".m4s";
                control.setFileSize(NetFileUtil.getFileLength2(url.toString(), header));
                filePaths.add(filePath);
                downloadMedia(url.toString(), filePath, control);
            }
        }
        return true;
    }

    private void downloadMedia(String url, String filePath, DownloadControl control) throws IOException, NoSuchAlgorithmException, KeyStoreException, URISyntaxException, KeyManagementException {
        HttpClientResult result = HttpClientUtils.doGet(url, header, new HashMap<>());
        result.toFile(filePath, control);
    }

    /**
     * 多线程下载
     *
     * @param isWaitTakeFinish 是否阻塞主线程等待下载完毕
     * @param threadCount      线程数
     * @param tem              DashTemplate 预期类型视频
     * @param fileName         文件名称
     * @return
     * @throws IOException
     * @throws ExecutionException
     * @throws InterruptedException
     * @throws NoSuchAlgorithmException
     * @throws KeyStoreException
     * @throws URISyntaxException
     * @throws KeyManagementException
     */
    public ArrayList<Future<DownloadResult>> dashDownloadThread(boolean isWaitTakeFinish, int threadCount, DashTemplate tem, String fileName, DownloadControl control)
            throws IOException, ExecutionException, InterruptedException, NoSuchAlgorithmException, KeyStoreException, URISyntaxException, KeyManagementException {
        //根据模板挑选预期的音频和视频
        ArrayList<Media> select = select(tem);
        //创建线程池
        ExecutorService executorService = Executors.newFixedThreadPool(control.getThreadCount());
        //设置线程完成任务次数
        control.setFinalThreadCount(control.getThreadCount());
        control.setThreadCount(control.getThreadCount() * 2);
        //任务列表
        ArrayList<Future<DownloadResult>> tasks = new ArrayList<>();
        //下载音频和视频
        for (Media media : select) {
            String format = media.getMimeType().replaceAll("/", "_");



            //有备用url就先用备用url
            if (media.getBackupUrl() != null) {
                //视频的url
                String url = media.getBackupUrl().get(0).toString();
                //文件保存路径
                String filePath = path + "//" + fileName + "." + format + ".m4s";
                //文件路径添加到路径表，用来合并视频时找到文件
                filePaths.add(filePath);
                //文件大小
                long fileSize = NetFileUtil.getFileLength2(url.toString(), header);
                control.setFileSize(fileSize);
                //开始下载
                ArrayList<Future<DownloadResult>> futures = durlDownloadThread(executorService, isWaitTakeFinish, threadCount, filePath, url, fileSize, control);
                //正在进行的下载任务添加到任务集合
                tasks.addAll(futures);
            } else {
                String url = media.getBaseUrl().toString();
                String filePath = path + "//" + fileName + "." + format + ".m4s";
                filePaths.add(filePath);
                long fileSize = NetFileUtil.getFileLength2(url.toString(), header);
                control.setFileSize(fileSize);
                ArrayList<Future<DownloadResult>> futures = durlDownloadThread(executorService, isWaitTakeFinish, threadCount, filePath, url, fileSize, control);
                tasks.addAll(futures);
            }
        }

        //等待所有的任务完成,完成后才执行结束线程池代码
        if (isWaitTakeFinish) for (Future<DownloadResult> task : tasks) task.get();
        //结束线程池
        executorService.shutdown();
        return tasks;
    }

    /**
     * 合并音频和视频
     */
    public String merge() throws Exception {
        return merge(filePaths.get(0), filePaths.get(1));
    }

    /**
     * 合并音频和视频
     */
    public String merge(String videoFilePath, String audioFilePath) throws Exception {
        //文件类型
        String fileType = videoFilePath.substring(videoFilePath.lastIndexOf("_") + 1, videoFilePath.lastIndexOf("."));
        //分析文件名称
        String fileName = new File(videoFilePath).getName();
        fileName = fileName.substring(0, fileName.indexOf("."));

        //输出路径
        String out = path + "/" + fileName + "." + fileType;
        return merge(videoFilePath, audioFilePath, out);


//        FFmpeg.exec(" -i "+videoFilePath+" -i "+audioFilePath+" -c copy ")
//        FFmpeg.exec(" -i "+videoFilePath+" -i "+audioFilePath+" -codec copy "+path+"/"+fileName+fileType);
    }

    /**
     * 合并音频和视频
     */
    public String merge(String videoFilePath, String audioFilePath, String outPath) throws Exception {
        //删除同名文件
        new File(outPath).delete();
        //获取绝对路径
        videoFilePath = new File(videoFilePath).getCanonicalPath();
        audioFilePath = new File(audioFilePath).getCanonicalPath();
        outPath = new File(outPath).getCanonicalPath();

        String exec = FFmpeg.exec(" -i " + videoFilePath + " -i " + audioFilePath + " -codec copy " + outPath);
        //删除源文件
        if (new File(outPath).isFile()) {
            new File(videoFilePath).delete();
            new File(audioFilePath).delete();
        }

        return exec;
//        FFmpeg.exec(" -i "+videoFilePath+" -i "+audioFilePath+" -c copy ")
    }

    private ArrayList<Future<DownloadResult>> durlDownloadThread(ExecutorService executorService, boolean isWaitTakeFinish, int threadCount, String filePath, String url, long fileSize, DownloadControl control) throws IOException, ExecutionException, InterruptedException {
        LoggerFactory.getLogger("Test").trace("文件长度: "+ fileSize);
        //创建任务结果队列
        ArrayList<Future<DownloadResult>> tasks = new ArrayList<>();
        //多线程下载文件
        //下载文件
        ArrayList<Future<DownloadResult>> futures = VideoDownloadUtil.downloadFileThread(executorService, url, filePath, fileSize, threadCount, header, json.getBv(), control);
        tasks.addAll(futures);
        return tasks;
    }


    /**
     * 选择符合用户期待的类型，如果不符合就返回类似类型
     *
     * @param tem
     * @return
     */
    private ArrayList<Media> select(DashTemplate tem) {
        ArrayList<Media> list = new ArrayList<>();
        HashSet<Media> audios = selectAudio(tem);
        HashSet<Media> videos = selectVideo(tem);

        Media maxV = null;
        Media maxAndMp4V = null;
        //选择清晰度最高的与mp4格式下清晰度最高的
        for (Media video : videos) {
            if (maxV == null) maxV = video;
            if (maxV.getId() < video.getId()) {
                maxV = video;
            }
            if ("mp4".equalsIgnoreCase(maxV.getMimeType().replaceAll("video/", ""))) {
                maxAndMp4V = maxV;
            }
        }

        String maxV_Tpye = maxV.getMimeType().replaceAll("video/", "");

        Media maxA = null;
        Media maxAndMp4A = null;

        //选择清晰度最高并与视频文件格式保存一直  与mp4格式下清晰度最高的
        for (Media audio : audios) {
            String maxA_Tpye = audio.getMimeType().replaceAll("audio/", "");
            boolean isType = maxA_Tpye.equalsIgnoreCase(maxV_Tpye);
            if (maxA == null && isType) maxA = audio;
            if (maxA.getId() < audio.getId() && isType) {
                maxA = audio;
            }
            if ("mp4".equalsIgnoreCase(maxA.getMimeType().replaceAll("audio/", ""))) {
                maxAndMp4A = maxA;
            }
        }
        //用户找MP4格式并且存在MP4格式，返回mp4格式
        if (maxAndMp4A != null && maxAndMp4V != null &&
                (tem.getFileType() == null || "mp4".equalsIgnoreCase(tem.getFileType()))) {
            list.add(maxAndMp4A);
            list.add(maxAndMp4V);
            return list;
        }
        //返回用户期待的
        list.add(maxA);
        list.add(maxV);
        return list;
    }

    private HashSet<Media> selectAudio(DashTemplate tem) {
        //符合清晰度预期的音频
        HashSet<Media> qnSet = new HashSet<>();
        //符合“视频格式”预期的音频，注意是视频格式
        HashSet<Media> formatSet = new HashSet<>();
        //符合音频编码格式预期的音频
        HashSet<Media> codeSet = new HashSet<>();
        HashSet<Media> all = new HashSet<>();

        //遍历所有dash，防止遇到分段情况
        //分别选出符合预期的视频
        for (Dash dash : data.getDash()) {
            for (Media audio : dash.audio) {
                //找到用户预期的分辨率
                if (audio.getId() == tem.getAudioQn()) {
                    qnSet.add(audio);
                }
                //找到用户预期的视频文件格式
                if (audio.getMimeType().replaceAll("audio/", "").equalsIgnoreCase(tem.getFileType())) {
                    formatSet.add(audio);
                }
                //找到用户预期的视频编码格式
                if (audio.getCodecs().equalsIgnoreCase(tem.getAudioCodecs())) {
                    codeSet.add(audio);
                }
                all.add(audio);
            }
        }

        //求交集
        //依照权重返回集合，清晰度，文件格式，编码。
        HashSet<Media> intersection = intersection(qnSet, formatSet);
        HashSet<Media> intersection2 = intersection(qnSet, codeSet);
        HashSet<Media> intersection3 = intersection(intersection, intersection2);
        HashSet<Media> intersection4 = intersection(qnSet, codeSet);
        if (intersection3.isEmpty()) {
            if (intersection.isEmpty()) {
                if (intersection4.isEmpty()) {
                    if (qnSet.isEmpty()) {
                        return all;
                    } else {
                        return qnSet;
                    }
                } else {
                    return intersection4;
                }
            } else {
                return intersection;
            }
        } else {
            return intersection3;
        }
    }

    private HashSet<Media> selectVideo(DashTemplate tem) {
        //预期的分辨率
        int quality = tem.getQn();
        if (quality == 0) quality = data.getQuality();
        //符合分辨率预期的视频
        HashSet<Media> qnSet = new HashSet<>();
        //符合视频格式预期的视频
        HashSet<Media> formatSet = new HashSet<>();
        //符合视频编码格式预期的视频
        HashSet<Media> codeSet = new HashSet<>();
        HashSet<Media> all = new HashSet<>();

        //遍历所有dash，防止遇到分段情况
        //分别选出符合预期的视频
        for (Dash dash : data.getDash()) {
            for (Media video : dash.video) {
                //找到用户预期的分辨率
                if (video.getId() == quality) {
                    qnSet.add(video);
                }
                //找到用户预期的视频文件格式
                if (video.getMimeType().replaceAll("video/", "").equalsIgnoreCase(tem.getFileType())) {
                    formatSet.add(video);
                }
                //找到用户预期的视频编码格式
                if (video.getCodecs().equalsIgnoreCase(tem.getVideoCodecs())) {
                    codeSet.add(video);
                }
                all.add(video);
            }
        }

        //求交集
        //依照权重返回集合，清晰度，文件格式，编码。
        HashSet<Media> intersection = intersection(qnSet, formatSet);
        HashSet<Media> intersection2 = intersection(qnSet, codeSet);
        HashSet<Media> intersection3 = intersection(intersection, intersection2);
        HashSet<Media> intersection4 = intersection(qnSet, codeSet);
        if (intersection3.isEmpty()) {
            if (intersection.isEmpty()) {
                if (intersection4.isEmpty()) {
                    if (qnSet.isEmpty()) {
                        return all;
                    } else {
                        return qnSet;
                    }
                } else {
                    return intersection4;
                }
            } else {
                return intersection;
            }
        } else {
            return intersection3;
        }
    }


    /**
     * 交集
     *
     * @param set1
     * @param set2
     * @return
     */
    private HashSet<Media> intersection(HashSet<Media> set1, HashSet<Media> set2) {
        HashSet<Media> result = new HashSet<>();
        result.addAll(set1);
        result.retainAll(set2);
        return result;
    }
}
