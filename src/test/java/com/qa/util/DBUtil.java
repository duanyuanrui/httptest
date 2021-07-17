package com.qa.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBUtil {

    /*
    连接数据库
     */
    public Connection getCon(String jdbcName,String url,String user,String pass) throws Exception{
        Class.forName(jdbcName);
        Connection conn = DriverManager.getConnection(url,user,pass);
        return conn;
    }
    /*
    关闭数据库
     */
    public void closeCon(Connection connection) throws Exception{
        if(connection!=null){
            connection.close();
        }
    }
}