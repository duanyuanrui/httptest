package com.qa.test.reqres;

import com.alibaba.fastjson.JSONObject;
import com.qa.base.TestBase;
import com.qa.util.HttpClientUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.entity.StringEntity;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;

public class LoginTest extends TestBase {

    public HttpClientUtils httpClientUtils = new HttpClientUtils();
    public String url= HOST+"/api/login";
    ;

    @BeforeClass
    public void setUp(){

    }

    @BeforeMethod
    public void testStart(Method method) {
        System.out.println(">>>>>>>>>>>>>>>>Test case: "
                + method.getName()+" start run>>>>>>>>>>>>>>>>");
    }
    @Test(dataProvider = "dp", dataProviderClass = com.qa.util.Dataprovider.class)
    public void loginTest(String email,String password,String expectedHttpcode,String expectedToken) throws IOException {
        //头信息
        HashMap<String,String> headerMap = new HashMap<>();
        headerMap.put("Content-Type", "application/json");
        //组装entity
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("email",email);
        jsonObject.put("password",password);
        StringEntity stringEntity = new StringEntity(jsonObject.toString());

        CloseableHttpResponse closeableHttpResponse = httpClientUtils.post(url,headerMap,stringEntity);
        if(closeableHttpResponse == null){
            System.out.println("null");
        }
        //验证httpcode
        int httpCode = closeableHttpResponse.getStatusLine().getStatusCode();
        Assert.assertEquals(httpCode,Integer.parseInt(expectedHttpcode));

        //验证业务数据
        String token = httpClientUtils.getResponse(closeableHttpResponse).get("token").toString();
        Assert.assertEquals(token,expectedToken);
    }
    @BeforeMethod
    public void testStop(Method method) {
        System.out.println(">>>>>>>>>>>>>>>>Test case: "
                + method.getName()+" stop >>>>>>>>>>>>>>>>");
    }

    @AfterClass
    public void clean(){

    }

    @DataProvider(name = "LoginTestData")
    public Object[][] LoginTestData(){
        return new Object[][]{
                {"eve.holt@reqres.in","cityslicka",200,"QpwL5tke4Pnpja7X4"}
        };
    }
}
