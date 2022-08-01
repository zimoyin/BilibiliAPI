package github.zimoyin.core.utils.reflex;


import java.io.File;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * 通过反射来查找包下的所有的类
 */
public class FindClass {


    private static final String CLASS_SUFFIX = ".class";
    private static final String CLASS_FILE_PREFIX = File.separator + "classes" + File.separator;
    private static final String PACKAGE_SEPARATOR = ".";

    public static void main(String[] args) throws MalformedURLException {

    }

    /**
     * 查找包下的所有类的名字
     *
     * @return List集合，内容为类的全名
     */
    public static List<String> getClazzName(String packageName, boolean showChildPackageFlag) {

        List<String> result = new ArrayList<>();
        String suffixPath = packageName.replaceAll("\\.", "/");


        //获取线程的类加载器（线程类加载器突破双亲委派）
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        try {
            //获取url： 查找具有给定(路径)名称的所有资源
            Enumeration<URL> urls = loader.getResources(suffixPath);
            while (urls.hasMoreElements()) {
                URL url = urls.nextElement();


                if (url != null) {
                    //获取URL的协议，如果是class就是file协议，jar就是jar协议
                    String protocol = url.getProtocol();
                    if ("file".equals(protocol)) {
                        String path = url.getPath();//类路径
                        result.addAll(getAllClassNameByFile(new File(path), showChildPackageFlag));
                    } else if ("jar".equals(protocol)) {
                        JarFile jarFile = null;
                        try {
                            jarFile = ((JarURLConnection) url.openConnection()).getJarFile();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        if (jarFile != null) {
                            result.addAll(getAllClassNameByJar(jarFile, packageName, showChildPackageFlag));

                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }


    /**
     * 递归获取所有class文件的名字
     *
     * @param file
     * @param flag 是否需要迭代遍历
     * @return List
     */
    private static List<String> getAllClassNameByFile(File file, boolean flag) {
        List<String> result = new ArrayList<>();
        if (!file.exists()) {
            return result;
        }
        if (file.isFile()) {
            String path = file.getPath();
            // 注意：这里替换文件分割符要用replace。因为replaceAll里面的参数是正则表达式,而windows环境中File.separator="\\"的,因此会有问题
            if (path.endsWith(CLASS_SUFFIX)) {
                path = path.replace(CLASS_SUFFIX, "");
                // 从"/classes/"后面开始截取
                String clazzName = path.substring(path.indexOf(CLASS_FILE_PREFIX) + CLASS_FILE_PREFIX.length())
                        .replace(File.separator, PACKAGE_SEPARATOR);
                if (-1 == clazzName.indexOf("$")) {
                    result.add(clazzName);
                }
            }
            return result;

        } else {
            File[] listFiles = file.listFiles();
            if (listFiles != null && listFiles.length > 0) {
                for (File f : listFiles) {
                    if (flag) {
                        result.addAll(getAllClassNameByFile(f, flag));
                    } else {
                        if (f.isFile()) {
                            String path = f.getPath();
                            if (path.endsWith(CLASS_SUFFIX)) {
                                path = path.replace(CLASS_SUFFIX, "");
                                // 从"/classes/"后面开始截取
                                String clazzName = path.substring(path.indexOf(CLASS_FILE_PREFIX) + CLASS_FILE_PREFIX.length())
                                        .replace(File.separator, PACKAGE_SEPARATOR);
                                if (-1 == clazzName.indexOf("$")) {
                                    result.add(clazzName);
                                }
                            }
                        }
                    }
                }
            }
            return result;
        }
    }


    /**
     * 递归获取jar所有class文件的名字
     *
     * @param jarFile
     * @param packageName 包名
     * @param flag        是否需要迭代遍历
     * @return List
     */
    private static List<String> getAllClassNameByJar(JarFile jarFile, String packageName, boolean flag) {
        List<String> result = new ArrayList<>();
        Enumeration<JarEntry> entries = jarFile.entries();
        while (entries.hasMoreElements()) {
            JarEntry jarEntry = entries.nextElement();
            String name = jarEntry.getName();
            // 判断是不是class文件
            if (name.endsWith(CLASS_SUFFIX)) {
                name = name.replace(CLASS_SUFFIX, "").replace("/", ".");
                if (flag) {
                    // 如果要子包的文件,那么就只要开头相同且不是内部类就ok
                    if (name.startsWith(packageName) && -1 == name.indexOf("$")) {
                        result.add(name);
                    }
                } else {
                    // 如果不要子包的文件,那么就必须保证最后一个"."之前的字符串和包名一样且不是内部类
                    if (packageName.equals(name.substring(0, name.lastIndexOf("."))) && -1 == name.indexOf("$")) {
                        result.add(name);
                    }
                }
            }
        }
        return result;
    }


    /**
     * 获取jar包下的class文件名称
     *
     * @param jarFilePath
     * @return
     * @URL https://blog.csdn.net/xiao1_1bing/article/details/85122085
     */
    public static List<String> getJarClassName(String jarFilePath) {
        ArrayList<String> classNames = new ArrayList<>();
        JarFile jarFile = null;
        try {
            jarFile = new JarFile(jarFilePath);
            Enumeration<JarEntry> entrys = jarFile.entries();
            while (entrys.hasMoreElements()) {
                JarEntry jarEntry = entrys.nextElement();

                //如果是class文件就放入集合
                try {
                    String classPath = jarEntry.getName().replaceAll("/", "\\.");
                    if (classPath.lastIndexOf(".") == classPath.length()-1 ) continue;
                    String lastName = classPath.substring(classPath.lastIndexOf("."));
                    if (lastName.equals(".class")) classNames.add(classPath.substring(0,classPath.lastIndexOf(".")));//去掉.class后缀
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return classNames;
    }
}
