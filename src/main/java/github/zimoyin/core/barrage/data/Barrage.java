package github.zimoyin.core.barrage.data;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.Objects;

/**
 * 弹幕数据类型
 */
@lombok.Data
public class Barrage implements Data {

    /**
     * 发送弹幕的人，直播弹幕才能获取，视频弹幕不能直接获取到
     */
    private String author;

    /**
     * 发送弹幕的人的mid
     */
    private String mid;


    /**
     * 弹幕在视频内出现的秒数
     */
    private double showTime;
    /**
     * 弹幕类型
     */
    private BarrageType type;
    /**
     * 弹幕大小
     */
    private BarrageFontSize size;
    /**
     * 弹幕颜色 十进制RGB888值
     */
    private BarrageRGB rbg;
    /**
     * 弹幕发送时间
     */
    private long sendTime;
    /**
     * 弹幕池类型
     */
    private BarragePoolType barragePoolType;
    /**
     * 发送者mid的HASH
     * 用于屏蔽用户和查看用户发送的所有弹幕 也可反查用户id
     */
    private String midHash;
    /**
     * 弹幕dmid
     * 唯一 可用于操作参数
     */
    private String dmid;
    /**
     * 弹幕的屏蔽等级
     * 0-10，低于用户设定等级的弹幕将被屏蔽  新增，下方样例未包含）
     */
    private int shieldingLevel;
    /**
     * 弹幕正文
     */
    private String text;

    /**
     * 作用未知
     */
    private String action;


    public Barrage() {
    }

    public Barrage(double showTime, BarrageType type, BarrageFontSize size, BarrageRGB rbg, long sendTime, BarragePoolType barragePoolType, String midHash, String dmid, int shieldingLevel, String text) {
        this.showTime = showTime;
        this.type = type;
        this.size = size;
        this.rbg = rbg;
        this.sendTime = sendTime;
        this.barragePoolType = barragePoolType;
        this.midHash = midHash;
        this.dmid = dmid;
        this.shieldingLevel = shieldingLevel;
        this.text = text;
    }






    /**
     *0	视频内弹幕出现时间	float	秒
     * 1	弹幕类型	int32	1 2 3：普通弹幕
     * 4：底部弹幕
     * 5：顶部弹幕
     * 6：逆向弹幕
     * 7：高级弹幕
     * 8：代码弹幕
     * 9：BAS弹幕（pool必须为2）
     * 2	弹幕字号	int32	18：小
     * 25：标准
     * 36：大
     * 3	弹幕颜色	int32	十进制RGB888值
     * 4	弹幕发送时间	int32	时间戳
     * 5	弹幕池类型	int32	0：普通池
     * 1：字幕池
     * 2：特殊池（代码/BAS弹幕）
     * 6	发送者mid的HASH	string	用于屏蔽用户和查看用户发送的所有弹幕 也可反查用户id
     * 7	弹幕dmid	int64	唯一 可用于操作参数
     * 8	弹幕的屏蔽等级	int32	0-10，低于用户设定等级的弹幕将被屏蔽
     * （新增，下方样例未包含）
     */
    public Barrage(double showTime, int type, int size,
                   int rbg, long sendTime,
                   int barragePoolType, String midHash,
                   String dmid, int shieldingLevel,
                   String text) {
        this.showTime = showTime;
        this.type = new BarrageType(type);
        this.size = new BarrageFontSize(size);
        this.rbg = new BarrageRGB(rbg);
        this.sendTime = sendTime;
        this.barragePoolType = new BarragePoolType(barragePoolType);
        this.midHash = midHash;
        this.dmid = dmid;
        this.shieldingLevel = shieldingLevel;
        this.text = text;
    }

    public Barrage(String text) {
        this.text = text;
    }

    /**
     * 弹幕在视频内出现的秒数
     * @return
     */
    public double getShowTime() {
        return showTime;
    }

    /**
     * 弹幕在视频内出现的秒数
     * @param showTime
     */
    public void setShowTime(double showTime) {
        this.showTime = showTime;
    }

    /**
     * 弹幕类型
     * @return
     */
    public BarrageType getType() {
        return type;
    }

    /**
     * 弹幕类型
     * @param type
     */
    public void setType(BarrageType type) {
        this.type = type;
    }

    public void setType(int type) {
        this.type = new BarrageType(type);
    }


    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    /**
     * 弹幕大小字号
     */
    public BarrageFontSize getSize() {
        return size;
    }


    /**
     * 弹幕大小字号
     * @param size
     */
    public void setSize(BarrageFontSize size) {
        this.size = size;
    }
    /**
     * 弹幕大小字号
     * @param size
     */
    public void setSize(int size) {
        this.size = new BarrageFontSize(size);
    }

    /**
     * 弹幕颜色 10进制
     */
    public BarrageRGB getRbg() {
        return rbg;
    }

    /**
     * 弹幕颜色 10进制
     * @param rbg
     */
    public void setRbg(BarrageRGB rbg) {
        this.rbg = rbg;
    }

