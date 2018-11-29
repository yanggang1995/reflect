package com.metadata.yg.utils;

import com.metadata.yg.constant.Conf;
import com.metadata.yg.inf.MetadataExecutor;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.*;

import static com.metadata.yg.constant.Conf.*;

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


    public static byte[] radix16To2(String str) {
        byte[] b = new byte[str.length() / 2];
        for (int i = 0; i < b.length; i++) {
            b[i] = ((byte) Integer.parseInt(str.substring(i * 2, (1 + i) * 2), 16));
        }
        return b;
    }

    public static byte[] radixStr(String str,int radix){
        switch (radix){
            case 8:
                return str.getBytes();
            case 16:
                return radix16To2(str);
            case 10:
                return str.getBytes();
            default:
                logger.info("未知的进制，默认为十进制");
                return str.getBytes();
        }
    }

    public static MetadataExecutor getExecutor(String className){
        try {
            Class<?> clazz = Class.forName(className);
            MetadataExecutor executor= (MetadataExecutor) clazz.newInstance();
            return executor;
        } catch (ClassNotFoundException e) {
            logger.error("配置文件中的插件类不存在");
        } catch (IllegalAccessException e) {
            logger.error(e.getMessage());
        } catch (InstantiationException e) {
            logger.error(e.getMessage());
        }
        return null;
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
}
