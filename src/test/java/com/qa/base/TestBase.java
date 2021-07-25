package com.qa.base;

import com.qa.util.DBUtil;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.util.Properties;

public class TestBase {
    public static Properties prop;
    public static String HOST;
    public static DBUtil dbUtil;
    public static String DB_DRIVER;
    public static String DB_URL;
    public static String DB_USER;
    public static String DB_PASS;
    static {
        try{

            File file = new File(Thread.currentThread().getContextClassLoader().getResource("config.properties").getFile());            //读取配置文件
            //File file = new File("src/test/resources/config.properties");
            prop = new Properties();
            FileInputStream fis = new FileInputStream(file);
            prop.load(fis);

            //获取主站地址
            HOST = prop.getProperty("HOST");

            //读取数据库信息
            DB_DRIVER = prop.getProperty("DB_DRIVER");
            DB_URL = prop.getProperty("DB_URL");
            DB_USER = prop.getProperty("DB_USER");
            DB_PASS = prop.getProperty("DB_PASS");
            dbUtil = new DBUtil();
            //连接数据库
            Connection connection = dbUtil.getConnection(DB_DRIVER,DB_URL,DB_USER,DB_PASS);

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}