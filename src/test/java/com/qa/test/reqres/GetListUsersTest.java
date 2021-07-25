package com.qa.test.reqres;

import com.alibaba.fastjson.JSONObject;
import com.qa.base.TestBase;
import com.qa.util.HttpClientUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.IOException;
import java.lang.reflect.Method;

public class GetListUsersTest extends TestBase {
    public HttpClientUtils httpClientUtils = new HttpClientUtils();
    public String url = HOST+"/api/users?page=2";

    @BeforeClass
    public void setUp(){

    }
    @BeforeMethod
    public void testStart(Method method) {
        System.out.println(">>>>>>>>>>>>>>>>Test case: "
                + method.getName()+"  start run>>>>>>>>>>>>>>>>");
    }

    @Test(dataProvider = "dp", dataProviderClass = com.qa.util.Dataprovider.class,invocationCount = 20,threadPoolSize=2)
    public void getListUsersTest(String  expectedHttpcode,String pageNum) throws IOException {
        System.out.println(url);
        CloseableHttpResponse closeableHttpResponse = httpClientUtils.get(url);
        //验证httpcode
        int httpCode = closeableHttpResponse.getStatusLine().getStatusCode();
        Assert.assertEquals(httpCode,Integer.parseInt(expectedHttpcode));

        //验证业务数据
        JSONObject resultJsonObject = httpClientUtils.getResponse(closeableHttpResponse);
        Assert.assertEquals(resultJsonObject.get("page").toString(),pageNum);

    }

    @BeforeMethod
    public void testStop(Method method) {
        System.out.println(">>>>>>>>>>>>>>>>Test case: "
                + method.getName()+" stop >>>>>>>>>>>>>>>>");
    }

    @AfterClass
    public void clean(){

    }

    @DataProvider(name = "getListUsersTestData")
    public Object[][] getListUsersTestData(){
        return new Object[][]{
                {200,2}
        };
    }

}
