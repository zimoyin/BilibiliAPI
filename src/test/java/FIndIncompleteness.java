import github.zimoyin.core.Incompleteness;
import github.zimoyin.core.utils.reflex.FindClass;

import java.lang.reflect.Method;
import java.util.List;


/**
 * 扫描项目中未完成的类与方法
 */
public class FIndIncompleteness {
    public static void main(String[] args) throws ClassNotFoundException {
        //扫描 github 下所有的类
        List<String> github = FindClass.getClazzName("github", true);
        //遍历类路径
        for (String s : github) {
            //获取类对象
            Class<?> aClass = Class.forName(s);
            //类上面是否存在 @Incompleteness 注解
            Incompleteness annotation = aClass.getAnnotation(Incompleteness.class);
            boolean a = false;
            if (annotation != null) {
                System.out.println("未完成类：" + s);
                a = true;
            }
        }
    }
}
