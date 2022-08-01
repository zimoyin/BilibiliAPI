package github.zimoyin.core.utils;

import github.zimoyin.core.video.download.DownloadControl;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

public class IO {

    public static byte[] toByte(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] b = new byte[1024];
        int len = 0;
        while ((len = inputStream.read(b)) != -1) {
            byteArrayOutputStream.write(b, 0, len);
        }
        inputStream.close();
        return byteArrayOutputStream.toByteArray();
    }


    public static void toFile(InputStream inputStream, String path) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(path);
        byte[] b = new byte[1024];
        int len = 0;
        while ((len = inputStream.read(b)) != -1) {
            fileOutputStream.write(b, 0, len);
        }
        fileOutputStream.flush();
        fileOutputStream.close();
    }

    public static void toFile(InputStream inputStream, String path, DownloadControl control) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(path);
        byte[] b = new byte[65535/3*2];
        int code = 0;
        //累计下载大小
        int size = 0;
        while ((code = inputStream.read(b)) != -1) {
            fileOutputStream.write(b, 0, code);
            size+=code;
            if (control != null){
                if (control.isStop()) break;
                control.setLength(code,size,control.getFileSize());
            }
        }
        if (control != null) control.finish();
        fileOutputStream.flush();
        fileOutputStream.close();
    }

    /**
     * 获取指定后缀的文件
     *
     * @return
     */
    public static ArrayList<File> getFileType(String path, String fileTyle) {
        ArrayList<File> fileArrayList = new ArrayList<>();
        File file = new File(path);
        if (file.isDirectory()) {
            File[] files = file.listFiles(pathname -> {
                String fileName = pathname.getName();
                if (!fileName.contains(".")){
                    return false;
                }
                String tyle = fileName.substring(fileName.lastIndexOf(".")+1, fileName.length());
                if (tyle.equalsIgnoreCase(fileTyle)) return true;
                return false;
            });
            Collections.addAll(fileArrayList, files);
        } else {
            String fileName = file.getName();
            if (!fileName.contains(".")){
                return fileArrayList;
            }
            String tyle = fileName.substring(fileName.lastIndexOf("."), fileName.length());
            if (tyle.equalsIgnoreCase(fileTyle)) fileArrayList.add(file);
        }
        return fileArrayList;
    }
}
