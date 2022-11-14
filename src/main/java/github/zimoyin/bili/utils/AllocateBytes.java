package github.zimoyin.bili.utils;

import java.util.HashMap;

/**
 * 分配字节，用来多线程断点下载
 */
public class AllocateBytes {
    /**
     *
     * @param ThreadCount 线程数
     * @param FileSize //文件大小
     * @return key(开始位置):value(结束位置)
     */
   public static HashMap<Long,Long> CalculationBytes(int ThreadCount,long FileSize){
        HashMap<Long,Long> Index = new HashMap<>();
        //每个线程平均分配多少字节
        long blockSize = FileSize / ThreadCount;
        //计算每个线程下载的开始位置和结束位置
        for (int i = 0; i < ThreadCount; i++) {
            long startIndex = i * blockSize;   //每个线程下载的开始位置
            long endIndex = (i + 1) * blockSize - 1;//计算终止位置。i+1不能为零否则，分配失败。blockSize - 1要将1个字节抛给后面的线程否则后面线程会重复下载同一个位置的字节

            //判断是否为最后一个线程
            //最后一个线程必须包含文件剩余的字节
            if (i == ThreadCount - 1) {
                //讲该线程的起始位置到文件末尾的字节全部归他
                endIndex = FileSize;
            }
            Index.put(startIndex,endIndex);
        }
        return Index;
    }
}
