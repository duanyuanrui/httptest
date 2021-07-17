package com.qa.test;

import com.alibaba.fastjson.JSONObject;
import com.qa.base.TestBase;
import com.qa.util.RestClient;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;

public class PostApiTest extends TestBase {
    TestBase testBase;
    String host;
    RestClient restClient;
    CloseableHttpResponse closeableHttpResponse;

    @BeforeClass
    public void setup(){
        testBase = new TestBase();
        host = prop.getProperty("HOST");
    }

    @Test(dataProvider = "post-provider")
    public void postAPITest( String email,String password,int errorCode) throws ClientProtocolException, IOException {
        String paramrl="/api/login";  //接口地址
        String url=host+paramrl;    //拼接地址
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("email",email);
        jsonObject.put("password",password);
        restClient = new RestClient();
        closeableHttpResponse = restClient.post(url,jsonObject);
        //assert
        int statusCode = closeableHttpResponse.getStatusLine().getStatusCode();
        System.out.println("statusCode="+statusCode);
        Assert.assertEquals(statusCode,errorCode,"response status code is not 200");
    }

    @DataProvider(name = "post-provider")
    public Object[][] postApi(){
        return new Object[][]{
                {"eve.holt@reqres.in","cityslicka",200},
                {"eve.holt@163.com","cityslicka",400}
        };
    }

}
