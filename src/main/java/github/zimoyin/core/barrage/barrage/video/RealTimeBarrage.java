package github.zimoyin.core.barrage.barrage.video;


import com.google.protobuf.InvalidProtocolBufferException;

import github.zimoyin.core.barrage.barrage.BarrageInterface;
import github.zimoyin.core.barrage.com.github.proto.BarrageProto;
import github.zimoyin.core.barrage.data.Barrage;
import github.zimoyin.core.cookie.Cookie;
import github.zimoyin.core.cookie.GlobalCookie;
import github.zimoyin.core.exception.CookieNotFoundException;
import github.zimoyin.core.utils.net.httpclient.HttpClientResult;
import github.zimoyin.core.utils.net.httpclient.HttpClientUtils;
import sun.misc.GC;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * 实时弹幕
 * 未测试。异常处理机制未完善 完成后可以去掉过时标准
 * 异常处理机制标准，哪个接口有问题就报哪个，是主要区分网络原因，服务器原因，还是本地原因
 * @API: https://github.com/SocialSisterYi/bilibili-API-collect
 */
public class RealTimeBarrage extends ArrayList<Barrage> implements BarrageInterface {
    //仅获取6min的整数倍时间内的弹幕，6min内最多弹幕数为6000条（如第一包中弹幕progress值域为0-360000）
    //oid = cid , segment_index =分包 6分钟一包
    private static final String URL_WEB = "http://api.bilibili.com/x/v2/dm/web/seg.so?type=1&oid=%s&segment_index=%s";
    //Cookie
    Cookie cookie;
    //请求头
    HashMap<String, String> header;
    //分包
    private int page = 1;

    @Deprecated
    public RealTimeBarrage() {
    }

    public RealTimeBarrage(Cookie cookie) {
        this.cookie = cookie;
        setHeader();
    }

    @Deprecated
    public RealTimeBarrage(Cookie cookie, int page) {
        this.page = page;
        this.cookie = cookie;
        setHeader();
    }

    @Deprecated
    public RealTimeBarrage(Cookie cookie, String bv) throws Exception {
        this.cookie = cookie;
        setHeader();
        byte[] pageByByte = getPageByByte(getCID(bv), 1);
        parseBarrage(pageByByte, this);
    }

    @Deprecated
    public RealTimeBarrage(Cookie cookie, String bv, int page) throws Exception {
        this.page = page;
        this.cookie = cookie;
        setHeader();
        byte[] pageByByte = getPageByByte(getCID(bv), page);
        parseBarrage(pageByByte, this);
    }

    /**
     * 设置请求头
     */
    private void setHeader() {
        header = new HashMap<>();
        header.put("referer", "https://www.bilibili.com");
        header.put("origin", "https://www.bilibili.com");
        header.put("Cookie", cookie.toString());
    }

    /**
     * 设置cookie
     *
     * @param cookie
     */
    public void setCookie(Cookie cookie) {
        this.cookie = cookie;
        setHeader();
    }


    /**
     * 获取这个BV视频所有的实时弹幕
     *
     * @param bv
     * @return
     * @throws Exception
     */
    public HashSet<Barrage> getBarrageAll(String bv) throws Exception {
        HashSet<Barrage> barrages = new HashSet<>();
        int page = 1;
        while (true) {
            ArrayList<Barrage> barrage = getBarrage(bv, page);
            if (barrage == null) {
                break;
            }
            barrages.addAll(barrage);
            page++;
        }
        return barrages;
    }


    /**
     * 获取实时弹幕，获取最近6分钟的弹幕
     *
     * @param bv 视频的bv号
     * @return
     * @throws Exception
     */
    @Override
    public ArrayList<Barrage> getBarrage(String bv) throws Exception {
        ArrayList<Barrage> list = new ArrayList<>();
//        byte[] pageByByte = getPageByByte(getCID(bv), 1);
        byte[] pageByByte = getPageByByte(bv, 1);
        return parseBarrage(pageByByte, list);
    }

    /**
     * 获取实时弹幕
     *
     * @param bv   视频的bv号
     * @param page 弹幕分包，六分钟一包，弹幕上限为3000条
     * @return
     * @throws Exception
     */
    public ArrayList<Barrage> getBarrage(String bv, int page) throws Exception {
        ArrayList<Barrage> list = new ArrayList<>();
//        byte[] pageByByte = getPageByByte(getCID(bv), page);
        byte[] pageByByte = null;
        try {
            pageByByte = getPageByByte(bv, page);
        } catch (IllegalArgumentException e) {
            if (e.getMessage().equals("Entity may not be null")) {
                //包数达到了最大，无法获取这个包数或者比这个更大的包数
                //这种情况下不抛出异常
            } else {
                throw e;
            }
        }
        return parseBarrage(pageByByte, list);
    }


    /**
     * 返回未解析的字符串
     *
     * @param bv 视频的bv号
     * @return
     * @throws Exception
     */
    @Override
    public String getPage(String bv) throws Exception {
        return doGet(getCID(bv), 1).getContent();
    }


    /**
     * 返回未解析的字符串
     *
     * @param bv   视频的bv号
     * @param page 弹幕分包，六分钟一包，弹幕上限为3000条
     * @return
     * @throws Exception
     */
    public String getPage(String bv, int page) throws Exception {
        return doGet(getCID(bv), page).getContent();
    }


    /**
     * 解析弹幕文件
     *
     * @param pageByByte 文件二进制数组 byte[]
     * @param list       空的list对象
     * @throws InvalidProtocolBufferException
     */
    private ArrayList<Barrage> parseBarrage(byte[] pageByByte, ArrayList<Barrage> list) throws InvalidProtocolBufferException {
        if (pageByByte == null) return null;
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
        return list;
    }

    /**
     * 获取弹幕文件的响应结果
     *
     * @param cid  视频的CID，可有由getCID 方法获取
     * @param page 弹幕的分包，6分钟一包
     * @return
     * @throws Exception
     */
    public HttpClientResult doGet(String cid, int page) throws Exception {
        HttpClientResult httpClientResult = HttpClientUtils.doGet(String.format(URL_WEB, cid, page), header, null);
        return httpClientResult;
    }

    /**
     * 获取日期弹幕文件的 byte数组
     *
     * @param bv   视频的bv号，如果是av号可由toBV方法转换一下
     * @param page 弹幕的分包，6分钟一包
     * @return byte[]
     * @throws Exception
     */
    public byte[] getPageByByte(String bv, int page) throws Exception {
        HttpClientResult httpClientResult = doGet(getCID(bv), page);
        return httpClientResult.getBytes();
    }
}
