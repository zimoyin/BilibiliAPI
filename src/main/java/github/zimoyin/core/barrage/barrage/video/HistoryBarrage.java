package github.zimoyin.core.barrage.barrage.video;

import com.alibaba.fastjson.JSONArray;
import github.zimoyin.core.barrage.barrage.BarrageInterface;
import github.zimoyin.core.barrage.com.github.proto.BarrageProto;
import github.zimoyin.core.barrage.data.Barrage;
import github.zimoyin.core.cookie.Cookie;
import github.zimoyin.core.cookie.GlobalCookie;
import github.zimoyin.core.exception.Code;
import github.zimoyin.core.exception.CodeException;
import github.zimoyin.core.exception.CookieNotFoundException;
import github.zimoyin.core.utils.DateFormat;
import github.zimoyin.core.utils.JsonSerializeUtil;
import github.zimoyin.core.utils.net.httpclient.HttpClientResult;
import github.zimoyin.core.utils.net.httpclient.HttpClientUtils;
import org.apache.http.HttpException;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;


/**
 * 获取视频的历史弹幕文件
 * <p>
 * API的获取历史弹幕文件的时候需要至少休眠 1分钟
 * 获取历史弹幕是需要登录的因此需要传入cookie<br>
 * 获取视频的历史弹幕，因为弹幕接口的xml接口失效了，只能使用proto接口来获取弹幕文件（二进制）<br>
 * 这个类是会把弹幕文件反序列为对象的，因此不用去了解proto.<br>
 * </p>
 * <p>
 * 主要此类不会创建一个 List 对象返回结果，而是直接返回自身，自身就是一个List 集合
 * </P>
 * @API: https://github.com/SocialSisterYi/bilibili-API-collect
 */
public class HistoryBarrage extends ArrayList<Barrage> implements BarrageInterface {
    // GET 需要cookie   oid=cid  month =YYYY-MM
    private static final String URL_DATE = "http://api.bilibili.com/x/v2/dm/history/index?type=1&oid=%s&month=%s";
    //type =1 代表是视频弹幕
    private static final String URL_DATE_BARRAGE_PROTO = "http://api.bilibili.com/x/v2/dm/web/history/seg.so?type=1&oid=%s&date=%s";
    //Cookie
    Cookie cookie;
    //请求头
    HashMap<String, String> header;
    //请求头
//    HashMap<String,String> param;


    /**
     * 创建本类，Cookie需要set另外传入进来否则会报错
     */
    public HistoryBarrage() {
        header = new HashMap<>();
        header.put("referer", "https://www.bilibili.com");
        header.put("origin", "https://www.bilibili.com");
    }

    /**
     * 创建一个带有Cookie的对象
     *
     * @param cookie
     */
    public HistoryBarrage(Cookie cookie) {
        header = new HashMap<>();
        header.put("referer", "https://www.bilibili.com");
        header.put("origin", "https://www.bilibili.com");
        header.put("Cookie", cookie.toString());
    }

    /**
     * 快速构建历史弹幕列表
     *
     * @param cookie
     * @param bv
     * @param date
     * @throws Exception
     */
    /**
     * 未测试，异常处理机制不完善
     *
     * @param cookie
     * @param bv
     * @param date
     * @throws Exception
     */
    @Deprecated
    public HistoryBarrage(Cookie cookie, String bv, String date) throws Exception {
        this.cookie = cookie;
        header = new HashMap<>();
        header.put("referer", "https://www.bilibili.com");
        header.put("origin", "https://www.bilibili.com");
        header.put("Cookie", cookie.toString());
        //获取弹幕文件的二进制数组
        byte[] pageByByte = getPageByByte(bv, date);
        //解析弹幕文件
        setBarrages(pageByByte, this);
    }

    /**
     * 设置cookie
     *
     * @param cookie
     */
    public void setCookie(Cookie cookie) {
        this.cookie = cookie;
        header.clear();
        header.put("Cookie", cookie.toString());
    }


