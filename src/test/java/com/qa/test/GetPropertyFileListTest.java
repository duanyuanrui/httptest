package com.qa.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.qa.base.TestBase;
import com.qa.util.RestClient;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;

public class GetPropertyFileListTest extends TestBase {
    String host;
    RestClient restClient = new RestClient();

    @BeforeClass
    public void setUp(){
        host=prop.getProperty("HOST");
    }

    @Test(dataProvider = "getPropertyFileListTestData")
    public void getPropertyFileListTest(int page,int pagesize,int statuscode,int httpcode) throws IOException {
        String url = host+"/yfzc/getfilelist";

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("page",page);
        jsonObject.put("pagesize",pagesize);

        CloseableHttpResponse closeableHttpResponse = restClient.post(url,jsonObject);
        int httpCode = closeableHttpResponse.getStatusLine().getStatusCode();
        Assert.assertEquals(httpCode,httpcode);

        HttpEntity httpEntity = closeableHttpResponse.getEntity();
        String result = EntityUtils.toString(httpEntity,"UTF-8").toString();

        JSONObject resultJsonObject = JSONObject.parseObject(result);
        String pretty = JSON.toJSONString(resultJsonObject, SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue,
                SerializerFeature.WriteDateUseDateFormat);
        System.out.println(pretty);
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
