package github.zimoyin.core.utils.shell;

import github.zimoyin.core.utils.shell.Shell;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

@Deprecated
public class FFmpeg {
    /**
     * ffmpeg 位置
     */
    private static final String ffmpegPath;

    //初始化类
    static {
        File f0 = new File("./lib/nativebin/");
        f0.mkdirs();
        //将文件移到外面
        File file1 = new File("./lib/nativebin/ffmpeg.exe");
        File file = new File("./lib/nativebin/ffmpeg");
        init0(file);
        init0(file1);

        if (Shell.isWindows()) {
            try {
                ffmpegPath = file1.getCanonicalPath();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            try {
                ffmpegPath = file.getCanonicalPath();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static String exec(String parameter) throws Exception {
        return Shell.run(ffmpegPath + " " + parameter);
    }


    /**
     * 创建外部配置文件，jar包内的文件不方便
     *
     * @param configFile 文件路径
     */
    private static void init0(File configFile) {
        boolean file1 = configFile.exists();
        //没有配置文件就创建配置文件
        if (!file1) {
            //配置文件模板输入流
            InputStream resourceAsStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("nativebin/" + configFile.getName());
            //配置文件模板输出流
            FileOutputStream fileWriter = null;
            try {
                fileWriter = new FileOutputStream(configFile.getPath());
                byte[] b = new byte[1024];
                int code = 0;
                while ((code = resourceAsStream.read(b)) != -1) {
                    fileWriter.write(b, 0, code);
                }
                fileWriter.flush();
            } catch (IOException e) {
                if (resourceAsStream != null) {
                    try {
                        resourceAsStream.close();
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }

                if (fileWriter != null) {
                    try {
                        fileWriter.close();
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }
            }
        }
    }
}
