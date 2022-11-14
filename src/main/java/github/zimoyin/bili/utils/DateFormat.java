package github.zimoyin.bili.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateFormat {

    /**
     * 当前日期格式化成 YYYY-MM 格式
     * @return
     */
    public static String format(){
        //获取日期
        //导 import java.util.Date; 下的包
        Date date = new Date();
        //Fri Nov 29 10:05:00 CST 2019

        SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM");
        String datef = sdf.format(date);
        return datef;
    }

    /**
     * 当前日期
     */
    public static String format(String dateTemp){
        //获取日期
        //导 import java.util.Date; 下的包
        Date date = new Date();
        //Fri Nov 29 10:05:00 CST 2019

        SimpleDateFormat sdf = new SimpleDateFormat(dateTemp);
        String datef = sdf.format(date);
        return datef;
    }

    /**
     * 当前日期
     */
    public static String format(Date date,String dateTemp){
        SimpleDateFormat sdf = new SimpleDateFormat(dateTemp);
        String datef = sdf.format(date);
        return datef;
    }
    /**
     * 当前日期
     */
    public static String format(String date,String strDateTemp,String dateTemp) throws ParseException {
//        String strDate = "2011-11-11 10:11:30.345" ;
        // 准备第一个模板，从字符串中提取出日期数字
//        String pat1 = "yyyy-MM-dd HH:mm:ss.SSS" ;
        // 准备第二个模板，将提取后的日期数字变为指定的格式
//        String pat2 = "yyyy年MM月dd日 HH时mm分ss秒SSS毫秒" ;
        SimpleDateFormat sdf1 = new SimpleDateFormat(strDateTemp) ;        // 实例化模板对象
        SimpleDateFormat sdf2 = new SimpleDateFormat(dateTemp) ;        // 实例化模板对象
        Date d = sdf1.parse(date) ;
        return sdf2.format(d);
    }


    public static void main(String[] args) {
        Date date = new Date();//获取当前时间
        Calendar calendar = Calendar.getInstance(); //创建Calendar 的实例
        calendar.add(Calendar.MONTH, -1);//当前时间减去一个月，即一个月前的时间
        calendar.add(Calendar.MONTH, -1);//当前时间减去一个月，即一个月前的时间
        SimpleDateFormat sdf2 = new SimpleDateFormat("YYYY-MM") ;        // 实例化模板对象
        String format = sdf2.format(calendar.getTime());
        System.out.println(format);
    }


}
