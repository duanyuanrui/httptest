package com.qa.test.yongyou;

import com.alibaba.fastjson.JSONObject;
import com.qa.base.TestBase;
import com.qa.util.HttpClientUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.entity.StringEntity;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.HashMap;

public class DelProPertyFileTest extends TestBase {
    HttpClientUtils httpClientUtils = new HttpClientUtils();
     @BeforeClass
    public  void setUp(){
     }

     @Test(dataProvider = "delProPertyFileTestData")
    public  void delProPertyFileTest(String code,String data,String msg,int statusCode,int httpCode) throws IOException {
         String url = HOST+"/yfzc/deletezcfile";
         //设置头信息
         HashMap<String,String> headerMap = new HashMap<>();
         headerMap.put("Content-Type", "application/json");
        //设置entity
         JSONObject jsonObject = new JSONObject();
         jsonObject.put("code",code);
         jsonObject.put("data",data);
         jsonObject.put("msg",msg);
         StringEntity stringEntity = new StringEntity(jsonObject.toString());

         CloseableHttpResponse closeableHttpResponse = httpClientUtils.post(url,headerMap, stringEntity);
         //验证httpcode
         int httpcode = closeableHttpResponse.getStatusLine().getStatusCode();
         Assert.assertEquals(httpCode,httpcode);
        //验证业务数据
         JSONObject resultJsonObject = httpClientUtils.getResponse(closeableHttpResponse);
         int statucode = Integer.parseInt(resultJsonObject.get("code").toString());
         Assert.assertEquals(statucode,statusCode);
     }

     @DataProvider(name = "delProPertyFileTestData")
    public Object[][] delProPertyFileTestData(){
         return  new Object[][]{
                 {"0","{}","删除成功",0,200}
         };


     }
}
