package github.zimoyin.core.video.download;

import github.zimoyin.core.exception.DownloadException;
import github.zimoyin.core.video.download.videoDownload.WebDashVideoDownload;
import github.zimoyin.core.video.download.videoDownload.WebDurlVideoDownload;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.Future;


/**
 * 视频下载器基类
 * 视频下载器，可以下载各种格式视频，并且可以精确的下载某p视频，与某个互动视频的具体模块
 * 对于互动视频，请手动指定一个模块的cid这样才可以下载，至于自动下载所有p视频、下载指定范围视频p与下载所有模块视频，请找上层实现
 *
 * 【视频下载流程·用于自我实现】
 *  下载一个视频需要视频的BV号用来定位一个视频，cid用来定位视频中的具体某一p，然后一个bv和cid获取到url，但是b站不会直接返回url而是包含URl的json，因此需要解析
 *  1. 获取视频信息  通过 VideoInfo.class 类 拿到 -> WEBVideoINFOJsonRootBean.class
 *      > 从中可以获取到视频的标题，分p等信息（包括分p的cid）
 *  2. 视频下载的URL VideoURLFormat -> VideoURLJsonRoot.class
 *     > 里面存在视频的url，有两个大类别： dash与durl
 *     > dash： 可以下载两个m4s文件，一个是视频的一个是音频的，下载后请合并他们
 *     > durl: 可以直接下载mp4与flv格式视频，注意mp4格式通常固定为360P
 *  3. VideoURLFormat 类方法所需的参数
 *     > ID : 里面传入bv，如果想下载这个bv视频其他p或模块视频可以传入 该分p或模块cid
 *     > QN ： 视频清晰度（见类）
 *     > Fnval ： 视频格式（见类）
 *  4. 视频下载时需要cookie 与 防盗链 "referer" 请将它设置为 "https://www.bilibili.com"
 *  5. 对于互动视频，请构建 ID时，传入bv与cid。对于互动视频cid请见 InteractVideoInfo.class
 */
@Data
@Deprecated
public abstract class VideoDownloadAbs {


    private WebDurlVideoDownload durlDownloadObj;
    private WebDashVideoDownload dashDownloadObj;

    private HashMap<Integer, String> pageMap = new HashMap<Integer, String>();


    /**
     * 下载视频
     *
     * @throws Exception
     */
    public abstract boolean download() throws DownloadException;

    /**
     * 异步单线程下载视频
     *
     * @throws Exception
     */
    public abstract void downloadAsynchronous() throws DownloadException;

    /**
     * 多线程下载视频: 注意多个线程对同一个文件进行读写操作，下载速度有所提升，但是可能导致灾难发生
     *
     * @param isWaitTakeFinish 是否阻塞主线程等待下载完毕
     * @throws Exception
     */
    public abstract ArrayList<Future<DownloadResult>> downloadThread(boolean isWaitTakeFinish) throws DownloadException;

    /**
     * 合并音频和视频: 指针对 dash 的 m4s
     */
    public abstract String merge() throws DownloadException;

    /**
     * 合并音频和视频: 指针对 dash 的 m4s
     */
    public abstract String merge(String videoFilePath, String audioFilePath, String outPath) throws DownloadException;

    /**
     * 停止下载
     */
    public abstract void stop() throws DownloadException;

    /**
     * 下载前的初始化，检查路径，文件名称，是否可以下载
     */
    public abstract boolean downloadInit();


    /**
     * 判断是否是durl 类型的视频
     */
    public abstract boolean isDurl();

    /**
     * 判断是否是dash 类型的视频
     */
    public abstract boolean isDash();
}
