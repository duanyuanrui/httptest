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
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import javax.swing.text.html.parser.Entity;
import java.io.IOException;

public class AddPropertyFileTest extends TestBase {
    String host;
    RestClient restClient;

    @BeforeClass
    public  void setUp(){
        host = prop.getProperty("HOST"); //获取host地址

        //SQL造数据

    }

    @Test(dataProvider = "addropertyFileTestData")
    public void  addropertyFileTest(String name,String description,int httpcode) throws IOException {
        String url = host+"/yfzc/addzcfile";
        restClient = new RestClient();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name",name);
        jsonObject.put("description",description);
        CloseableHttpResponse response = restClient.post(url,jsonObject);
        int httpCode = response.getStatusLine().getStatusCode();
        Assert.assertEquals(httpCode,httpcode);

        //获取响应结果
        HttpEntity resultEntity = response.getEntity();
        String  result = new String (EntityUtils.toString(resultEntity,"UTF-8"));
        //获取code
        JSONObject resultJsonObject = JSONObject.parseObject(result);
        String pretty = JSON.toJSONString(resultJsonObject, SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue,
                SerializerFeature.WriteDateUseDateFormat);
        System.out.println(pretty);
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
