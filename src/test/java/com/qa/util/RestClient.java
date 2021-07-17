package com.qa.util;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import java.io.IOException;

public class RestClient {
    public  CloseableHttpResponse  get(String url) throws ClientProtocolException, IOException{
        //创建一个可关闭的httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        //创建一个httpget对象
        HttpGet httpGet = new HttpGet(url);
        //执行请求
        CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
        return  httpResponse;
    }

    public CloseableHttpResponse post(String url,JSONObject jsonObject) throws ClientProtocolException,IOException{
        //创建一个可关闭的httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        //创建一个httppost对象
        HttpPost httpPost = new HttpPost(url);
        //设置头信息  application/x-www-form-urlencoded
        httpPost.setHeader("content-type","application/json");
        //把json放在entity中
        StringEntity entity = new StringEntity(jsonObject.toString(),"utf-8");
        //
        httpPost.setEntity(entity);
        //执行请求
        CloseableHttpResponse httpResponse = httpClient.execute(httpPost);
        return httpResponse;
    }
}