    /**
     * 获取该视频的所有的历史弹幕
     *
     * @param bv 视频的BV号
     * @param sleepTime 睡眠时间，只有睡眠了才会一定程度上的降低IP被封的概率
     * @param handle 对具体一天中的弹幕列表进行处理
     * @return
     * @throws Exception
     */
    public ArrayList<Barrage> getBarrageAll(String bv, long sleepTime, HistoryBarrageAllHandle handle) throws Exception {
        ArrayList<Barrage> barrages = null; //临时弹幕集合
        ArrayList<Barrage> barrageAll = null; //所有的弹幕集合
        try {
            ArrayList<String> historyDateAll = getHistoryDateAll(bv, sleepTime);
            barrageAll = new ArrayList<>();
            //遍历所有的日期弹幕
            for (String date : historyDateAll) {
                //睡眠一下，防止封IP
                Thread.sleep(sleepTime);
                //获取当天弹幕
                barrages = getBarrage(bv, date);
                //调用 HistoryBarrageAllHandle.handle() 方法用来处理弹幕，他是用户自己实现的类
                if (handle != null) handle.handle(date, barrages);
                // 将当天弹幕并入到所有弹幕里面
                barrageAll.addAll(barrages);
                barrages = null;
            }
        } catch (Exception e) {
            throw new HttpException(" 访问接口时出现异常无法获取视频(" + bv + ")下所有的历史弹幕文件，原因未知", e);
        }
        return barrageAll;
    }

     /**
     * 获取该视频的所有的历史弹幕
     *
     * @param bv 视频的BV号
     * @param sleepTime 睡眠时间，只有睡眠了才会一定程度上的降低IP被封的概率
     */
    public ArrayList<Barrage> getBarrageAll(String bv, long sleepTime) throws Exception {
        ArrayList<Barrage> barrages = null; //临时弹幕集合
        ArrayList<Barrage> barrageAll = null; //所有的弹幕集合
        try {
            //日期列表
            ArrayList<String> historyDateAll = getHistoryDateAll(bv, sleepTime);
            barrageAll = new ArrayList<>();
            //遍历所有的日期弹幕
            for (String date : historyDateAll) {
                //睡眠一下，防止封IP
                Thread.sleep(sleepTime);
                //获取当天弹幕
                barrages = getBarrage(bv, date);
                // 将当天弹幕并入到所有弹幕里面
                barrageAll.addAll(barrages);
                barrages = null;
            }
        } catch (Exception e) {
            throw new HttpException(" 访问接口时出现异常无法获取视频(" + bv + ")下所有的历史弹幕文件，原因未知", e);
        }
        return barrageAll;
    }


    /**
     * 获取该视频的所有的历史弹幕,每1s获取一天的弹幕列表
     * @param bv 视频的BV号
     * @return
     * @throws Exception
     */
    public ArrayList<Barrage> getBarrageAll(String bv) throws Exception {
        ArrayList<Barrage> barrages = null; //临时弹幕集合，只有获取当天的弹幕
        ArrayList<Barrage> barrageAll = null; //所有的弹幕集合，方法执行完返回
        try {
            //日期列表
            ArrayList<String> historyDateAll = getHistoryDateAll(bv, 1000);
            barrageAll = new ArrayList<>();
            //遍历所有的日期并获取当天弹幕
            for (String date : historyDateAll) {
                //睡眠一下，防止封IP
                Thread.sleep(1000);
                //获取当天弹幕
                barrages = getBarrage(bv, date);
                // 将当天弹幕并入到所有弹幕里面
                barrageAll.addAll(barrages);
                barrages = null;
            }
        } catch (Exception e) {
            throw new HttpException(" 访问接口时出现异常无法获取视频(" + bv + ")下所有的历史弹幕文件，原因未知", e);
        }
        return barrageAll;
    }


    /**
     * 返回弹幕列表到一个新的集合，而不是使用自身的集合
     *
     * @param bv
     * @return
     * @throws Exception
     */
    @Override
    public ArrayList<Barrage> getBarrage(String bv) throws Exception {
        return getBarrage(bv, DateFormat.format("YYYY-MM-dd"));
    }

    /**
     * 返回弹幕列表 到一个新的集合，而不是使用自身的集合
     *
     * @param bv
     * @return
     * @throws Exception
     */
    public ArrayList<Barrage> getBarrage(String bv, String date) throws Exception {
        ArrayList<Barrage> barrages = new ArrayList<>();
        //获取弹幕文件的二进制数组
        byte[] pageByByte = getPageByByte(bv, date);
        //解析弹幕文件
        setBarrages(pageByByte, barrages);
        return barrages;
    }

