package com.metadata.yg.utils;

import com.metadata.yg.constant.Conf;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.*;
import java.util.Iterator;
import java.util.List;

public class FileUtils {


    public static String readXml(String loopNode,String needNodeName){
        SAXReader saxReader=new SAXReader();
        try {
            Document document= saxReader.read(new File(Conf.EXECUTORFIL));
            Element root=document.getRootElement();
            Iterator it=root.elementIterator(loopNode);
            while (it.hasNext()){
                Element element= (Element) it.next();
                return element.elementText(needNodeName);
            }
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
            csvFile = new File(fileName+"."+DateUtils.getDate()+".csv");
            outSTr = new FileOutputStream(csvFile);
            BufferedOutputStream Buff = new BufferedOutputStream(outSTr);

            for (List row : exportData) {
                for (Iterator propertyIterator = row.iterator(); propertyIterator.hasNext();) {
                    Buff.write(propertyIterator.next().toString().getBytes());
                    if (propertyIterator.hasNext()) {
                        Buff.write(radix16To2("33ff"));
                    }
                }
                Buff.write(radix16To2("03fe0d0a"));
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


    private static byte[] radix16To2(String str) {
        byte[] b = new byte[str.length() / 2];
        for (int i = 0; i < b.length; i++) {
            b[i] = ((byte) Integer.parseInt(str.substring(i * 2, (1 + i) * 2), 16));
        }
        return b;
    }
}
