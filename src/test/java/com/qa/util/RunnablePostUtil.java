package com.qa.util;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.entity.StringEntity;

import java.util.HashMap;
import java.util.concurrent.CountDownLatch;

public class RunnablePostUtil implements Runnable{
    HttpClientUtils httpClientUtils = new HttpClientUtils();
    private String url;
    private HashMap<String,String> headerMap;
    private StringEntity stringEntity;
    private CountDownLatch countDownLatch;

    public RunnablePostUtil(String url,HashMap<String,String> headerMap,StringEntity stringEntity,CountDownLatch countDownLatch){
        this.url= url;
        this.headerMap= headerMap;
        this.stringEntity = stringEntity;
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        try{
            httpClientUtils.post(url,headerMap,stringEntity);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            countDownLatch.countDown();
        }
    }
}
