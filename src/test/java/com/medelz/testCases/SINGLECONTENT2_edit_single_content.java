
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


public class SINGLECONTENT2_edit_single_content extends TestBase{

	String gettoken = readFromFile("token.txt");
	String fileid = readFromFile("fileid.txt");
	int fid=Integer.parseInt(fileid);
	@BeforeClass
	void edit_single_content() throws InterruptedException
	{
	
	logger.info("*********Started edit_single_content **********");
		
	RestAssured.baseURI = TestBase.baseURI;
	httpRequest = RestAssured.given();
	JSONObject requestParams = new JSONObject();
	requestParams.put("post_id", fid); // Cast
	requestParams.put("post_status", "public");
	requestParams.put("post_title", "Latest - music");
	requestParams.put("post_description", "dsdsds and this is updated here again");
	requestParams.put("category", "music");
	requestParams.put("content_quality", "professional");
	requestParams.put("music_genre_id", 1606);
	requestParams.put("music_language_id", 1329);
	requestParams.put("dance_genre_id", 0);
	requestParams.put("film_genre_id", 0);
	requestParams.put("film_language_id", 0);
	requestParams.put("poetry_genre_id", 0);
	requestParams.put("painting_subject_id", 0);
	requestParams.put("painting_style_id", 0);
	requestParams.put("painting_medium_id", 0);
	requestParams.put("painting_material_id", 0);
	requestParams.put("painting_size_id", 0);
	requestParams.put("photography_genre_id", 0);


	httpRequest.header("Accept", "application/json");
	httpRequest.header("Authorization", "Basic bWVkZWx6Ok1lZGVsekBkZXZAMTk3Ng==");
	httpRequest.header("Content-Type", "application/json");
	httpRequest.header("Authorization-Token", gettoken);
//	httpRequest.header("Authorization-Token", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJJRCI6ODgwLCJ1c2VyX2xvZ2luIjoia2FzaGlmX3plYiBraGFuIDg3ODAxMSIsInVzZXJfZW1haWwiOiJrYXNoaWZ6azIxNkBnbWFpbC5jb20iLCJ1c2VyX3JlZ2lzdGVyZWQiOiIyMDIzLTA2LTA4IDA1OjM4OjM3IiwiZGlzcGxheV9uYW1lIjoia2FzaGlmIGtoYW4iLCJleHAiOjE3MDQ2OTQ2NjcsInNlY3JldF9rZXkiOiJOVEk1TnpJeCJ9.enoZJx5z9BrfzFTuzwFXOAb-i1KMVYHlSroV5nybMEE");
	
	httpRequest.body(requestParams.toJSONString());
	response = httpRequest.request(Method.POST,"/v1/edit_single_content");
	
	Thread.sleep(5);
	}
			


	@Test
	void checkResposeBody()
	{
		logger.info("***********  Checking Respose Body **********");
		
		String responseBody = response.getBody().asString();
		logger.info("Response Body==>"+responseBody);
		Assert.assertEquals(responseBody.contains("Latest - music"), true);
		Assert.assertEquals(responseBody.contains(Integer.toString(fid)), true);
		Assert.assertEquals(responseBody.contains("public"), true);
		Assert.assertEquals(responseBody.contains("1329"), true);
		Assert.assertEquals(responseBody.contains("1606"), true);
		Assert.assertEquals(responseBody.contains("post has been updated!"), true);
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
	logger.info("*********  Finished edit_single_content **********");
}

}