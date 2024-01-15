
package com.medelz.testCases;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.*;

import com.medelz.base.TestBase;
import com.medelz.testCases.AUTH_pre_registeration_data_validation;
import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;


public class AUTH_user_registeration extends TestBase{
	String number = readFromFile("number.txt");
	String emaill = readFromFile("email.txt");
	String passwordd = readFromFile("password.txt");
	String usernamee = readFromFile("username.txt");
	String name = readFromFile("name.txt");
	
	@BeforeClass
	void user_registeration() throws InterruptedException
	{
	Thread.sleep(10000);
	logger.info("*********Started user_registeration **********");
	System.out.println(number + " " + emaill + " " + passwordd + " " + usernamee + " " + name);

	RestAssured.baseURI = TestBase.baseURI;;
	httpRequest = RestAssured.given();
	JSONObject requestParams = new JSONObject();
	
//	requestParams.put("username","aziz_khan345015" ); 
	requestParams.put("username", usernamee); 
	requestParams.put("firstname", name); 
//	requestParams.put("firstName", "aziz"); // Cast
	requestParams.put("lastname", "khan");
	requestParams.put("email", emaill);
//	requestParams.put("email", "rocco@yopmail.com");
	requestParams.put("password", passwordd);
//	requestParams.put("password", "Envy15gold@");
	requestParams.put("role", "artist");
	requestParams.put("country_code", "+1");
	requestParams.put("phoneNumber", number);
//	requestParams.put("phoneNumber", "+12706818943");
	requestParams.put("dob", "1980/01/01");
	requestParams.put("gender", "male");
	requestParams.put("user_fcm", "12312312@#@#@#");
	requestParams.put("platform", "postman");
	requestParams.put("country_iso", "PK");


	httpRequest.header("Accept", "application/json");
	httpRequest.header("Authorization", "Basic bWVkZWx6Ok1lZGVsekBkZXZAMTk3Ng==");
	httpRequest.header("Content-Type", "application/json");
//	httpRequest.header("Authorization-Token", AUTH_user_login.getToken());
//	httpRequest.header("Authorization-Token", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJJRCI6ODgwLCJ1c2VyX2xvZ2luIjoia2FzaGlmX3plYiBraGFuIDg3ODAxMSIsInVzZXJfZW1haWwiOiJrYXNoaWZ6azIxNkBnbWFpbC5jb20iLCJ1c2VyX3JlZ2lzdGVyZWQiOiIyMDIzLTA2LTA4IDA1OjM4OjM3IiwiZGlzcGxheV9uYW1lIjoia2FzaGlmIG11c2siLCJleHAiOjE3MDUzODkyNzgsInNlY3JldF9rZXkiOiJPVEkwTVRJMCJ9.MFCY0XAogSXQOO_Bid6Rd-RSC9nXGgkLfAwPgYffTd4");
	
	httpRequest.body(requestParams.toJSONString());
	response = httpRequest.request(Method.POST,"/v1/users/register");
	
	Thread.sleep(5);
	}
			


	@Test
	void checkResposeBody()
	{
		logger.info("***********  Checking Respose Body **********");
		
		String responseBody = response.getBody().asString();
		logger.info("Response Body==>"+responseBody);
		Assert.assertEquals(responseBody.contains(emaill), true);
		Assert.assertEquals(responseBody.contains(usernamee), true);
		Assert.assertEquals(responseBody.contains(number), true);
		Assert.assertEquals(responseBody.contains(name), true);
		Assert.assertEquals(responseBody.contains("Congratulations! You are now signed up."), true);
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
	logger.info("*********  Finished user_registeration **********");
}

}
