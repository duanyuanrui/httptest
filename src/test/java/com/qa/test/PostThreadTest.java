package com.qa.test;

import com.alibaba.fastjson.JSONObject;
import com.google.inject.internal.cglib.core.$ReflectUtils;
import com.qa.base.TestBase;
import com.qa.util.RunnablePostUtil;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.jar.JarEntry;

public class PostThreadTest extends TestBase {
    String host;
    @BeforeClass
    public  void setUp(){
        host = prop.getProperty("HOST");
    }

    @Test(dataProvider = "data")
    public  void fun(String email,String password,int count){

            String paramrl="/api/login";  //接口地址
            String url=host+paramrl;    //拼接地址
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("email",email);
            jsonObject.put("password",password);
        CountDownLatch countDownLatch = new CountDownLatch(count);
        long  beginTime = System.currentTimeMillis();
        ExecutorService pool = Executors.newCachedThreadPool();
        for(int i=0;i<count;i++){
            RunnablePostUtil runnablePostUtil = new RunnablePostUtil(url,jsonObject,countDownLatch);
            pool.execute(runnablePostUtil);
        }
        pool.shutdown();
        try{
            countDownLatch.await();
        }catch (Exception e){
            e.printStackTrace();
        }
        long endTime = System.currentTimeMillis();
        System.out.println(count+"个接口共耗时"+(endTime-beginTime));


    }

    @DataProvider(name = "data")
    public  Object[][] fun(){
        return  new Object[][]{
                {"eve.holt@reqres.in","cityslicka",1}
        };
    }


}
