package github.zimoyin.core.video.download;

import github.zimoyin.core.exception.DownloadException;
import github.zimoyin.core.video.download.videoDownload.WebDashVideoDownload;
import github.zimoyin.core.video.download.videoDownload.WebDurlVideoDownload;
import github.zimoyin.core.video.url.VideoURLFormat;
import github.zimoyin.core.video.url.VideoURLPreviewFormatP1080;
import github.zimoyin.core.video.url.pojo.Dash;
import github.zimoyin.core.video.url.pojo.Durl;
import github.zimoyin.core.video.url.pojo.VideoURLJsonRoot;
import lombok.Data;

import java.io.File;
import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.Future;

/**
 * 注释请见父类
 * 进行多任务下载时注意使用多线程要节制
 * 下载地址的初始化，他会在调用download等方法时进行初始化也可以调用init方法手动初始化
 * 如何获取下载地址：不需要向类里面传入下载地址，他会自己生成。如果要获取请get以下对象（根据需要）:urlPojo、durlDownloadObj、dashDownloadObj
 * bug：无法并发（指多次调用download等方法）下载多任务（控制器失效，下载文件字节块丢失）,控制器在使用非多线程下载时会报错
 */
@Deprecated
@Data
public class VideoDownload extends VideoDownloadAbs {

    /**
     * 当前视频下载设置
     */
    private VideoDownloadSetting setting;

    /**
     * 当前带有url信息的对象
     */
    private VideoURLJsonRoot urlPojo;

    /**
     * 当前下载控制器,请不要直接赋值
     */
    private DownloadControl control;


    /**
     * 当前durl 格式视频下载
     */
    private WebDurlVideoDownload durlDownloadObj;
    /**
     * 当前dash视频格式下载器，如果对视频有格式要求的话，请给dashTemplate 属性赋值
     */
    private WebDashVideoDownload dashDownloadObj;

    /**
     * 当下载器进行多任务下载时，将所有的下载器，控制器保存下来
     */
    private ArrayList<WebDashVideoDownload> dashDownloadObjArray = new ArrayList<WebDashVideoDownload>();
    /**
     * 当下载器进行多任务下载时，将所有的下载器，控制器保存下来
     */
    private ArrayList<WebDurlVideoDownload> durlDownloadObjArray = new ArrayList<WebDurlVideoDownload>();
    /**
     * 当下载器进行多任务下载时，将所有的下载器，控制器保存下来
     */
    private volatile ArrayList<DownloadControl> controlArray = new ArrayList<DownloadControl>();

    /**
     * 监听集合
     */
    private volatile ArrayList<DownloadHandle> handles = new ArrayList<>();

    /**
     * 监听集合
     */
    private volatile DownloadHandle thisHandle;

    public VideoDownload() {
    }

    public VideoDownload(VideoDownloadSetting setting) {
        this.setting = setting;
        this.setting.build();
    }

    /**
     * 将就着一下不想使用VideoDownloadSetting，对画质和格式没要求
     *
     * @param bv
     */
    public VideoDownload(String bv) {
        this.setting = new VideoDownloadSetting(bv).build();
    }

    @Override
    public boolean download() throws DownloadException {
        setting.setThreadCount(1);//这是个单线程下载
        init();
        //是否允许下载
        if (!isOverride()) return false;
        if (isNotDownload()) throw new DownloadException("URL信息状态码不为 0 ，禁止下载该视频");
        boolean b = false;
        try {
            if (isDurl()) b = durlDownloadObj.durlDownload(setting.getFileName(), control);
            if (isDash()) b = dashDownloadObj.dashDownload(setting.getDashTemplate(), setting.getFileName(), control);
            return b;
        } catch (Exception e) {
            stop();
            control.close();
            throw new DownloadException("下载失败", e);
        }
    }

    @Override
    public void downloadAsynchronous() throws DownloadException {
        setting.setThreadCount(1);//这是个单线程下载
        init();
        //是否允许下载
        if (!isOverride()) return;
        if (isNotDownload()) throw new DownloadException("URL信息状态码不为 0 ，禁止下载该视频");
        new Thread(new Runnable() {
            public void run() {
                try {
                    Thread.currentThread().setName("downloadFile-" + setting.getFileName());
                    if (isDurl()) durlDownloadObj.durlDownload(setting.getFileName(), control);
                    if (isDash())
                        dashDownloadObj.dashDownload(setting.getDashTemplate(), setting.getFileName(), control);
                } catch (Exception e) {
                    stop();
                    control.close();
                    throw new DownloadException("下载失败", e);
                }
            }
        }).start();
    }

    @Override
    public ArrayList<Future<DownloadResult>> downloadThread(boolean isWaitTakeFinish) throws DownloadException {
        init();
        //是否允许下载
        if (!isOverride()) return new ArrayList<Future<DownloadResult>>();
        if (isNotDownload()) throw new DownloadException("URL信息状态码不为 0 ，禁止下载该视频");
        try {
            if (isDurl())
                return durlDownloadObj.durlDownloadThread(isWaitTakeFinish, setting.getThreadCount(), setting.getFileName(), control);
            if (isDash())
                return dashDownloadObj.dashDownloadThread(isWaitTakeFinish, setting.getThreadCount(), setting.getDashTemplate(), setting.getFileName(), control);
        } catch (Exception e) {
            stop();
            control.close();
            throw new DownloadException("下载失败", e);
        }
        return null;
    }

