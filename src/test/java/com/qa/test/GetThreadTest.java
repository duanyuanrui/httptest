package com.qa.test;

import com.qa.base.TestBase;
import com.qa.util.RunnableGetUtil;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GetThreadTest extends TestBase {
    String host;
    @BeforeClass
    public void setup(){
        host = prop.getProperty("HOST"); //获取host地址
    }

    @Test(dataProvider = "data")
    public void fun(String pageNo){
        String url = host+"/api/users?page="+pageNo;
        long begintime = System.currentTimeMillis();//系统开始时间
        ExecutorService pool = Executors.newCachedThreadPool();
        final int count = 50;
        CountDownLatch countDownLatch = new CountDownLatch(count);
        for(int i=0;i<count;i++){
            RunnableGetUtil runnableGetUtil = new RunnableGetUtil(url,countDownLatch);
            pool.execute(runnableGetUtil);
            System.out.println("test"+i);
        }
        pool.shutdown();
        try{
            countDownLatch.await();
        }catch (Exception e){
            e.printStackTrace();
        }
        long endTime = System.currentTimeMillis();
        System.out.println(count+"个接口共耗时"+(endTime-begintime));

    }

    @DataProvider(name = "data")
    public Object[][] getData(){
        return new Object[][]{
                {"2"}
        };
    }

}
