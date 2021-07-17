package com.qa.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.qa.base.TestBase;
import com.qa.util.RestClient;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;

public class UpdatePropertyFileTest extends TestBase {
    String host;
    RestClient restClient;
    @BeforeClass
    public void setUp(){
        host = prop.getProperty("HOST"); //获取host地址
    }

    @Test(dataProvider = "updatePropertyFileTestData")
    public void updatePropertyFileTest(String id ,String name,String description,int statusCode ,int httpcode) throws IOException {
        String url = host+"/yfzc/updatezcfile";
        restClient = new RestClient();

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id",id);
        jsonObject.put("name",name);
        jsonObject.put("description",description);
        HttpResponse response = restClient.post(url,jsonObject);

        int httpCode = response.getStatusLine().getStatusCode();
        Assert.assertEquals(httpCode,httpcode);

        HttpEntity httpEntity = response.getEntity();
        //entity-->String
        String resultString = EntityUtils.toString(httpEntity,"UTF-8");
        //String --->JSONObject
        JSONObject resultJsonObject = JSONObject.parseObject(resultString);
        String pretty = JSON.toJSONString(resultJsonObject, SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue,
                SerializerFeature.WriteDateUseDateFormat);
        System.out.println(pretty);
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
