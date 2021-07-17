package com.qa.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.qa.base.TestBase;
import com.qa.util.RestClient;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.logging.Logger;

public class DelProPertyFileTest extends TestBase {
    String host;
    RestClient restClient = new RestClient();
     @BeforeClass
    public  void setUp(){
         host = prop.getProperty("HOST");
     }

     @Test(dataProvider = "delProPertyFileTestData")
    public  void delProPertyFileTest(String code,String data,String msg,int statusCode,int httpCode) throws IOException {
         String url = host+"/yfzc/deletezcfile";

         JSONObject jsonObject = new JSONObject();
         jsonObject.put("code",code);
         jsonObject.put("data",data);
         jsonObject.put("msg",msg);

         CloseableHttpResponse closeableHttpResponse = restClient.post(url, jsonObject);
         int httpcode = closeableHttpResponse.getStatusLine().getStatusCode();
         Assert.assertEquals(httpCode,httpcode);

         HttpEntity httpEntity = closeableHttpResponse.getEntity();
         String result = new String(EntityUtils.toString(httpEntity,"UTF-8"));

         JSONObject resultJsonObject = JSONObject.parseObject(result);
         String pretty = JSON.toJSONString(resultJsonObject, SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue,
                 SerializerFeature.WriteDateUseDateFormat);
         System.out.println(pretty);
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
