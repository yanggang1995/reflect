package com.metadata.yg;

import com.jayway.jsonpath.JsonPath;
import com.metadata.yg.constant.Conf;
import com.metadata.yg.task.DataExecutor;
import com.metadata.yg.transform.MetadataTransform;
import com.metadata.yg.transform.impl.BJLTTransform;
import com.metadata.yg.utils.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import static com.metadata.yg.utils.FileUtils.deleteNullFile;

/**
 * @author: Y.G
 * @description:
 * @create: 2018-11-29 16:50
 **/
public class ExecutorMain {
    private static final Logger logger = LoggerFactory.getLogger(ExecutorMain.class);
    /*public static void main(String[] args) {
        System.out.println("PID----："+FileUtils.getPID());
        logger.info("PID----："+FileUtils.getPID());
        System.out.println(FileUtils.getProjectPath());
        FileUtils.makeOutPath();
        for(Map conf:FileUtils.readXml("source_conf")){
            try {
                new DataExecutor((""+conf.get("table")).toLowerCase()).executor(ObjectUtils.getTransform((String) conf.get("class")),10000);
            } catch (Exception e) {
                e.printStackTrace();
            }
            break;
        }
    }*/
//    public static String tables="Userinfo/UserLogonInfo/UserLogoutInfo/Contentviewlog/ProductInfo";
    public static void main(String[] args) {
        System.out.println("PID----:"+FileUtils.getPID());
        logger.info("PID----:"+FileUtils.getPID());
        System.out.println(FileUtils.getProjectPath());
//        FileUtils.makeOutPath();
        Map tableNums=null;
        try {
            tableNums=JsonPath.read(new File(Conf.TABLENUMS),"$");
            logger.info("生成数配置文件加载成功");
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("程序结束 makeData.json 文件读取异常,请检查文件格式或配置路径");
            System.exit(-1);
        }
        MetadataTransform mt=new BJLTTransform();
        try {
            new DataExecutor().executor(mt,tableNums);
        } catch (Exception e) {
            e.printStackTrace();
        }
        deleteNullFile(Conf.OUTPATH);
        logger.info("生成结束");
    }
}