    /**
     * 解析弹幕文件
     *
     * @param pageByByte
     * @param list
     * @throws CodeException
     */
    private void setBarrages(byte[] pageByByte, List<Barrage> list) throws CodeException {
        try {
            //解析弹幕文件
            BarrageProto.DmSegMobileReply dmSegMobileReply = BarrageProto.DmSegMobileReply.parseFrom(pageByByte);
            for (BarrageProto.DanmakuElem elem : dmSegMobileReply.getElemsList()) {
                Barrage barrage = new Barrage();
                barrage.setText(elem.getContent());
                barrage.setSendTime(elem.getCtime());
                barrage.setMidHash(elem.getMidHash());
                barrage.setShowTime(elem.getProgress());
                barrage.setDmid(elem.getIdStr());
                barrage.setRbg(elem.getColor());
                barrage.setSize(elem.getFontsize());
                barrage.setBarragePoolType(elem.getPool());
                barrage.setShieldingLevel(elem.getWeight());
                barrage.setType(elem.getMode());
                barrage.setAction(elem.getAction());
                list.add(barrage);
            }
        } catch (Exception e) {
            Code.throwCodeException(new String(pageByByte), "解析弹幕文件失败，无法解析历史弹幕记录", e);
        }
    }

    /**
     * 获取今天的日期弹幕文件的String
     *
     * @param bv 视频的bv号，如果是av号可由toBV方法转换一下
     * @return String
     * @throws Exception
     */
    @Override
    public String getPage(String bv) throws Exception {
        String date = DateFormat.format("YYYY-MM-dd");
        return getPage(bv, date);
    }


    /**
     * 获取日期弹幕文件的String
     *
     * @param bv   视频的bv号，如果是av号可由toBV方法转换一下
     * @param data 视频的历史弹幕日期，格式：YYYY-MM-DD
     * @return String
     * @throws Exception
     */
    public String getPage(String bv, String data) throws Exception {
        HttpClientResult httpClientResult = doGet(getCID(bv), data);
        return httpClientResult.getContent();
    }

    /**
     * 获取日期弹幕文件的 byte数组
     *
     * @param bv   视频的bv号，如果是av号可由toBV方法转换一下
     * @param data 视频的历史弹幕日期，格式：YYYY-MM-DD
     * @return byte[]
     * @throws Exception
     */
    public byte[] getPageByByte(String bv, String data) throws Exception {
        HttpClientResult httpClientResult = doGet(getCID(bv), data);
        return httpClientResult.getBytes();
    }


    /**
     * 获取日期弹幕文件的网络流，不建议使用这个方法，因为可能会导致无法释放资源
     *
     * @param bv   视频的bv号，如果是av号可由toBV方法转换一下
     * @param data 视频的历史弹幕日期，格式：YYYY-MM-DD
     * @return
     * @throws Exception
     */
    @Deprecated
    public InputStream getPageInputSteam(String bv, String data) throws Exception {
        HttpClientResult httpClientResult = doGet(getCID(bv), data);
        return httpClientResult.getInputStream();
    }


    /**
     * 获取视频下某年某月的视频弹幕日期列表
     *
     * @param bv   视频的bv号
     * @param date 要获取的年月 格式 ： YYYY-MM
     * @return
     */
    public ArrayList<String> getHistoryDates(String bv, String date) throws Exception {
        ArrayList<String> dates = new ArrayList<>();
        String cid = getCID(bv);
        HttpClientResult httpClientResult = HttpClientUtils.doGet(String.format(URL_DATE, cid, date), header, null);
        String content = httpClientResult.getContent();
        //code 为 0 说明请求成功，如果不是就抛出异常
        Code.throwCodeException(content, "无法获取(" + bv + ": " + date + ")下的弹幕日期列表");
        String read = JsonSerializeUtil.getJsonPath().read(content, "/data");
        if ("null".equalsIgnoreCase(read)) return null;
        JSONArray array = JSONArray.parseArray(read);
        for (Object o : array) {
            dates.add(o.toString());
        }
        return dates;
    }


