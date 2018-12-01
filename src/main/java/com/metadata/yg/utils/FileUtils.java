package com.metadata.yg.utils;

import com.metadata.yg.ExecutorMain;
import com.metadata.yg.constant.Conf;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.lang.management.ManagementFactory;
import java.util.*;

import static com.metadata.yg.constant.Conf.*;
import static com.metadata.yg.utils.DataUtils.radix16To2;

public class FileUtils {

    private static final Logger logger = LoggerFactory.getLogger(FileUtils.class);

    public static List<Map<String,String>> readXml(String loopNode){
        List<Map<String,String>> confList = new ArrayList<>();
        SAXReader saxReader=new SAXReader();
        try {
            Document document= saxReader.read(new File(Conf.EXECUTORFILE));
            Element root=document.getRootElement();
            Iterator it=root.elementIterator(loopNode);
            while (it.hasNext()){
                Map<String,String> classTable = new HashMap<>();
                Element element= (Element) it.next();
                classTable.put("class",element.elementText("class"));
                classTable.put("table",element.elementText("table"));
                classTable.put("sql",element.elementText("sql"));
                confList.add(classTable);
            }
            return confList;
        } catch (DocumentException e) {
            logger.error(e.getMessage());
        }
        return null;
    }


    public static File createCSVFile(List<List> exportData, String fileName) {
        FileOutputStream outSTr;
        File csvFile=null;
        try {
            //定义文件名格式并创建
            csvFile = new File(fileName+"."+DateUtils.getYesterDay()+"."+SUFFIX);
            outSTr = new FileOutputStream(csvFile);
            BufferedOutputStream Buff = new BufferedOutputStream(outSTr);

            for (List row : exportData) {
                for (Iterator propertyIterator = row.iterator(); propertyIterator.hasNext();) {
                    Buff.write(propertyIterator.next().toString().getBytes());
                    if (propertyIterator.hasNext()) {
                        Buff.write(radix16To2((String) FileUtils.getConfig().get(COLUMN)));
                    }
                }
                Buff.write(radix16To2((String) FileUtils.getConfig().get(ROW)));
            }
            Buff.flush();
            Buff.close();
        } catch (FileNotFoundException e) {
            logger.error("文件创建失败");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        return csvFile;
    }

    public static Properties getConfig(){
        Properties props = new Properties();
        try {
            FileInputStream in = new FileInputStream(CONFIGPATH);
            props.load(in);
            in.close();
        } catch (FileNotFoundException e) {
            logger.error("config.properties不存在");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        return props;
    }

    /**
     * 创建文件输出目录
     */
    public static void makeOutPath(){
        File outpath = new File(Conf.OUTPATH);
        if(!outpath.exists()){
            outpath.mkdirs();
            logger.info("文件输出目录已创建："+Conf.OUTPATH);
        }
        logger.info("文件输出目录："+Conf.OUTPATH);
    }

    /**
     * 获取程序执行的PID
     * @return pid
     */
    public  static String getPID(){
        return ManagementFactory.getRuntimeMXBean().getName().split("@")[0];
    }

    /**
     * 获取程序执行的绝对路径目录
     * @return 目录
     */
    public static String getProjectPath() {
        java.net.URL url = ExecutorMain.class .getProtectionDomain().getCodeSource().getLocation();
        String filePath = null ;
        try {
            filePath = java.net.URLDecoder.decode (url.getPath(), "utf-8");
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        if (filePath!=null && filePath.endsWith(".jar"))
            filePath = filePath.substring(0, filePath.lastIndexOf("/"));
        return new File(filePath).getAbsolutePath();
    }
}
