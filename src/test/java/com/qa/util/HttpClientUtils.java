package com.qa.util;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HttpClientUtils {
    //get方法
    public  CloseableHttpResponse  get(String url) throws ClientProtocolException, IOException{
        //创建一个可关闭的httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        //创建一个httpget对象
        HttpGet httpGet = new HttpGet(url);
        //执行请求
        CloseableHttpResponse closeableHttpResponse = httpClient.execute(httpGet);
        return  closeableHttpResponse;
    }

    //设置头信息的get方法
    public CloseableHttpResponse  get(String url, HashMap<String,String> headerMap) throws ClientProtocolException, IOException{

        //创建一个可关闭的httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        //创建一个httpget对象
        HttpGet httpGet = new HttpGet(url);
        //设置头信息
        for(Map.Entry<String,String> entry : headerMap.entrySet()){
            httpGet.setHeader(entry.getKey(),entry.getValue());
        }
        //执行请求
        CloseableHttpResponse closeableHttpResponse = httpClient.execute(httpGet);
        return  closeableHttpResponse;
    }

    //post方法
    public  CloseableHttpResponse post(String url,HashMap<String,String> headerMap,StringEntity stringEntity) throws ClientProtocolException,IOException{
        //创建一个可关闭的httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        //创建一个httppost对象
        HttpPost httpPost = new HttpPost(url);
        //设置头信息
        for(Map.Entry<String,String> entry : headerMap.entrySet()){
            httpPost.setHeader(entry.getKey(),entry.getValue());
        }
        //设置entity
        httpPost.setEntity(stringEntity);
        //执行请求
        CloseableHttpResponse closeableHttpResponse = httpClient.execute(httpPost);
        return closeableHttpResponse;
    }
    //获取响应数据
    public JSONObject getResponse(CloseableHttpResponse closeableHttpResponse)throws IOException{
        //获取响应结果
        HttpEntity resultEntity = closeableHttpResponse.getEntity();
        //将entity转化为String
        String  result = new String (EntityUtils.toString(resultEntity,"UTF-8"));
        //将String转化为JSONObject
        JSONObject resultJsonObject = JSONObject.parseObject(result);
        return  resultJsonObject;
    }


}
