
package com.medelz.testCases;

import org.testng.Assert;
import org.testng.annotations.*;

import com.medelz.base.TestBase;
import com.medelz.testCases.AUTH_user_login;
import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;


public class SINGLECONTENT_public_song extends TestBase{
	String gettoken = readFromFile("token.txt");
	String playlistid = readFromFile("playlistid.txt");
	String accountid = readFromFile("accountid.txt");
	
	int pid=Integer.parseInt(playlistid);
	int aid=Integer.parseInt(accountid);
	
		
	@BeforeClass
	void public_song() throws InterruptedException
	{
		Thread.sleep(10000);
	logger.info("*********Started public_song **********");
	System.out.println(aid);
	RestAssured.baseURI = TestBase.baseURI;
	httpRequest = RestAssured.given();
	httpRequest.header("Accept", "application/json");
	httpRequest.header("Authorization", "Basic bWVkZWx6Ok1lZGVsekBkZXZAMTk3Ng==");
	httpRequest.header("Content-Type", "application/json");
	httpRequest.header("Authorization-Token", gettoken);
//	httpRequest.header("Authorization-Token", "Bearer Basic bWVkZWx6Ok1lZGVsekBkZXZAMTk3Ng==");
	response = httpRequest.request(Method.GET,"/v1/public_playlist_songs?playlistid="+pid+"&user_id="+aid+"&page_number=1&entries_perPage=10");
	
	Thread.sleep(5);
	}
			


	@Test
	void checkResposeBody()
	{
		logger.info("***********  Checking Respose Body **********");
		
		String responseBody = response.getBody().asString();
//		System.out.println("response is ;"+responseBody);
		logger.info("Response Body==>"+responseBody);
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
		logger.info("*********  Finished public_song **********");
	}
				 	
}
