package github.zimoyin.core.video.url.param;

import github.zimoyin.core.cookie.Cookie;
import github.zimoyin.core.utils.IDConvert;
import github.zimoyin.core.video.download.setting.info.DownloadVideoID;
import lombok.Getter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

@Getter
public final class ParamBuilder {
    private Fnver fnver = Fnver.Default;
    private Fourk fourk = Fourk.Default;
    private ArrayList<Integer> fnvals = new ArrayList<>();
    private QN qn = QN.P1080_cookie;
    private String bvid;
    private long cid;
    private Cookie cookie;
    private DownloadVideoID ID;

    public ParamBuilder() {
    }

    public ParamBuilder(String bvid) {
        if (bvid.toUpperCase().indexOf("BV") != 0)
            throw new IllegalArgumentException("ParamBuilder 只能接收 BVID 为ID");
        this.bvid = bvid;
        try {
            this.cid = Long.parseLong(IDConvert.BvToCID(bvid));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public ParamBuilder(String bvid, long cid) {
        if (cid <= 0) throw new IllegalArgumentException("cid 不能小于等于 0");
        if (bvid.toUpperCase().indexOf("BV") != 0)
            throw new IllegalArgumentException("ParamBuilder 只能接收 BVID 为ID");
        this.bvid = bvid;
        this.cid = cid;
    }

    public ParamBuilder(QN qn, String bvid, long cid) {
        if (cid <= 0) throw new IllegalArgumentException("cid 不能小于等于 0");
        if (bvid.toUpperCase().indexOf("BV") != 0)
            throw new IllegalArgumentException("ParamBuilder 只能接收 BVID 为ID");
        this.qn = qn;
        this.bvid = bvid;
        this.cid = cid;
    }

    public ParamBuilder append(QN qn) {
        this.qn = qn;
        //4K 超清
        if (qn == QN.P4k_cookie_vip) fnvals.add(128);
        //8k
        if (qn == QN.P8k_cookie_vip) fnvals.add(1024);
            //HDR 真彩色
        else if (qn == QN.HDR_cookie_vip) fnvals.add(64);
            //杜比视界
        else if (qn == QN.DV_cookie_vip_all) fnvals.add(512);
        return this;
    }

    public ParamBuilder append(Fnval fnval) {
        if (qn.getQn() >= 120 && fnval.getValue() < 16)
            throw new IllegalArgumentException("视频参数不合法。请指定为 dash 视频模式");
        fnvals.add(fnval.getValue());
        return this;
    }

    public ParamBuilder append(Fnver fnver) {
        this.fnver = fnver;
        return this;
    }

    public ParamBuilder append(ArrayList<Integer> fnvals) {
        this.fnvals = fnvals;
        return this;
    }

    public ParamBuilder append(Cookie cookie) {
        this.cookie = cookie;
        return this;
    }

    public ParamBuilder append(Fourk fourk) {
        this.fourk = fourk;
        return this;
    }

    public ParamBuilder append(Long cid) {
        if (cid <= 0) throw new IllegalArgumentException("cid 不能小于等于 0");
        this.cid = cid;
        return this;
    }

    public ParamBuilder append(String bvid) {
        if (bvid.toUpperCase().indexOf("BV") != 0)
            throw new IllegalArgumentException("ParamBuilder 只能接收 BVID 为ID");
        this.bvid = bvid;
        return this;
    }

    public ParamBuilder append(DownloadVideoID ID) {
        this.ID = ID;
        if (this.ID.getID() != null) append(this.ID.getBV());
        return this;
    }

    public int getFnval() {
        int fnval0 = 0;
        for (Integer fnval : fnvals) {
            fnval0 += fnval;
        }
        //如果未能设置 Fnval 则默认设置 FLV
        if (fnval0 == 0) return Fnval.FLV.getValue();
        return fnval0;
    }

    public String toString() {
        return "?" +
                "fnver" + "=" + fnver.getValue() + "&" +
                "fourk" + "=" + fourk.getValue() + "&" +
                "qn" + "=" + qn.getQn() + "&" +
                "fnval" + "=" + getFnval() + "&" +
                "bvid" + "=" + bvid + "&" +
                "cid" + "=" + cid;
    }


    public HashMap<String, String> build() {
        if (bvid == null || cid == 0)
            throw new NullPointerException("cid and bvid must not be null");
        HashMap<String, String> param = new HashMap<>();
        param.put("fnver", String.valueOf(fnver.getValue()));
        param.put("fourk", String.valueOf(fourk.getValue()));
        param.put("qn", String.valueOf(qn.getQn()));
        param.put("fnval", String.valueOf(getFnval()));
        param.put("cid", String.valueOf(cid));
        param.put("bvid", bvid);

        return param;
    }

}
