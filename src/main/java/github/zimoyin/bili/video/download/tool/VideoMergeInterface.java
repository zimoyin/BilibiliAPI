package github.zimoyin.bili.video.download.tool;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public interface VideoMergeInterface {
    /**
     * 合并视频
     *
     * @return 是否成功
     */
    public abstract boolean merge();

    /**
     * 合并视频
     *
     * @param outPath       输出路径（文件夹）
     * @param inputFilePath 输入路径（文件）
     * @return 是否成功
     */
    public abstract boolean merge(final String outPath, final String... inputFilePath);

    /**
     * @param outPath        输出路径（文件夹）
     * @param inputFilePath  输入路径（文件）
     * @param inputFilePath2 输入路径（文件）
     * @return
     */
    public abstract boolean merge(final String outPath, final String inputFilePath, final String inputFilePath2);

    /**
     * @param inputPath 输入路径（文件夹）
     * @param outPath   输出路径（文件夹）
     * @return
     */
    public abstract boolean mergeAll(final String inputPath, final String outPath);

    /**
     * @return
     */
    public abstract boolean isDeleteCached();

    /**
     * 获取该文件夹下所有待合并的文件，并配置合并列表
     *
     * @return 合并列表
     */
    public default HashMap<String, List<File>> getFiles(final String inputPath) {
        File file = new File(inputPath);
        if (!file.exists()) throw new NullPointerException("文件路径不存在: " + file.toString());
        if (!file.isDirectory()) throw new IllegalArgumentException("文件路径不是一个文件夹:" + file);
        HashMap<String, List<File>> map = new HashMap<>();
        for (File file0 : Objects.requireNonNull(file.listFiles())) {
            String name = file0.getName().substring(file0.getName().indexOf("."));
            List<File> list = map.get(name);
            if (list == null) list = new ArrayList<>();
            list.add(file0);
            if (list.size() > 2) throw new IllegalArgumentException("存在多个同名文件");
        }
        return map;
    }
}
