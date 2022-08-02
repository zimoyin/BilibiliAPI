package github.zimoyin.core.live.massage.vertx;

import io.vertx.core.buffer.Buffer;
import io.vertx.core.buffer.impl.BufferImpl;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;

/**
 * TCP的数据包
 */
@Data
public class Package {
    private volatile int sequence = 0;

    public final Logger logger = LoggerFactory.getLogger(this.getClass());

    private Buffer buffer;
    /**
     * 包头
     */
    private Header header;
    /**
     * 负载
     */
    private Body body;

    public Package(Buffer buffer) {
        this.header = new Header(buffer);
        this.body = new Body(buffer);
        this.buffer = buffer;
    }

    public  Package(){}


    public long length(){
        return buffer.length();
    }


    /**
     * 认证包
     * @param body
     * @return
     */
    public Buffer getCertificationPackage(String body){
        Buffer buffer = new BufferImpl();
        //正文+头部大小
        int i = body.getBytes().length;
        //头
        Header header = new Header();
        //正文+头部大小
        header.setPageSize(16+i);
        //头部大小
        header.setHeaderSize((short) 16);
        //协议版本
        header.setVersion((short) 2);
        //操作码
        header.setCode(7);
        //递增
        header.setSequence(sequence);
        sequence++;

        //头部
        buffer.appendBuffer(header.buildHeader());
        //正文
        buffer.appendString(body);
        return buffer;
    }

    /**
     * 心跳包
     * @return
     */
    public Buffer getHeart(){
        Header header = new Header();
        //正文+头部大小
        header.setPageSize(16);
        //头部大小
        header.setHeaderSize((short) 16);
        //协议版本
        header.setVersion((short) 2);
        //操作码
        header.setCode(2);
        //递增
        header.setSequence(sequence);
        sequence++;
        //正文
//        buffer.appendString(body);
        return header.buildHeader();
    }

    @Data
    public static class Header{
        public final Logger logger = LoggerFactory.getLogger(this.getClass());
        /**
         * 字段偏移 0
         * 包大小
         */
        private int pageSize = 16;
        /**
         * 字段偏移 4
         * 头部大小
         */
        private short headerSize = 16;
        /**
         * 字段偏移 6
         * 协议版本:
         * 0普通包正文不使用压缩                                    Body JSON
         * 1心跳及认证包正文不使用压缩                               Body 内容为房间人气值(Int 32 Big Endian)
         * 2普通包正文使用zlib压缩
         * 3普通包正文使用brotli压缩,解压为一个带头部的协议0普通包
         */
        private short version = 0;
        /**
         * 字段偏移 8
         * 操作码
         * 2	心跳包
         * 3	心跳包回复（人气值）
         * 5	普通包（命令）
         * 7	认证包
         * 8	认证包回复
         */
        private int code = 7;
        /**
         * 字段偏移 12
         * 递增
         */
        private int sequence = 0;


        /**
         * 设置头部信息
         * @param buffer
         * @return
         */
        public Buffer buildHeader(Buffer buffer){
            //正文+头部大小
            buffer.appendInt(this.pageSize);
            //头部大小
            buffer.appendShort(this.headerSize);
            //协议版本
            buffer.appendShort(this.version);
            //操作码
            buffer.appendInt(this.code);
            //递增
            buffer.appendInt(this.sequence);
            return buffer;
        }


        /**
         * 设置头部信息
         * @return
         */
        public Buffer buildHeader(){
            Buffer buffer = new BufferImpl();
            //正文+头部大小
            buffer.appendInt(this.pageSize);
            //头部大小
            buffer.appendShort(this.headerSize);
            //协议版本
            buffer.appendShort(this.version);
            //操作码
            buffer.appendInt(this.code);
            //递增
            buffer.appendInt(this.sequence);
            return buffer;
        }

        /**
         * 拆包
         * @param buffer
         */
        public Header(Buffer buffer) {
            this.pageSize=buffer.getInt(0);
            if (buffer.length() != this.pageSize) {
                logger.warn("这是一个损耗的包 包实际长度:{}, 包头描述包长度:{}",buffer.length(),this.pageSize);
                return;
            }
            this.headerSize = buffer.getShort(4);
            this.version=buffer.getShort(6);
            this.code=buffer.getInt(8);
            this.sequence=buffer.getInt(12);
        }

        public Header() {
        }

        public long length() {
            return 16;
        }
    }

    @Data
    public static class Body{
        /**
         * 字段偏移 16
         */
        private  String body;

        /**
         * 解压后的数据
         */
        private String deBody;

        /**
         * 构建 body 数据
         * @param buffer
         * @return
         */
        public Buffer buildBody(Buffer buffer){
                buffer.appendString(this.body);
                return buffer;
        }

        /**
         * 返回一个Byte 数组
         * @return
         */
        public byte[] getBytes(){
            return this.body.getBytes();
        }

        /**
         * 拆包
         * @param buffer
         */
        public Body(Buffer buffer) {
            this.body = buffer.getString(16,buffer.length());
        }


        public Body() {
        }


        public long length() {
            return getBytes().length;
        }
    }
}
