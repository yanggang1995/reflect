package com.metadata.yg.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

    public static String getDate(){
        return new SimpleDateFormat("yyyyMMddHHmm").format(new Date());
    }

}