    public void setRbg(int rbg) {
        this.rbg = new BarrageRGB(rbg);
    }

    /**
     * 发送时间
     */
    public long getSendTime() {
        return sendTime;
    }

    /**
     * 发送时间
     * @param sendTime
     */
    public void setSendTime(long sendTime) {
        this.sendTime = sendTime;
    }

    /**
     * 弹幕池类型
     * @return
     */
    public BarragePoolType getBarragePoolType() {
        return barragePoolType;
    }

    /**
     * 弹幕池类型
     * @param barragePoolType
     */
    public void setBarragePoolType(BarragePoolType barragePoolType) {
        this.barragePoolType = barragePoolType;
    }

    public void setBarragePoolType(int barragePoolType) {
        this.barragePoolType = new BarragePoolType(barragePoolType);
    }

    /**
     * 发送者mid的HASH 用于屏蔽用户和查看用户发送的所有弹幕 也可反查用户id
     * @return
     */
    public String getMidHash() {
        return midHash;
    }

    /**
     * 发送者mid的HASH 用于屏蔽用户和查看用户发送的所有弹幕 也可反查用户id
     * @param midHash
     */
    public void setMidHash(String midHash) {
        this.midHash = midHash;
    }

    /**
     *
     * 弹幕dmid 唯一 可用于操作参数
     * @return
     */
    public String getDmid() {
        return dmid;
    }

    /**
     * 弹幕dmid 唯一 可用于操作参数
     *
     * @param dmid
     */
    public void setDmid(String dmid) {
        this.dmid = dmid;
    }

    /**
     * 屏蔽等级
     * @return
     */
    public int getShieldingLevel() {
        return shieldingLevel;
    }

    /**
     * 设置弹幕屏蔽等级  0-10
     * @param shieldingLevel
     */
    public void setShieldingLevel(int shieldingLevel) {
        this.shieldingLevel = shieldingLevel;
    }

    /**
     * 返回弹幕内容
     * @return
     */
    public String getText() {
        return text;
    }

    /**
     * 设置弹幕内容
     * @param text
     */
    public void setText(String text) {
        this.text = text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Barrage barrage = (Barrage) o;
        return Double.compare(barrage.showTime, showTime) == 0 && sendTime == barrage.sendTime && Objects.equals(type, barrage.type) && Objects.equals(size, barrage.size) && Objects.equals(rbg, barrage.rbg) && Objects.equals(barragePoolType, barrage.barragePoolType) && Objects.equals(midHash, barrage.midHash) && Objects.equals(dmid, barrage.dmid) && Objects.equals(shieldingLevel, barrage.shieldingLevel) && Objects.equals(text, barrage.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(showTime, type, size, rbg, sendTime, barragePoolType, midHash, dmid, shieldingLevel, text);
    }

//    @Override
//    public String toString() {
//        return "Barrage{" +
//                "showTime=" + showTime +
//                ", type=" + type.val +
//                ", size=" + size.val +
//                ", rbg=" + rbg.getValHEX().toUpperCase() +
//                ", sendTime=" + sendTime +
//                ", barragePoolType=" + barragePoolType.val +
//                ", midHash='" + midHash + '\'' +
//                ", dmid='" + dmid + '\'' +
//                ", shieldingLevel='" + shieldingLevel + '\'' +
//                ", text='" + text + '\'' +
//                ", action='" + action + '\'' +
//                '}';
//    }

    /**
     * 将Barrage 对象 序列化成 json
     * @param barrage
     * @return
     */
    public String toJson(Barrage barrage){
        String s = JSON.toJSONString(barrage);
        return s;
    }

    public String toJson(){
        String s = JSON.toJSONString(this);
        return s;
    }


    /**
     * 获取弹幕内容
     * @return
     */
    @Override
    public String toStringOfContent() {
        return getText();
    }

    /**
     * 依次给Barrage赋值 ,索引顺序安装类中变量顺序定义
     *
     * @param index
     * @param val
     */
    public void setVal(int index, double val,String val2) {

        switch (index) {
            case 1:
                this.showTime = val;
                break;
            case 2:
                this.type = new BarrageType((int) val);
                break;
            case 3:
                this.size = new BarrageFontSize((int) val);
                break;
            case 4:
                this.rbg = new BarrageRGB((int) val);
                break;
            case 5:
                this.sendTime = (long) val;
                break;
            case 6:
                this.barragePoolType = new BarragePoolType((int) val);
                break;
            case 7:
                this.midHash = val2;
                break;
            case 8:
                this.dmid = val2;
                break;
            case 9:
                this.shieldingLevel = (int) val;
                break;
            case 10:
                this.text = val2;
                break;
        }
    }

    /**
     * 弹幕类型<br>
     * <p>
     * 1 2 3：普通弹幕<br>
     * 4：底部弹幕<br>
     * 5：顶部弹幕<br>
     * 6：逆向弹幕<br>
     * 7：高级弹幕<br>
     * 8：代码弹幕<br>
     * 9：BAS弹幕（pool必须为2）<br>
     */
    static class BarrageType {
        /**
         * 普通弹幕
         */
        public static final int ORDINAEY_1 = 1;
        /**
         * 普通弹幕
         */
        public static final int ORDINAEY_2 = 2;
        /**
         * 普通弹幕
         */
        public static final int ORDINAEY_3 = 3;
        /**
         * 底部弹幕
         */
        public static final int BOTTOM = 4;
        /**
         * 顶部弹幕
         */
        public static final int TOP = 5;
        /**
         * 逆向弹幕
         */
        public static final int REVERSE = 6;
        /**
         * 高级弹幕
         */
        public static final int ADVANCE = 7;
        /**
         * 代码弹幕
         */
        public static final int CODE = 8;
        /**
         * BAS弹幕（pool必须为2）
         */
        public static final int BAS = 9;


        private int val;

        public BarrageType(int val) {
            this.val = val;
        }

        public int getOrdinaey1() {
            return ORDINAEY_1;
        }

        public int getOrdinaey2() {
            return ORDINAEY_2;
        }

        public int getOrdinaey3() {
            return ORDINAEY_3;
        }

        public int getBOTTOM() {
            return BOTTOM;
        }

        public int getTOP() {
            return TOP;
        }

        public int getREVERSE() {
            return REVERSE;
        }

        public int getADVANCE() {
            return ADVANCE;
        }

        public int getCODE() {
            return CODE;
        }

        public int getBAS() {
            return BAS;
        }

        public int getVal() {
            return val;
        }

        public void setVal(int val) {
            this.val = val;
        }


        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            BarrageType that = (BarrageType) o;
            return val == that.val;
        }

        @Override
        public int hashCode() {
            return Objects.hash(val);
        }

        @Override
        public String toString() {
            return "BarrageType{" +
                    "val=" + val +
                    '}';
        }
    }

