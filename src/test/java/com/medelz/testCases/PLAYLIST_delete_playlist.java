
package com.medelz.testCases;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.*;

import com.medelz.base.TestBase;
import com.medelz.testCases.PLAYLIST_create_playlist;
import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;


public class PLAYLIST_delete_playlist extends TestBase{
	String gettoken = readFromFile("token.txt");
	String playlistid = readFromFile("playlistid.txt");
	
	int pid=Integer.parseInt(playlistid);
		
	@BeforeClass
	void delete_playlist() throws InterruptedException
	{
	
	logger.info("*********Started delete_playlist **********");
		
	RestAssured.baseURI = TestBase.baseURI;
	httpRequest = RestAssured.given();
	JSONObject requestParams = new JSONObject();
	requestParams.put("type", "music"); // Cast
//	requestParams.put("id", 9067);
	requestParams.put("id", pid);

	httpRequest.header("Accept", "application/json");
	httpRequest.header("Authorization", "Basic bWVkZWx6Ok1lZGVsekBkZXZAMTk3Ng==");
	httpRequest.header("Content-Type", "application/json");
	httpRequest.header("Authorization-Token", gettoken);

	httpRequest.body(requestParams.toJSONString());
	response = httpRequest.request(Method.POST,"/v1/delete_playlist");
	
	Thread.sleep(5);
	}
			


	@Test
	void checkResposeBody()
	{
		logger.info("***********  Checking Respose Body **********");
		
		String responseBody = response.getBody().asString();
		System.out.println("response is ;"+responseBody);
		logger.info("Response Body==>"+responseBody);
		Assert.assertEquals(responseBody.contains("private"), true);
		Assert.assertEquals(responseBody.contains("updated title"), true);
		Assert.assertEquals(responseBody.contains("music"), true);
//		Assert.assertEquals(responseBody.contains("9058"), true);
		Assert.assertEquals(responseBody.contains(Integer.toString(pid)), true);
		Assert.assertEquals(responseBody.contains("playlist has been deleted!"), true);
		Assert.assertTrue(responseBody!=null);
		
	}
		
	@Test
	void checkStatusCode()
	{
		logger.info("***********  Checking Status Code **********");
		
		int statusCode = response.getStatusCode(); // Gettng status code
		logger.info("Status Code is ==>" + statusCode); //200
		Assert.assertEquals(statusCode, 200);
		
	}
		
	@Test
	void checkResponseTime()
	{
		logger.info("***********  Checking Response Time **********");
		
		long responseTime = response.getTime(); // Getting status Line
		logger.info("Response Time is ==>" + responseTime);
		
		if(responseTime>8000)
			logger.warn("Response Time is greater than 2000");
		
		Assert.assertTrue(responseTime<10000);
		
			
	}
	

	
	
	@Test
	void checkContentType()
	{
		logger.info("***********  Checking Content Type **********");
		
		String contentType = response.header("Content-Type");
		logger.info("Content type is ==>" + contentType);
		Assert.assertEquals(contentType, "application/json; charset=UTF-8");
	}

	@Test
	void checkserverType()
	{
		logger.info("***********  Checking Server Type **********");
		
		String serverType = response.header("Server");
		logger.info("Server Type is =>" +serverType); 
		Assert.assertEquals(serverType, "cloudflare");
	
	}




@AfterClass
void tearDown()
{
	logger.info("*********  Finished delete_playlist **********");
}

}
