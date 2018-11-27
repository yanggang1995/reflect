package com.metadata.yg.utils;

import org.joda.time.DateTime;

public class DateUtils {

    public static String getYesterDay(){
        DateTime todayDt = new DateTime();
        DateTime yestoday = todayDt.minusDays(1);
        return yestoday.toString("yyyyMMdd");
    }
}
