package com.metadata.yg;

import com.metadata.yg.constant.Conf;
import com.metadata.yg.task.DataExecutor;
import com.metadata.yg.utils.C3P0Utils;
import com.metadata.yg.utils.FileUtils;
import com.metadata.yg.utils.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * @author: Y.G
 * @description:
 * @create: 2018-11-29 16:50
 **/
public class ExecutorMain {
    private static final Logger logger = LoggerFactory.getLogger(ExecutorMain.class);
    public static void main(String[] args) {
        System.out.println("PID----："+FileUtils.getPID());
        logger.info("PID----："+FileUtils.getPID());
        System.out.println(FileUtils.getProjectPath());
        FileUtils.makeOutPath();
        for(Map conf:FileUtils.readXml("source_conf")){
            try {
                C3P0Utils c3P0Utils=new C3P0Utils();
                new DataExecutor((Conf.OUTPATH+"/"+conf.get("table")).toLowerCase()).executor(ObjectUtils.getTransform((String) conf.get("class")),c3P0Utils.getResultSet((String) conf.get("sql")));
                c3P0Utils.closeConn();
            } catch (Exception e) {
                e.printStackTrace();
            }
            break;
        }
    }
}
