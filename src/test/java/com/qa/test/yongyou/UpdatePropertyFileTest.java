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

public class UpdatePropertyFileTest extends TestBase {
    HttpClientUtils httpClientUtils;
    @BeforeClass
    public void setUp(){
        HOST = prop.getProperty("HOST"); //获取host地址
    }

    @Test(dataProvider = "updatePropertyFileTestData")
    public void updatePropertyFileTest(String id ,String name,String description,int statusCode ,int httpcode) throws IOException {
        String url = HOST+"/yfzc/updatezcfile";
        httpClientUtils = new HttpClientUtils();
        //设置头信息
        HashMap<String,String> headerMap = new HashMap<>();
        headerMap.put("Content-Type", "application/json");
        //设置entity
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id",id);
        jsonObject.put("name",name);
        jsonObject.put("description",description);
        StringEntity stringEntity = new StringEntity(jsonObject.toString());
        CloseableHttpResponse closeableHttpResponse = httpClientUtils.post(url,headerMap,stringEntity);
        //验证httpcode
        int httpCode = closeableHttpResponse.getStatusLine().getStatusCode();
        Assert.assertEquals(httpCode,httpcode);
        //验证业务数据
        JSONObject resultJsonObject = httpClientUtils.getResponse(closeableHttpResponse);
        int statuscode = Integer.parseInt(resultJsonObject.get("code").toString());
        Assert.assertEquals(statuscode,statusCode);
    }

    @DataProvider(name = "updatePropertyFileTestData")
    public Object[][] updatePropertyFileTestData(){
       return  new Object[][]{
               {"1","调研报告","规划阶段文档",0,200},
               {"2","调研报告","规划阶段文档",0,200},
               {"3","调研报告","规划阶段文档",0,200},
       };
    }
}
