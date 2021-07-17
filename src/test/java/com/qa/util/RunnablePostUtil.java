package com.qa.util;

import com.alibaba.fastjson.JSONObject;

import java.util.concurrent.CountDownLatch;

public class RunnablePostUtil implements Runnable{
    RestClient restClient = new RestClient();
    private String url;
    private JSONObject jsonObject;
    private CountDownLatch countDownLatch;

    public RunnablePostUtil(String url,JSONObject jsonObject,CountDownLatch countDownLatch){
        this.url= url;
        this.jsonObject = jsonObject;
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        try{
            restClient.post(url,jsonObject);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            countDownLatch.countDown();
        }
    }
}
