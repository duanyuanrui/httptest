package com.qa.test;

import com.qa.base.TestBase;
import com.qa.util.DBUtil;
import com.qa.util.RestClient;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.IOException;
import java.sql.Connection;

public class GetApiTest extends TestBase{
    String host;

    RestClient restClient;
    CloseableHttpResponse closeableHttpResponse;
    Connection connection;
    DBUtil dbUtil= new DBUtil();

    @BeforeClass
    public  void before_class() throws Exception{
        host = prop.getProperty("HOST"); //获取host地址
        /*连接数据库
        DB_DRIVER = prop.getProperty("DB_DRIVER");
        DB_URL = prop.getProperty("DB_URL");
        DB_USER = prop.getProperty("DB_USER");
        DB_PASS = prop.getProperty("DB_PASS");
        connection = dbUtil.getCon(DB_DRIVER,DB_URL,DB_USER,DB_PASS);*/

    }

    @AfterClass
    public void after_class() throws Exception{
        dbUtil.closeCon(connection);
    }

    @Test(dataProvider = "getapi-provider")
    public  void getAPITest(String pageNo,int errorCode) throws ClientProtocolException, IOException{
        String paramrl="/api/users";
        String url = host + paramrl+"?page="+pageNo;
        restClient = new RestClient();
        closeableHttpResponse = restClient.get(url);
        //断言
        int statusCode = closeableHttpResponse.getStatusLine().getStatusCode();
        Assert.assertEquals(statusCode,errorCode,"response status code is not 200");

//        String responseString = EntityUtils.toString(closeableHttpResponse.getEntity(),"UTF-8");
//        JSONObject responseJson = JSON.parseObject(responseString);
//        String s = TestUtil.getValBYJPath(responseJson,"data[0]/first_name");
//        System.out.println(s);
    }

    @DataProvider(name = "getapi-provider")
    public Object[][] getApi(){
        return new  Object[][]{
                {"2",200},
                {"3",200},
    } ;
    };


}
