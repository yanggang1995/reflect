package com.metadata.yg.utils;

import com.metadata.yg.inf.CallBack;
import com.metadata.yg.inf.MetadataExecutor;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.List;

public class TBuilderRoomSqlFileTool {

    public static BufferedWriter initDataWrite(File dataFile) throws Exception {
        if (!dataFile.exists()) {
            if (!dataFile.createNewFile()) {
                System.err.println("创建文件失败，已存在：" + dataFile.getAbsolutePath());
            }
        }
        return new BufferedWriter(new OutputStreamWriter(new FileOutputStream(dataFile, true), "UTF-8"));
    }

    //读一行数据
    public static void loadDB(CallBack<Void> callBack, List<String> rs) throws Exception {
        int num = 0;
        /*InputStreamReader isr = new InputStreamReader(new FileInputStream(new File("data/testTxt.txt")), "utf-8");
        BufferedReader br = new BufferedReader(isr);
        String lineTxt;
        while ((lineTxt = br.readLine()) != null) {
            num++;
            callBack.call(num,lineTxt);
        }*/
        Class<?> clazz = Class.forName(FileUtils.readXml("source_conf","class_name"));
        MetadataExecutor executor= (MetadataExecutor) clazz.newInstance();
        for(String lineTxt:rs){
            num++;
            callBack.call(num,executor.getFormatRow(lineTxt));
        }
    }

    public static void writeLog(String str, Object... values) {
        System.out.println(str);
    }
}