    /**
     * 当有同名称的文件的时候是否允许下载并覆盖他
     * @return
     */
    private boolean isOverride(){
        if (!setting.isOverride()){
               for (File file : Objects.requireNonNull(new File(setting.getPath()).listFiles())) {
                String name = file.getName();
                String substring = name.substring(0, name.lastIndexOf("."));
                if (setting.getFileName().equals(substring)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 合并所有视频，注意只能合并在这个下载器内下载的视频，其他的无法合并
     * 注意：无法合并已经被合并过的视频
     *
     * @return
     * @throws DownloadException
     */
    public void mergeAll() throws DownloadException {
        for (WebDashVideoDownload dash : dashDownloadObjArray) {
            try {
                dash.merge();
            } catch (Exception e) {
                throw new DownloadException("合并视频文件失败", e);
            }
        }
        dashDownloadObjArray.clear();
    }

    @Override
    public String merge() throws DownloadException {
        try {
            dashDownloadObjArray.remove(dashDownloadObj);
            return dashDownloadObj.merge();
        } catch (Exception e) {
            throw new DownloadException("合并视频文件失败", e);
        }
    }

    @Override
    public String merge(String videoFilePath, String audioFilePath, String outPath) throws DownloadException {
        try {
            dashDownloadObjArray.remove(dashDownloadObj);
            return dashDownloadObj.merge(videoFilePath, audioFilePath, outPath);
        } catch (Exception e) {
            throw new DownloadException("合并视频文件失败", e);
        }
    }

    @Override
    public void stop() throws DownloadException {
        control.setStop(true);
    }

    @Deprecated
    @Override
    public boolean downloadInit() {
        init();
        return true;
    }

    /**
     * 下载初始化，包括：
     * URL pojo重新获取
     * 下载器对象重新构建
     * 控制器重新构建
     */
    private void init() {
        //构建设置
        if (this.setting != null) {
            this.setting.update();
        }
        initUrlPojo();
        initDownloadObj();
        initControl();
        //将下载器、控制器保存下来
        dashDownloadObjArray.add(dashDownloadObj);
        durlDownloadObjArray.add(durlDownloadObj);
        controlArray.add(control);
    }

    /**
     * 初始化一个控制器
     * 每次下载新任务的时候都去新初始化一个控制器
     * 新的控制器将拥有全局处理器，与当前处理器
     */
    private void initControl() {
        //获取一个新的控制器
        this.control = new DownloadControl(setting.getThreadCount());
        this.control.setFilePath(this.setting.getPath());
        this.control.setFileName(this.setting.getFileName());
        //将全局处理器交给新的控制器
        this.control.getHandles().addAll(this.handles);
        //将当前处理器交给新的控制器
        if (this.thisHandle != null) this.control.setListenDownloadSize(this.thisHandle);
    }

    /**
     * 参数下载器
     */
    private void initDownloadObj() {
        durlDownloadObj = new WebDurlVideoDownload(urlPojo, setting.getPath());
        durlDownloadObj.setCookie(setting.getCookie());
        dashDownloadObj = new WebDashVideoDownload(urlPojo, setting.getPath());
        dashDownloadObj.setCookie(setting.getCookie());
    }


    private void initUrlPojo() throws DownloadException {
        //视频的bv号
        String bv = setting.getBv();
        //视频的cid，cid是用于对bv视频确定具体分p的
        String cid = String.valueOf(setting.getCid());
        //清晰度
        int qn = setting.getQn();
        //视频格式
        int fnval = setting.getFnval();
        try {
            if (setting.isPreview1080p()) {
                //视频URL获取对象
                VideoURLPreviewFormatP1080 urlPreviewFormatP1080 = new VideoURLPreviewFormatP1080();
                //获取url的pojo
                this.urlPojo = urlPreviewFormatP1080.getJsonPojo(bv, cid);
            } else {
                //视频URL获取对象
                VideoURLFormat videoURLFormat = new VideoURLFormat(setting.getCookie());
                //获取url的pojo
                this.urlPojo = videoURLFormat.getJsonPOJO(bv, cid, qn, fnval);
            }
        } catch (Exception e) {
            throw new DownloadException("获取视频URL失败", e);
        }
    }

    /**
     * 获取URL的pojo类，注意，该方法会执行init方法，导致类在下载之前进行初始化
     *
     * @return
     */
    public VideoURLJsonRoot getUrlPojo() {
        init();
        return urlPojo;
    }

    /**
     * 获取URL的pojo类，注意，该方法会执行init方法，导致类在下载之前进行初始化
     * @param isInit 是否在获取URL的时候进行初始化
     * @return
     */
    public VideoURLJsonRoot getUrlPojo(boolean isInit) {
        if (isInit)init();
        return urlPojo;
    }

    /**
     * 为当前的一个控制器设置一个监听器（只能设置一个）
     * 如果想为所有的控制器添加监听器请调用 setAllListens 方法
     */
    public void setListens(DownloadHandle handle) {
        if (handle == null) return;
//        this.getControl().setListenDownloadSize(handle);
        this.thisHandle = handle;
    }


    /**
     * 为所有控制器设置一个监听器
     */
    public void setAllListens(DownloadHandle handle) {
        if (handle == null) return;
        handles.add(handle);
        for (DownloadControl control0 : this.controlArray) {
            control0.setListenDownloadSize(handle);
        }
    }


    @Override
    public boolean isDurl() {
        return urlPojo.getData().getDurl() != null;
    }

    @Override
    public boolean isDash() {
        return !isDurl();
    }

    public void setSetting(VideoDownloadSetting setting) {
        this.setting = setting;
        this.setting.build();
    }


    /**
     * 是否运行下载
     */
    public boolean isNotDownload() {
        System.out.println(urlPojo);
        return urlPojo.getCode() != 0;
    }


}
