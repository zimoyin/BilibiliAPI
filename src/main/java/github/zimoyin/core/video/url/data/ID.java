package github.zimoyin.core.video.url.data;

import github.zimoyin.core.exception.CodeException;
import github.zimoyin.core.exception.URLFormatException;
import github.zimoyin.core.utils.IDConvert;
import lombok.ToString;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

/**
 * 视频ID
 */
public class ID {
    public ID() {
    }

    public ID(String bv) {
        this.bv = bv;
    }

    public String bv;
    public String av;
    public String cid;

    public void setBv(String bv) {
        this.bv = bv;
    }

    public void setAV(String av) {
        this.av = av;
    }
    public void setCid(String cid) {this.cid = cid;}

    public String getBv() {
        if (bv == null) this.bv = IDConvert.AvToBv(av);
        return bv;
    }

    public String getAv() {
        if (av == null) this.av = IDConvert.BvToAv(bv);
        return av;
    }

    public String getCid() throws CodeException, IOException, NoSuchAlgorithmException, KeyStoreException, URISyntaxException, KeyManagementException {
        if (cid != null) return cid;
        if (bv != null) cid = IDConvert.BvToCID(this.bv);
        if (av != null) cid = IDConvert.AvToCID(this.av);
        return cid;
    }

    /**
     * 获取URL 中的参数
     *
     * @return
     */
    public HashMap<String, String> getVal(HashMap<String, String> val) throws CodeException, IOException, NoSuchAlgorithmException, KeyStoreException, URISyntaxException, KeyManagementException, URLFormatException {
        if (av != null) {
            val.put("avid", getAv());
        } else {
            if (bv == null) {
                throw new URLFormatException("URL构建时没有指定视频ID");
            }
            val.put("bvid", getBv());
        }
        val.put("cid", getCid());
        return val;
    }

    @Override
    public String toString() {
        return "ID{" +
                "bv='" + bv + '\'' +
                ", av='" + av + '\'' +
                ", cid='" + cid + '\'' +
                '}';
    }
}