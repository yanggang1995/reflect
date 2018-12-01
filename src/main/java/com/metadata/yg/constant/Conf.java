package com.metadata.yg.constant;

import com.metadata.yg.utils.FileUtils;

import java.util.Properties;

public final class Conf {
    private static Properties prop = FileUtils.getConfig();
    public static final String EXECUTORFILE = "conf/source.xml";
    public static final String CONFIGPATH = "conf/config.properties";
    public static final Integer RADIX = Integer.parseInt((String) prop.get("radix"));
    public static final String COLUMN = (String) prop.get("column");
    public static final String ROW = (String) prop.get("row");
    public static final String SUFFIX = (String) prop.get("suffix");
    public static final String OUTPATH = (String) prop.get("outpath");
    public static final String DRIVERTYPE = (String) prop.get("driverType");
    public static final String DRIVER = (String) prop.getProperty(DRIVERTYPE + ".driver");
    public static final String URL = prop.getProperty(DRIVERTYPE + ".url");
    public static final String USER = prop.getProperty(DRIVERTYPE + ".user");
    public static final String PASSWORD = prop.getProperty(DRIVERTYPE + ".password");
}
