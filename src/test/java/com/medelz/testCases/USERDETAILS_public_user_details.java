
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


public class USERDETAILS_public_user_details extends TestBase{

		
	@BeforeClass
	void public_user_details() throws InterruptedException
	{
	
	logger.info("*********Started public_user_details **********");
		
	RestAssured.baseURI = TestBase.baseURI;
	httpRequest = RestAssured.given();
	JSONObject requestParams = new JSONObject();

	
	requestParams.put("user_id", 880); // Cast


	httpRequest.header("Accept", "application/json");
	httpRequest.header("Authorization", "Basic bWVkZWx6Ok1lZGVsekBkZXZAMTk3Ng==");
	httpRequest.header("Content-Type", "application/json");
	httpRequest.header("Authorization-Token", AUTH_user_login.getToken());
//	httpRequest.header("Authorization-Token", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJJRCI6ODgwLCJ1c2VyX2xvZ2luIjoia2FzaGlmX3plYiBraGFuIDg3ODAxMSIsInVzZXJfZW1haWwiOiJrYXNoaWZ6azIxNkBnbWFpbC5jb20iLCJ1c2VyX3JlZ2lzdGVyZWQiOiIyMDIzLTA2LTA4IDA1OjM4OjM3IiwiZGlzcGxheV9uYW1lIjoia2FzaGlmIGtoYW4iLCJleHAiOjE3MDQ2OTQ2NjcsInNlY3JldF9rZXkiOiJOVEk1TnpJeCJ9.enoZJx5z9BrfzFTuzwFXOAb-i1KMVYHlSroV5nybMEE");
	
	httpRequest.body(requestParams.toJSONString());
	response = httpRequest.request(Method.POST,"/v1/get_public_user_details");
	
	Thread.sleep(5);
	}
			


	@Test
	void checkResposeBody()
	{
		logger.info("***********  Checking Respose Body **********");
		
		String responseBody = response.getBody().asString();
		System.out.println("response is ;"+responseBody);
		logger.info("Response Body==>"+responseBody);
		Assert.assertEquals(responseBody.contains("User data is listed below!."), true);
		Assert.assertEquals(responseBody.contains("kashif"), true);
		Assert.assertEquals(responseBody.contains("880"), true);
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
	logger.info("*********  Finished public_user_details **********");
}

}
