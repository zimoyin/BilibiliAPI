import java.io.File;
import java.io.IOException;

public class BuildFilePathTest {
    public static void main(String[] args) throws IOException {
        String fileName = "【DIY木刀】紫光檀1:1可拉刀光龙刃 \\看/到:最?后\"一<>定|不会后悔-0.mp4";
        fileName = fileName.replaceAll("[/:*?\"<>|\\\\]","_");

        System.out.println(fileName);
        new File("./download/{_-=()&^%$#@!';`~.txt").createNewFile();
    }
}
