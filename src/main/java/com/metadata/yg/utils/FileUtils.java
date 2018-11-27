package com.metadata.yg.utils;

import com.metadata.yg.constant.Conf;
import com.metadata.yg.inf.MetadataExecutor;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.*;
import java.util.*;

import static com.metadata.yg.constant.Conf.*;

public class FileUtils {


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
            System.out.println(e.getMessage());
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
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
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

    public static MetadataExecutor getExecutor(String className){
        try {
            Class<?> clazz = Class.forName(className);
            MetadataExecutor executor= (MetadataExecutor) clazz.newInstance();
            return executor;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
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
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return props;
    }
}
