package com.qa.test.yongyou;

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

public class AddPropertyFileTest extends TestBase {
    HttpClientUtils httpClientUtils;

    @BeforeClass
    public  void setUp(){
        //SQL造数据

    }
    @BeforeMethod
    public void testStart(Method method) {
        System.out.println(">>>>>>>>>>>>>>>>Test case: "
                + method.getName()+">>>>>>>>>>>>>>>> start run");
    }

    @Test(dataProvider = "addropertyFileTestData")
    public void  addropertyFileTest(String name,String description,int expectedHttpcode) throws IOException {
        String url = HOST+"/yfzc/addzcfile";
        httpClientUtils = new HttpClientUtils();
        //头信息
        HashMap<String,String> headerMap = new HashMap<>();
        headerMap.put("Content-Type", "application/json");
        //组装entity
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name",name);
        jsonObject.put("description",description);
        StringEntity stringEntity = new StringEntity(jsonObject.toString());

        CloseableHttpResponse response = httpClientUtils.post(url,headerMap,stringEntity);
        //验证httpcode
        int actualHttpCode = response.getStatusLine().getStatusCode();
        Assert.assertEquals(actualHttpCode,expectedHttpcode);

        //验证业务数据
        JSONObject resultJsonObject = httpClientUtils.getResponse(response);
        int statusCode = Integer.parseInt(resultJsonObject.get("code").toString());
        Assert.assertEquals(statusCode,0);

    }
    @AfterClass
    public void clear(){
        //SQL清除数据
    }

    @DataProvider (name = "addropertyFileTestData")
    public Object[][] addropertyFileTestData(){
        return new Object[][]{
                {"lily","good girl",200},
                {"david","good boy",200},
                {"kitty","black cat",200}
        };
    }

}
