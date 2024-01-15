
package com.medelz.testCases;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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


public class PREREGIS_user_pre_registration_otp_verification extends TestBase{

		
	@BeforeClass
	void user_pre_registration_otp_verification() throws InterruptedException
	{
		Thread.sleep(10000);
	logger.info("*********Started user_pre_registration_otp_verification **********");
		
	RestAssured.baseURI = TestBase.baseURI;;
	httpRequest = RestAssured.given();
	Scanner scanner = new Scanner(System.in);
	System.out.print("Enter a value:phone ");
	int phoneotp = scanner.nextInt();
	System.out.print(phoneotp);
	System.out.print("Enter a value:email ");
	int emailotp = scanner.nextInt();
	System.out.print(emailotp);
	String readnumber = readFromFile("number.txt");
	String reademail = readFromFile("email.txt");
	JSONObject requestParams = new JSONObject();
	requestParams.put("user_email",reademail);
	requestParams.put("user_phone",readnumber);
	requestParams.put("user_phone_otp",phoneotp );
	requestParams.put("user_email_otp", emailotp);



	httpRequest.header("Accept", "application/json");
	httpRequest.header("Authorization", "Basic bWVkZWx6Ok1lZGVsekBkZXZAMTk3Ng==");
	httpRequest.header("Content-Type", "application/json");
//	httpRequest.header("Authorization-Token", AUTH_user_login.getToken());
//	httpRequest.header("Authorization-Token", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJJRCI6ODgwLCJ1c2VyX2xvZ2luIjoia2FzaGlmX3plYiBraGFuIDg3ODAxMSIsInVzZXJfZW1haWwiOiJrYXNoaWZ6azIxNkBnbWFpbC5jb20iLCJ1c2VyX3JlZ2lzdGVyZWQiOiIyMDIzLTA2LTA4IDA1OjM4OjM3IiwiZGlzcGxheV9uYW1lIjoia2FzaGlmIGtoYW4iLCJleHAiOjE3MDQ2OTQ2NjcsInNlY3JldF9rZXkiOiJOVEk1TnpJeCJ9.enoZJx5z9BrfzFTuzwFXOAb-i1KMVYHlSroV5nybMEE");
	
	httpRequest.body(requestParams.toJSONString());
	response = httpRequest.request(Method.POST,"/v1/user_pre_registration_otp_verification");
	
	Thread.sleep(5);
	}
			


	@Test
	void checkResposeBody()
	{
		logger.info("***********  Checking Respose Body **********");
		
		String responseBody = response.getBody().asString();
		logger.info("Response Body==>"+responseBody);

		Assert.assertEquals(responseBody.contains("Congratulations OTPs (one time password) has been verified."), true);
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
	logger.info("*********  Finished user_pre_registration_otp_verification **********");
}

}
