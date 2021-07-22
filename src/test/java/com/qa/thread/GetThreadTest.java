package com.qa.thread;

import com.qa.base.TestBase;
import com.qa.util.RunnableGetUtil;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GetThreadTest extends TestBase {


    @Test(dataProvider = "data")
    public void fun(String pageNo){
        String url = HOST +"/api/users?page="+pageNo;
        //设置头信息
        HashMap<String,String> headerMap = new HashMap<>();
        headerMap.put("Content-Type", "application/json");

        long begintime = System.currentTimeMillis();//系统开始时间
        ExecutorService pool = Executors.newCachedThreadPool();
        final int count = 50;
        CountDownLatch countDownLatch = new CountDownLatch(count);
        for(int i=0;i<count;i++){
            RunnableGetUtil runnableGetUtil = new RunnableGetUtil(url,headerMap,countDownLatch);
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
