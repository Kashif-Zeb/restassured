
package com.medelz.testCases;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.*;

import com.medelz.base.TestBase;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;


public class PLAYLIST_create_playlist extends TestBase{

	
	@BeforeClass
	void create_playlist() throws InterruptedException
	{
		Thread.sleep(10000);
	logger.info("*********Started create_playlist **********");
	
	
	String gettoken = readFromFile("token.txt");
	String fileid = readFromFile("fileid.txt");
	
	RestAssured.baseURI = TestBase.baseURI;
	httpRequest = RestAssured.given();
	JSONObject requestParams = new JSONObject();
	requestParams.put("type", "music"); // Cast
	requestParams.put("title", "My first playlist");
	requestParams.put("description", "This is a new playlist for Medelz.");
	requestParams.put("availability", "public");
	List<Integer> contentIdsList = new ArrayList<>();
    contentIdsList.add(Integer.parseInt(fileid));
	requestParams.put("content_ids", contentIdsList);
	requestParams.put("banner_aws_key", "wp-content/uploads/Logo-Test.png");
	requestParams.put("banner_height", 1212);
	requestParams.put("banner_width", 1212);
	requestParams.put("banner_size", 1212);
	requestParams.put("banner_attachment_id", 26052);
	httpRequest.header("Accept", "application/json");
	httpRequest.header("Authorization", "Basic bWVkZWx6Ok1lZGVsekBkZXZAMTk3Ng==");
	httpRequest.header("Content-Type", "application/json");
	httpRequest.header("Authorization-Token", gettoken);
//	httpRequest.header("Authorization-Token", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJJRCI6ODgwLCJ1c2VyX2xvZ2luIjoia2FzaGlmX3plYiBraGFuIDg3ODAxMSIsInVzZXJfZW1haWwiOiJrYXNoaWZ6azIxNkBnbWFpbC5jb20iLCJ1c2VyX3JlZ2lzdGVyZWQiOiIyMDIzLTA2LTA4IDA1OjM4OjM3IiwiZGlzcGxheV9uYW1lIjoia2FzaGlmIG11c2siLCJleHAiOjE3MDUzODkyNzgsInNlY3JldF9rZXkiOiJPVEkwTVRJMCJ9.MFCY0XAogSXQOO_Bid6Rd-RSC9nXGgkLfAwPgYffTd4");
	
	httpRequest.body(requestParams.toJSONString());
	response = httpRequest.request(Method.POST,"/v1/create_playlist");
	
	Thread.sleep(5);
	}
			


	@Test
	void checkResposeBody()
	{
		logger.info("***********  Checking Respose Body **********");
		
		String responseBody = response.getBody().asString();
		System.out.println("response is ;"+responseBody);
		logger.info("Response Body==>"+responseBody);
		Assert.assertEquals(responseBody.contains("public"), true);
		Assert.assertEquals(responseBody.contains("My first playlist"), true);
		Assert.assertEquals(responseBody.contains("playlist has been created."), true);
		Assert.assertEquals(responseBody.contains("music"), true);
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

	 public static Integer  getplaylistid() {
		    JsonPath jsonPath = response.jsonPath();
		    int id = jsonPath.get("playlist_data.id");
		    try (FileWriter passwordFile = new FileWriter("playlistid.txt")) {
		    	passwordFile.write(Integer.toString(id));
		        System.out.println("accountid written to accountid.txt");
		    } catch (IOException e) {
		        e.printStackTrace();
		    }
		    System.out.println(id);
		    return id;

		}


@AfterClass
void tearDown()
{
	logger.info("*********  Finished create_playlist **********");
	getplaylistid();
}

}
