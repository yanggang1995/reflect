package com.metadata.yg.constant;

import com.metadata.yg.utils.DateUtils;
import com.metadata.yg.utils.FileUtils;

import java.util.Properties;

public final class Conf {
    private static Properties prop;
    private static final String projectPath;

    static {
        prop = FileUtils.getConfig();
        projectPath = FileUtils.getProjectPath() + "/";
//        projectPath="";
    }

    public static final String EXECUTORFILE = projectPath + "conf/source.xml";
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
    public static final boolean isSplit ="false".equals(prop.getProperty("isSplit")) ? false : true;
    public static final String fileDay=prop.getProperty("fileDay")==null?DateUtils.getYesterDay():prop.getProperty("fileDay");

    public static final String EXETIME = "20181101";
    public static final String PRODUCTSPATH = projectPath + "conf/product.json";
    public static final String ZPRODUCTSPATH = projectPath + "conf/zproduct.json";
    public static final String TABLENUMS = projectPath + "conf/makeData.json";
}
