import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * 执行系统命令工具类
 *
 * @author Storm
 */
public class CommandUtil {

    /**
     * 默认输出字符集，设置成其它字符集中文会乱码
     */
    private static final String DEFAULT_CHARSET = "GBK";

    /**
     * 执行系统命令
     *
     * @param command 命令
     * @return 命令执行完成输出内容
     * @throws IOException          执行失败时抛出异常，由调用者捕获处理
     * @throws InterruptedException 执行中断时抛出异常，由调用者捕获处理
     */
    public static String exec(String command) throws IOException, InterruptedException {

        Process process = Runtime.getRuntime().exec(command);
        process.waitFor();
        try (
                InputStream in = process.getInputStream();
                ByteArrayOutputStream out = new ByteArrayOutputStream();
        ) {

            byte[] bytes = new byte[4096];
            int len = -1;
            while ((len = in.read(bytes)) != -1) {
                out.write(bytes, 0, len);
            }

            return out.toString(DEFAULT_CHARSET);

        }
    }

    /**
     * 视频
     * 音频
     * 输出路径
     */


    public static void main(String[] args) {
        String c = "./lib/nativebin/ffmpeg.exe -i %s -i %s -c copy %s";
        File source = new File("D:\\code\\BiliBotCore\\BiliCore\\download\\1.m4s");
        File source2 = new File("D:\\code\\BiliBotCore\\BiliCore\\download\\2.m4s");
        try {

            String command = String.format(c, source.getCanonicalPath(), source2.getCanonicalPath(), "./download/out/out.mp4");
            String result = CommandUtil.exec(command);
            System.out.println(result);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}