package cn.crowdtrack.weather.tools;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
//Java时间格式转换大全


public class DateTools {
    /**
     * 时间到文本
     * 全部       dateToStr
     * 日期部分   dateToStr_date
     * 时间部分   dateToStr_time
     *
     * 文本到时间 strToDate
     */



    /**
     * 获取指定日期
     *
     * @param date       日期
     * @param format_str 格式
     * @return 返回字符串
     */
    public static String dateToStr(Date date,String format_str) {
        SimpleDateFormat formatter = new SimpleDateFormat(format_str);
        String dateString = formatter.format(date);
        return dateString;
    }

    public static String dateToStr_tomorrow(Date date,String format_str) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, +1);//明天
        //calendar.add(Calendar.DAY_OF_MONTH, 0);//今天
        //calendar.add(Calendar.DAY_OF_MONTH, -1);//昨天
        Date tomorrow_date = calendar.getTime();
        SimpleDateFormat formatter = new SimpleDateFormat(format_str);
        String dateString = formatter.format(tomorrow_date);
        return dateString;
    }


    /**
     * 将时间格式字符串转换为时间 yyyy-MM-dd HH:mm:ss
     *
     * @param strDate
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static Date strToDate(String strDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(strDate, pos);
        return strtodate;
    }
}