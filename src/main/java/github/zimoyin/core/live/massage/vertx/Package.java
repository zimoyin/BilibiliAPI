package github.zimoyin.core.live.massage.vertx;

import io.vertx.core.buffer.Buffer;
import io.vertx.core.buffer.impl.BufferImpl;
import lombok.Data;

import java.io.ByteArrayOutputStream;

public class Package {
    private volatile int sequence = 0;

    /**
     * 认证包
     * @param body
     * @return
     */
    public Buffer getCertificationPackage(String body){
        Buffer buffer = new BufferImpl();
        //正文+头部大小
        int i = body.getBytes().length;
        buffer.appendInt(16+i);
        //头部大小
        buffer.appendShort((short) 16);
        /**
         * 协议版本:
         * 0普通包正文不使用压缩
         * 1心跳及认证包正文不使用压缩
         * 2普通包正文使用zlib压缩
         * 3普通包正文使用brotli压缩,解压为一个带头部的协议0普通包
         */
        buffer.appendShort((short) 2);
        /**
         * 操作码
         * 2	心跳包
         * 3	心跳包回复（人气值）
         * 5	普通包（命令）
         * 7	认证包
         * 8	认证包回复
         */
        buffer.appendInt(7);
        //递增
        buffer.appendInt(sequence);
        sequence++;
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
            this.headerSize = buffer.getShort(4);
            this.version=buffer.getShort(6);
            this.code=buffer.getInt(8);
            this.sequence=buffer.getInt(12);
        }

        public Header() {
        }
    }

    @Data
    public static class Body{
        /**
         * 字段偏移 16
         */
        private  String body;

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
         * 拆包
         * @param buffer
         */
        public Body(Buffer buffer) {
            this.body = buffer.getString(16,buffer.length());
        }


        public Body() {
        }
    }
}
