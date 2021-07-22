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

public class GetPropertyFileListTest extends TestBase {
    HttpClientUtils httpClientUtils = new HttpClientUtils();

    @BeforeClass
    public void setUp(){
        HOST=prop.getProperty("HOST");
    }

    @Test(dataProvider = "getPropertyFileListTestData")
    public void getPropertyFileListTest(int page,int pagesize,int statuscode,int httpcode) throws IOException {
        String url = HOST+"/yfzc/getfilelist";
        //设置头信息
        HashMap<String,String> headerMap = new HashMap<>();
        headerMap.put("Content-Type", "application/json");
        //设置entity
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("page",page);
        jsonObject.put("pagesize",pagesize);
        StringEntity stringEntity = new StringEntity(jsonObject.toString());

        CloseableHttpResponse closeableHttpResponse = httpClientUtils.post(url,headerMap,stringEntity);
        //验证httpcode
        int httpCode = closeableHttpResponse.getStatusLine().getStatusCode();
        Assert.assertEquals(httpCode,httpcode);

        //验证业务数据
        JSONObject resultJsonObject = httpClientUtils.getResponse(closeableHttpResponse);

        int statusCode = Integer.parseInt(resultJsonObject.getByte("code").toString());
        int pageNo = Integer.parseInt(resultJsonObject.getJSONObject("data").get("page").toString());
        int pageSize = Integer.parseInt(resultJsonObject.getJSONObject("data").get("pageSize").toString());
        int listSize = resultJsonObject.getJSONObject("data").getJSONArray("list").size();

        Assert.assertEquals(statusCode,statuscode);
        Assert.assertEquals(pageNo,page);
        Assert.assertEquals(pageSize,pagesize);
        Assert.assertEquals(listSize,0);


    }

    @DataProvider(name ="getPropertyFileListTestData")
    public Object[][] getPropertyFileListTestData(){
        return  new Object[][]{
                {1,10,0,200}
        };
    }




}
