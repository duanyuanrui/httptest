package com.qa.util;

import java.util.HashMap;
import java.util.concurrent.CountDownLatch;

public class RunnableGetUtil implements  Runnable{
    private String url;
    private HashMap<String,String> headerMap;
    private CountDownLatch countDownLatch;
    HttpClientUtils httpClientUtils = new HttpClientUtils();

    public RunnableGetUtil(String url, HashMap<String,String> headerMap,CountDownLatch countDownLatch){
        this.url= url;
        this.headerMap= headerMap;
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        try{
            httpClientUtils.get(url);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            countDownLatch.countDown();
        }
    }
}
