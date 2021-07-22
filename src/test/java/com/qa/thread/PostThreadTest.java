package com.qa.thread;

import com.alibaba.fastjson.JSONObject;
import com.google.inject.internal.cglib.core.$ReflectUtils;
import com.qa.base.TestBase;
import com.qa.util.RunnablePostUtil;
import org.apache.http.entity.StringEntity;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.jar.JarEntry;

public class PostThreadTest extends TestBase {


    @Test(dataProvider = "data")
    public  void fun(String email,String password,int count) throws UnsupportedEncodingException {

            String paramrl="/api/login";  //接口地址
            String url= HOST + paramrl;    //拼接地址
        //设置头信息
        HashMap<String,String> headerMap = new HashMap<>();
        headerMap.put("Content-Type", "application/json");
        //设置entity
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("email",email);
            jsonObject.put("password",password);
        StringEntity stringEntity = new StringEntity(jsonObject.toString());

        CountDownLatch countDownLatch = new CountDownLatch(count);
        long  beginTime = System.currentTimeMillis();
        ExecutorService pool = Executors.newCachedThreadPool();
        for(int i=0;i<count;i++){
            RunnablePostUtil runnablePostUtil = new RunnablePostUtil(url,headerMap,stringEntity,countDownLatch);
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
