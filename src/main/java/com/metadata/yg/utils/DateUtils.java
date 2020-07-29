package com.metadata.yg.utils;

import org.joda.time.DateTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

    public static String getYesterDay(){
        DateTime todayDt = new DateTime();
        DateTime yestoday = todayDt.minusDays(1);
        return yestoday.toString("yyyyMMdd");
    }

    public static String formatTime(long timeUnix,String rgx){
        return new SimpleDateFormat(rgx).format(timeUnix);
    }

    public static long formatTimeTo24(Date date){
        String day=new SimpleDateFormat("yyyyMMdd").format(date.getTime());
        long timeUnix=date.getTime();
        try {
            timeUnix=new SimpleDateFormat("yyyyMMdd").parse(day).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return timeUnix;
    }

    public static Date randomDate(String beginDate, String endDate) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
            Date start = format.parse(beginDate);// 构造开始日期
            Date end = format.parse(endDate);// 构造结束日期
            // getTime()表示返回自 1970 年 1 月 1 日 00:00:00 GMT 以来此 Date 对象表示的毫秒数。
            if (start.getTime() >= end.getTime()) {
                return null;
            }
            long date = random(start.getTime(), end.getTime());
            return new Date(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Date randomDateFix(String beginDate, String endDate) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date start = format.parse(beginDate);// 构造开始日期
            Date end = format.parse(endDate);// 构造结束日期
            // getTime()表示返回自 1970 年 1 月 1 日 00:00:00 GMT 以来此 Date 对象表示的毫秒数。
            if (start.getTime() >= end.getTime()) {
                return null;
            }
            long date = random(start.getTime(), end.getTime());
            return new Date(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static long random(long begin, long end) {
        long rtn = begin + (long) (Math.random() * (end - begin));
        // 如果返回的是开始时间和结束时间，则递归调用本函数查找随机值
        if (rtn == begin || rtn == end) {
            return random(begin, end);
        }
        return rtn;
    }

    public static String randomDateMinMax(String min, String max) {
        Date randomDate = randomDate(min,max);
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        String result = format.format(randomDate);
//        System.out.println(result);
        return result;
    }


    public static String randomDateMinMaxFix(String min, String max) {
        Date randomDate = randomDateFix(min,max);
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String result = format.format(randomDate);
//        System.out.println(result);
        return result;
    }
    /**
     * 日期转时间戳
     * @param dateStr
     * @param format
     * @return
     */
    public static String date2Timestamp(String dateStr, String format) {
        String unixTimestampStr = new String();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            unixTimestampStr = String.valueOf(sdf.parse(dateStr).getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return unixTimestampStr;
    }

    public static String randomDateMinMax1(String min, String max) {
        Date randomDate = randomDate(min,max);
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        String result = format.format(randomDate);
        return result;
    }

    public static String transforDate(String time_unix) {
        long l_time = Long.valueOf(time_unix).longValue();
        Date d = new Date(l_time);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        return sdf.format(d);
    }

}
