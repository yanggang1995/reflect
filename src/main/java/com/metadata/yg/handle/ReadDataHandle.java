package com.metadata.yg.handle;

import com.metadata.yg.transform.MetadataTransform;
import com.metadata.yg.utils.C3P0Utils;
import com.metadata.yg.utils.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.metadata.yg.utils.ObjectUtils.getTransform;

/**
 * @author: Y.G
 * @description:
 * @create: 2018-11-30 14:55
 **/
public class ReadDataHandle {
    private static final Logger logger  = LoggerFactory.getLogger(ReadDataHandle.class);
    private C3P0Utils c3P0Utils= new C3P0Utils();
    private MetadataTransform metadataTransform;
    private String[]  outputTable;
    private String sourceSql;

    public ReadDataHandle(String className,String outTables,String sourceSql) throws SQLException {
        this.metadataTransform = getTransform(className);
        this.outputTable = outTables.split("/");
        this.sourceSql = sourceSql;
    }

    public Integer getSqlCount() throws SQLException {
        ResultSet rs = c3P0Utils.getResultSet("select count(1) count from (" + sourceSql + ")a0");
        rs.next();
        Integer count = rs.getInt(1);
        if(count==0){
            logger.info("数据源无数据");
            StopWatch.exitApplication();
        }
        return rs.getInt(1);
    }

    public List<String> mysqlLooper() throws SQLException {
        List<String>  oneSqls = new ArrayList<>();
        for(int i=0;i<=getSqlCount()%10000;i++){
            oneSqls.add(sourceSql+" limit "+i*10000+","+10000);
        }
        return oneSqls;
    }


    public void die(){
        c3P0Utils.closeConn();
    }
}
