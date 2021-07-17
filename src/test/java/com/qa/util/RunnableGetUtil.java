package com.qa.util;

import java.util.concurrent.CountDownLatch;

public class RunnableGetUtil implements  Runnable{
    private String url;
    private CountDownLatch countDownLatch;
    RestClient restClient = new RestClient();

    public RunnableGetUtil(String pageNO, CountDownLatch countDownLatch){
        this.url = pageNO;
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        try{
            restClient.get(url);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            countDownLatch.countDown();
        }
    }
}
