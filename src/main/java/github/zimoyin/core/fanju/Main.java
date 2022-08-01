package github.zimoyin.core.fanju;

import github.zimoyin.core.fanju.info.SeriesINFO;
import github.zimoyin.core.fanju.pojo.info.seriesI.Episodes;
import github.zimoyin.core.fanju.pojo.info.seriesI.Result;
import github.zimoyin.core.fanju.pojo.info.seriesI.SeriesJsonRootBean;
import org.apache.http.HttpException;

import java.util.List;

/**
 * 理想状态，提供bv、ssid、ed即可获取到番剧信息，提供setPage来确定下载某一集
 */
public class Main {
    public static void main(String[] args) throws HttpException {
        SeriesINFO series = new SeriesINFO();
//        SeriesJsonRootBean pojo = series.getPojo("ss41410");
        SeriesJsonRootBean pojo = series.getPojo("ep567956");

        Episodes ep567956 = series.getEpisodes("ep567956");
        System.out.println(ep567956);
    }
}
