import github.zimoyin.core.Incompleteness;
import github.zimoyin.core.utils.reflex.FindClass;

import java.lang.reflect.Method;
import java.util.List;


/**
 * 扫描项目中未完成的类与方法
 */
public class FIndIncompleteness {
    public static void main(String[] args) throws ClassNotFoundException {
        List<String> github = FindClass.getClazzName("github", true);
        for (String s : github) {
            Class<?> aClass = Class.forName(s);
            Incompleteness annotation = aClass.getAnnotation(Incompleteness.class);
            boolean a = false;
            if (annotation != null) {
                System.out.println("未完成类：" + s);
                a = true;
            }
        }
    }
}