    /**
     * 弹幕大小<br>
     * <p>
     * 18：小<br>
     * 25：标准<br>
     * 36：大<br>
     */
    static class BarrageFontSize {
        public final static int SMALL = 18;
        public final static int STANDARD = 25;
        public final static int LARGE = 36;

        private int val;

        public BarrageFontSize(int val) {
            this.val = val;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            BarrageFontSize that = (BarrageFontSize) o;
            return val == that.val;
        }

        @Override
        public int hashCode() {
            return Objects.hash(val);
        }

        @Override
        public String toString() {
            return "BarrageFontSize{" +
                    "val=" + val +
                    '}';
        }
    }

    /**
     * 弹幕池类型
     * 0：普通池<br>
     * 1：字幕池<br>
     * 2：特殊池（代码/BAS弹幕）<br>
     */
    static class BarragePoolType {
        public final static int ORDINAEY = 0;
        public final static int SUBTITLE = 1;
        public final static int OTHER = 2;

        private int val;

        public BarragePoolType(int val) {
            this.val = val;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            BarragePoolType that = (BarragePoolType) o;
            return val == that.val;
        }

        @Override
        public int hashCode() {
            return Objects.hash(val);
        }

        @Override
        public String toString() {
            return "BarragePoolType{" +
                    "val=" + val +
                    '}';
        }
    }

    /**
     * 弹幕颜色<br>
     * 颜色	HEX（RGB888）    DEC（RGB888）<br>
     * 红色	FE0302	        16646914<br>
     * 橘红	FF7204	        16740868<br>
     * 橘黄	FFAA02	        16755202<br>
     * 淡黄	FFD302	        16765698<br>
     * 黄色	FFFF00	        16776960<br>
     * 草绿	A0EE00	        10546688<br>
     * 绿色	00CD00	        52480<br>
     * 墨绿	019899	        104601<br>
     * 紫色	4266BE	        4351678<br>
     * 青色	89D5FF	        9022215<br>
     * 品红	CC0273	        13369971<br>
     * 黑色	222222	        2236962<br>
     * 灰色	9B9B9B	        10197915<br>
     * 白色	FFFFFF	        16777215<br>
     */
    static class BarrageRGB {
        /**
         * 十进制RGB888值
         */
        private int val;

        /**
         * 十进制RGB888值
         *
         * @param val
         */
        public BarrageRGB(int val) {
            this.val = val;
        }

        public String getValHEX() {
            return Integer.toHexString(val);
        }

        public int getVal() {
            return val;
        }

        /**
         * 十进制RGB888值
         *
         * @param val
         */
        public void setVal(int val) {
            this.val = val;
        }


        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            BarrageRGB that = (BarrageRGB) o;
            return val == that.val;
        }

        @Override
        public int hashCode() {
            return Objects.hash(val);
        }

        @Override
        public String toString() {
            return "BarrageRGB{" +
                    "val=" + val +
                    '}';
        }
    }
}
