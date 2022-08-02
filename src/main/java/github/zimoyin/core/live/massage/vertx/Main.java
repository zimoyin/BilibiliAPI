package github.zimoyin.core.live.massage.vertx;

import com.nixxcode.jvmbrotli.common.BrotliLoader;
import com.nixxcode.jvmbrotli.dec.BrotliInputStream;
import github.zimoyin.core.live.massage.MassageStream;
import github.zimoyin.core.live.pojo.info.DanmuInfoJsonRootBean;
import github.zimoyin.core.live.pojo.info.Host;
import github.zimoyin.core.utils.IO;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.buffer.impl.BufferImpl;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
        long id = 24000767;
//        DanmuInfoJsonRootBean pojo = new MassageStream().getJsonPojo(id);
//        pojo.setRoomID(id);
//        Vertx.vertx().deployVerticle(new TcpVerticle(pojo));
        Vertx.vertx().deployVerticle(new TcpVerticle(id));
    }
}
