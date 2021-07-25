package com.qa.util;

import java.sql.*;

public class DBUtil{

    static Connection connection = null;
    static PreparedStatement preparedStatement = null;
    static ResultSet resultSet = null;


    public static Connection getConnection(String jdbcName,String url,String user,String pass) {
        try {
            Class.forName(jdbcName);
            connection = (Connection)DriverManager.getConnection(url, user, pass);
        } catch (Exception e) {
            System.out.println("加载驱动失败!");
            e.printStackTrace();
        }
        return connection;
    }


    public static void close(Connection connection, PreparedStatement preparedStatement, ResultSet resultSet) {
        if(resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(preparedStatement != null) {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}