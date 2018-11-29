package com.metadata.yg.utils;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static com.metadata.yg.constant.Conf.*;

/**
 * @author: Y.G
 * @description:
 * @create: 2018-11-27 14:36
 **/
public class C3P0Utils {
    private static final Logger logger = LoggerFactory.getLogger(C3P0Utils.class);
    private  ComboPooledDataSource dataSource=new ComboPooledDataSource();
    private  Connection conn = null;
    private  Statement st = null;
    private  ResultSet rs = null;

    public  DataSource getDataSource(){
        try{
            this.dataSource.setDriverClass(DRIVER);
            this.dataSource.setJdbcUrl(URL);
            this.dataSource.setUser(USER);
            this.dataSource.setPassword(PASSWORD);
            return this.dataSource;
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        }
        return null;
    }

    public  Connection getConnection(){
        try {
            return getDataSource().getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public  ResultSet getResultSet(String sql) {
        this.conn = getConnection();
        try {
            this.st = conn.createStatement();
            logger.info("C3p0连接创建成功");
            this.rs = this.st.executeQuery(sql);
            return this.rs;
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return null;
    }

    public void closeConn(){
        try {
            if(rs != null)
                rs.close();
            if(st != null)
                st.close();
            if(conn!=null) {
                conn.close();
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }

    }
}
