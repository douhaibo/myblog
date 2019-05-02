package cn.ljtnono.myblog.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *  日期工具类
 *  @author ljt
 *  @date 2018/11/29
 *  @version 1.0
*/
public class DateUtil {

    private static final Integer MINUTE = 60;

    /**
     * 防止工具类进行实例化操作
     */
    private DateUtil() {
        super();
    }

    /**
     * yyyy-MM-dd 格式的日期
     * @param date 日期对象
     * @return String
     */
    public static String normalFormat(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

    public static Date stringToDate(String dateStr) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.parse(dateStr);
    }

    /**
     * HH:mm:ss 格式的日期
     * @param date 日期对象
     * @return String
     */
    public static String timeFormat(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        return format.format(date);
    }

    /**
     * yyyy-MM-dd HH:mm:ss 格式的日期
     * @param date 日期对象
     * @return String
     */
    public static String dateTimeFormat(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(date);
    }

    /**
     *  将日期转换为hh:mm:ss格式
     * @param duration 秒
     * @return hh:mm:ss 格式的时间字符串
     */
    public static String formatTime(int duration) {
        int hour = 0;
        int minute = 0;
        int second = 0;
        second = duration / 1000;
        if(second > MINUTE){
            minute = second / 60;
            second = second % 60;
        }
        if(minute > MINUTE){
            hour = minute / MINUTE;
            minute = minute % MINUTE;
        }
        return String.format("%02d:%02d:%02d",hour,minute,second);
    }

    /**
     * 指定获取一天中指定时间的Date对象
     * @return date
     */
    public static Date getSomeTime(int hour,int min,int sec) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,hour);
        calendar.set(Calendar.MINUTE,min);
        calendar.set(Calendar.SECOND,sec);
        return calendar.getTime();
    }
}
