package github.zimoyin.core.utils.shell;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Shell {

    public static String run(String exec) throws Exception {
        List<String> shell = getShell(exec);
        return run(shell, new ShellHandle() {
            @Override
            public void handle(String str) {
            }
        });
    }

    public static String run(String exec, ShellHandle handle) throws Exception {
        List<String> shell = getShell(exec);
        return run(shell, handle);
    }


    /**
     * 执行命令
     *
     * @param shellList
     * @return
     * @throws Exception
     */
    public static String run(List<String> shellList, ShellHandle handle) throws Exception {
//        String l ="";
//        for (String s : shellList) {
//            l=l+" "+s;
//        }
//        System.err.println(l);

        for (String s : shellList) {
            System.out.print(s+" ");
        }
        System.out.println();

        StringBuffer stringBuffer = new StringBuffer();
        ProcessBuilder processBuilder = new ProcessBuilder(shellList);
        processBuilder.redirectErrorStream(true);
        Process p = processBuilder.start();


        BufferedReader bs = new BufferedReader(new InputStreamReader(p.getInputStream(), Charset.forName("UTF-8")));

        //登录命令执行完毕后返回结果
//        p.waitFor();
        String line = null;
        BufferedReader ebs = null;
        //读取
        while ((line = bs.readLine()) != null) {
            handle.handle(line);
            stringBuffer.append(line).append("\n");
        }
        //读取错误
        if (p.exitValue() != 0) {
            //说明命令执行失败
            //可以进入到错误处理步骤中
            ebs = new BufferedReader(new InputStreamReader(p.getErrorStream(), Charset.forName("UTF-8")));
            line = null;
//            stringBuffer.append("错误输出：").append("\n");
            while ((line = bs.readLine()) != null) {
                handle.handle(line);
                stringBuffer.append(line).append("\n");
            }
        }

        if (ebs != null) ebs.close();
        if (bs != null) bs.close();
        p.destroy();
        return stringBuffer.toString().trim();
    }

    /**
     * kill
     *
     * @param name 进程名称
     * @return
     * @throws Exception
     */
    public static boolean kill(String name) throws Exception {
        List<String> shellList = getShell("tasklist |findstr " + "\"" + name + "\"");
        StringBuffer stringBuffer = new StringBuffer();
        ProcessBuilder processBuilder = new ProcessBuilder(shellList);
        processBuilder.redirectErrorStream(true);
        Process p = processBuilder.start();
        BufferedReader bs = new BufferedReader(new InputStreamReader(p.getInputStream(), Charset.forName("UTF-8")));

        //登录命令执行完毕后返回结果
        p.waitFor();

        String line = null;
        //读取
        while ((line = bs.readLine()) != null) {
            stringBuffer.append(line).append("\n");
        }

        String[] split = stringBuffer.toString().split("\\s+");
        int i = Integer.parseInt(split[1]);
        bs.close();
        p.destroy();

        List<String> shellList2 = getShell("taskkill /f /pid " + i);
        ProcessBuilder processBuilder2 = new ProcessBuilder(shellList2);
        processBuilder.redirectErrorStream(true);
        Process p2 = processBuilder.start();
        p2.destroy();


        return p2.exitValue() == 0;
    }


    /**
     * 解析命令,将命令转换为当前平台可执行的命令组
     *
     * @param shell 命令
     * @return
     * @throws Exception
     */
    public static List<String> getShell(String shell) throws Exception {
        List<String> shellList = new ArrayList<>();

        if (isLinux()) {
            shellList.add("sh");
            shellList.add("-c");
            //拼接用户的指令
            String text = "";
            String[] split = shell.split("\\s+");
            for (String s : split) {
                text += " " + s;
            }
            //进入用户上次进入的目录
            shellList.add(text);
        } else if (isWindows()) {
            shellList.add("cmd.exe");
            shellList.add("/c");
            //拼接用户的指令
            String[] split = shell.split("\\s+");
            for (String s : split) {
                shellList.add(s);
            }
        } else {
            throw new NullPointerException("未知的操作系统，无法获取到命令行");
        }


        return shellList;
    }


    /**
     * 判断是linux系统还是其他系统
     * 如果是Linux系统，返回true，否则返回false
     */
    public static boolean isLinux() {
        return System.getProperty("os.name").toLowerCase().contains("linux");
    }

    /**
     * 判断是Windows系统还是其他系统
     * 如果是Windows系统，返回true，否则返回false
     */
    public static boolean isWindows() {
        return System.getProperty("os.name").toLowerCase().contains("windows");
    }


}
