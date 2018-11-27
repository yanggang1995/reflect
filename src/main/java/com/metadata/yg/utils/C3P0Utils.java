package com.metadata.yg.utils;

import com.mchange.v2.c3p0.ComboPooledDataSource;

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
    private static ComboPooledDataSource dataSource=new ComboPooledDataSource();
    private  static Connection conn = null;
    private  static Statement st = null;
    private  static ResultSet rs = null;

    public static DataSource getDataSource(){
        try{
            dataSource.setDriverClass(DRIVER);
            dataSource.setJdbcUrl(URL);
            dataSource.setUser(USER);
            dataSource.setPassword(PASSWORD);
            return dataSource;
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Connection getConnection(){
        try {
            return getDataSource().getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static ResultSet getResultSet(String sql) {
        conn = getConnection();
        try {
            st = conn.createStatement();
            System.out.println("连接创建成功");
            rs = st.executeQuery(sql);
            return rs;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void closeConn(){
        try {
            if(rs != null)
                rs.close();
            if(st != null)
                st.close();
            if(conn!=null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
