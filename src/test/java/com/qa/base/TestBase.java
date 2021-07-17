package com.qa.base;

import com.qa.util.DBUtil;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class TestBase {
    public Properties prop;
    public String DB_DRIVER;
    public String DB_URL;
    public String DB_USER;
    public String DB_PASS;
    public DBUtil dbUtil;

    //构造函数
    public TestBase(){
        try {
            prop = new Properties();
            System.out.println("user.dir==="+System.getProperty("user.dir"));
            FileInputStream fis = new FileInputStream("/Users/wangge/IdeaProjects/watermelonPlan/src/test/resources/config.properties");
            prop.load(fis);

            DB_DRIVER = prop.getProperty("DB_DRIVER");
            DB_URL = prop.getProperty("DB_URL");
            DB_USER = prop.getProperty("DB_USER");
            DB_PASS = prop.getProperty("DB_PASS");

            dbUtil = new DBUtil();
            dbUtil.getCon(DB_DRIVER,DB_URL,DB_USER,DB_PASS);

        }catch (FileNotFoundException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}