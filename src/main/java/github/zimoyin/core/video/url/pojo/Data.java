package github.zimoyin.core.video.url.pojo;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * data对象，里面的字段只保留了部分
 */
@lombok.Data
public class Data {
    /**
     * 字段	类型	内容	备注
     * durl	array	视频分段	注：仅flv/mp4存在此项
     * dash	obj	dash音视频流信息	注：仅dash存在此项
     */

    /**
     * 用户请求的分辨率（服务器在Durl下会返回用户申请的分辨率，但是在Dash下会返回所有的分辨率）  值含义见上表
     */
    public int quality;

    /**
     * 视频格式：对于Durl是有效的，Dash只能用于参考，因为Dash会返回所有分辨率与格式的视频
     */
    public String format;

    /**
     * 视频长度，单位毫秒
     * 不同分辨率/格式可能有略微差异
     */
    public long timelength;

    /**
     * 视频支持的全部格式
     */
    public String accept_format;

    /**
     * 视频支持的分辨率列表
     */
    public String[] accept_description;

    /**
     * 支持格式的详细信息
     */
    public String[] support_formats;


    /**
     * 视频分段
     * 注：仅flv/mp4存在此项
     */
    public ArrayList<Durl> durl;


    /**
     * dash音视频流信息	注：仅dash存在此项
     */
    public ArrayList<Dash> dash;




    /**
     * flv与mp4格式的在这里
     * 仅flv方式具有分段
     */


    @Override
    public String toString() {
        return "Data{" +
                "\n\t\tquality(视频分辨率代码)=" + quality +
                ", \n\t\tformat(视频格式)='" + format + '\'' +
                ", \n\t\ttimelength=(视频长度)" + ((double) timelength / 1000 / 60) + " min" +
                ", \n\t\taccept_format(视频支持的全部格式)='" + accept_format + '\'' +
                ", \n\t\taccept_description(视频支持的分辨率列表)=" + Arrays.toString(accept_description) +
                ", \n\t\tsupport_formats(支持格式的详细信息)=" + Arrays.toString(support_formats) +
                ", \n\t\tdurl=" + durl +
                ", \n\t\tdash=" + dash +
                "\n\t" +
                '}';
    }
}