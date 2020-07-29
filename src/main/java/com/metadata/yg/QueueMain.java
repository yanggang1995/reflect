package com.metadata.yg;

import com.jayway.jsonpath.JsonPath;
import com.metadata.yg.constant.Conf;
import com.metadata.yg.task.QueueExecutor;
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
 * @create: 2019-01-02 19:47
 **/
public class QueueMain {
    private static final Logger logger = LoggerFactory.getLogger(QueueMain.class);

    public static void main(String[] args) {
        System.out.println("PID----："+FileUtils.getPID());
        logger.info("PID----："+FileUtils.getPID());
        System.out.println(FileUtils.getProjectPath());
        FileUtils.makeOutPath();
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
            new QueueExecutor().executor(mt,tableNums);
        } catch (Exception e) {
            e.printStackTrace();
        }
        deleteNullFile(Conf.OUTPATH);
        logger.info("生成结束");
        System.exit(0);
    }
}