    /**
     * 获取该视频所有的弹幕日期列表
     *
     * @param bv        视频的bv号
     * @param sleepTime 线程睡眠时间，为了防止封号封IP请保持每秒钟的请求数量小于20条,单位毫秒.至少休眠 [30,120]秒
     * @return 弹幕日期列表
     * @throws Exception
     */
    public ArrayList<String> getHistoryDateAll(String bv, long sleepTime) throws Exception {
        //存储这个视频所有日期的集合
        ArrayList<String> dates = new ArrayList<>();
        //获取视频的 cid
        String cid = getCID(bv);
        //创建Calendar 的实例
        Calendar calendar = Calendar.getInstance();

        while (true) {
            //线程睡眠时间，为了防止封号封IP请保持每秒钟的请求数量小于20条,单位毫秒
            Thread.sleep(sleepTime);
            //获取此次循环的日期
            String date = DateFormat.format(calendar.getTime(), "YYYY-MM");
            //访问接口
            HttpClientResult httpClientResult = HttpClientUtils.doGet(String.format(URL_DATE, cid, date), header, null);
            String content = httpClientResult.getContent();
            //code 为 0 说明请求成功，如果不是就抛出异常
            Code.throwCodeException(content, "无法获取(" + bv + ": " + date + ")下的所有的弹幕日期列表");
            //处理一下json，给null转移
            content = content.replaceAll(":null", ":\"null\"");
            //当前时间减去一个月，即一个月前的时间
            calendar.add(Calendar.MONTH, -1);
            //获取data
            String data = JsonSerializeUtil.getJsonPath().read(content, "/data");
            //如果data为null说明当前日期超过了视频的发送日期
            if ("null".equalsIgnoreCase(data)) break;
            //遍历日期列表，取出日期放入总日期中
            JSONArray array = JSONArray.parseArray(data);
            for (Object o : array) {
                dates.add(o.toString());
            }
        }
        return dates;
    }


    /**
     * 获取该视频所有的弹幕日期列表
     *
     * @param bv 视频的bv号.每0.8s确认一天的日期弹幕是否存在
     * @return
     * @throws Exception
     */
    public ArrayList<String> getHistoryDateAll(String bv) throws Exception {
        //存储这个视频所有日期的集合
        ArrayList<String> dates = new ArrayList<>();
        //获取视频的 cid
        String cid = getCID(bv);
        //创建Calendar 的实例
        Calendar calendar = Calendar.getInstance();

        while (true) {
            //线程睡眠时间，为了防止封号封IP请保持每秒钟的请求数量小于20条,单位毫秒
            Thread.sleep(800);
            //获取此次循环的日期
            String date = DateFormat.format(calendar.getTime(), "YYYY-MM");
            //访问接口
            HttpClientResult httpClientResult = HttpClientUtils.doGet(String.format(URL_DATE, cid, date), header, null);
            String content = httpClientResult.getContent();
            //code 为 0 说明请求成功，如果不是就抛出异常
            Code.throwCodeException(content, "无法获取(" + bv + ": " + date + ")下的所有的弹幕日期列表");
            //处理一下json，给null转移
            content = content.replaceAll(":null", ":\"null\"");
            //当前时间减去一个月，即一个月前的时间
            calendar.add(Calendar.MONTH, -1);
            //获取data
            String data = JsonSerializeUtil.getJsonPath().read(content, "/data");
            //如果data为null说明当前日期超过了视频的发送日期
            if ("null".equalsIgnoreCase(data)) break;
            //遍历日期列表，取出日期放入总日期中
            JSONArray array = JSONArray.parseArray(data);
            for (Object o : array) {
                dates.add(o.toString());
            }
        }
        return dates;
    }

    /**
     * 获取日期弹幕文件的响应结果
     *
     * @param cid  视频的CID，可有由getCID 方法获取
     * @param data 视频的历史弹幕日期，格式：YYYY-MM-DD
     * @return
     * @throws Exception
     */
    public HttpClientResult doGet(String cid, String data) throws Exception {
        HttpClientResult httpClientResult = HttpClientUtils.doGet(String.format(URL_DATE_BARRAGE_PROTO, cid, data), header, null);
        Code.throwCodeException(httpClientResult.getCode(), "无法访问历史弹幕接口");
        return httpClientResult;
    }


    /**
     * 清理所有的弹幕信息
     */
    public void clear() {
        this.clear();
    }
}
