
package com.medelz.testCases;

import java.io.FileWriter;
import java.io.IOException;
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


public class HOME_upload_file extends TestBase{
	
		
	@BeforeClass
	void upload_file() throws InterruptedException
	{
	Thread.sleep(10000);
	logger.info("*********Started upload_file **********");
	
	String gettoken = readFromFile("token.txt");
	
	RestAssured.baseURI = TestBase.baseURI;;
	httpRequest = RestAssured.given();
	JSONObject requestParams = new JSONObject();
	requestParams.put("banner_attachment_id", 26400); // Cast
	requestParams.put("banner_aws_key", "");
	requestParams.put("banner_file_size", "333");
	requestParams.put("banner_height", "333");
	requestParams.put("banner_width", "333");
	requestParams.put("category", "music");
	requestParams.put("content_quality", "professional");
	requestParams.put("dance_genre_id", 0);
	requestParams.put("fashion_genre_id", "0");
	requestParams.put("file_size", 23913466);
	requestParams.put("film_genre_id", 3461);
	requestParams.put("film_language_id", 5890);
	requestParams.put("image_height", 0);
	requestParams.put("image_width", 0);
	requestParams.put("media_attachment_id", 0);
	requestParams.put("music_genre_id", 1585);
	requestParams.put("music_language_id", 1332);
	requestParams.put("painting_material_id", 4188);
	requestParams.put("painting_medium_id", 4233);
	requestParams.put("painting_size_id",4758);
	requestParams.put("painting_style_id",4590);
	requestParams.put("painting_subject_id",4389);
	requestParams.put("painting_color_id",4055);
	requestParams.put("painting_orientation_id",4374);
	requestParams.put("photography_genre_id",3756);
	requestParams.put("poetry_genre_id",0);
	requestParams.put("post_aws_key", "wp-content/uploads/dummy-music-video.mp4");
	requestParams.put("post_description","Cloud flare test to verify the test.");
	requestParams.put("post_duration",83847);
	requestParams.put("post_status","public");
	requestParams.put("post_title","Cloud flare test - new");


	httpRequest.header("Accept", "application/json");
	httpRequest.header("Authorization", "Basic bWVkZWx6Ok1lZGVsekBkZXZAMTk3Ng==");
	httpRequest.header("Content-Type", "application/json");
	httpRequest.header("Authorization-Token", gettoken);
//	httpRequest.header("Authorization-Token", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJJRCI6ODgwLCJ1c2VyX2xvZ2luIjoia2FzaGlmX3plYiBraGFuIDg3ODAxMSIsInVzZXJfZW1haWwiOiJrYXNoaWZ6azIxNkBnbWFpbC5jb20iLCJ1c2VyX3JlZ2lzdGVyZWQiOiIyMDIzLTA2LTA4IDA1OjM4OjM3IiwiZGlzcGxheV9uYW1lIjoia2FzaGlmIGtoYW4iLCJleHAiOjE3MDQ2OTQ2NjcsInNlY3JldF9rZXkiOiJOVEk1TnpJeCJ9.enoZJx5z9BrfzFTuzwFXOAb-i1KMVYHlSroV5nybMEE");
	
	httpRequest.body(requestParams.toJSONString());
	response = httpRequest.request(Method.POST,"/v1/upload_file");
	
	Thread.sleep(5);

	}
			


	@Test
	void checkResposeBody()
	{
		logger.info("***********  Checking Respose Body **********");
		
		String responseBody = response.getBody().asString();
		logger.info("Response Body==>"+responseBody);
		Assert.assertEquals(responseBody.contains("Cloud flare test - new"), true);
		Assert.assertEquals(responseBody.contains("File has been uploaded successfuly."), true);
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

	 public static Integer  fileid() {
		    JsonPath jsonPath = response.jsonPath();
		    int fileid = jsonPath.get("response.file_id");
		    try (FileWriter passwordFile = new FileWriter("fileid.txt")) {
		    	passwordFile.write(Integer.toString(fileid));
	            System.out.println("fileid written to fileid.txt");
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
		    System.out.println(fileid);
		    return fileid;

		}


@AfterClass
void tearDown()
{
	logger.info("*********  Finished upload_file **********");
	
	fileid();

}

}
