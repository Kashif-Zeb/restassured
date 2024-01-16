
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


public class USERDETAILS_change_user_details extends TestBase{
	String gettoken = readFromFile("token.txt");
	String name = readFromFile("name.txt");
	
	@BeforeClass
	void change_user_details() throws InterruptedException
	{
		Thread.sleep(10000);
	logger.info("*********Started change_user_details **********");
		
	
	String gettoken = readFromFile("token.txt");
	String name = readFromFile("name.txt");
	
	RestAssured.baseURI = TestBase.baseURI;
	httpRequest = RestAssured.given();
	JSONObject requestParams = new JSONObject();
	List<String> userskill = new ArrayList<>();
	userskill.add("Dancer");
	userskill.add("Painter");
	userskill.add("Writer");
	
	List<String> userlang = new ArrayList<>();
	userlang.add("Urdu");
	userlang.add("English");
	userlang.add("Punjabi");
	
	requestParams.put("user_skills", userskill); // Cast
	requestParams.put("user_languages",userlang );
	requestParams.put("hire_me", 1);
	requestParams.put("collab", 1);
	requestParams.put("exclusive_content", 0);
	requestParams.put("get_in_touch", 0);
	requestParams.put("first_name", name);
	requestParams.put("last_name", "musk");
	requestParams.put("date_of_birth", "1995/10/27");
	requestParams.put("short_bio", "my bio.");
	requestParams.put("gender", "male");
	requestParams.put("user_country", "US");
	requestParams.put("user_state", "1416");
	requestParams.put("user_city", "111110");
	requestParams.put("user_country_name", "United States");
	requestParams.put("user_state_name", "California");
	requestParams.put("user_city_name", "Albany");
	requestParams.put("youtube_url", "https://www.youtube.com/@ranaijazofficial55");
	requestParams.put("instagram_url", "https://www.instagram.com/angelinajolie/?hl=en");
	requestParams.put("facebook_url", "https://www.facebook.com/sonakshisinhaofficial/");
	requestParams.put("twitter_url", "https://twitter.com/sadafaj?lang=en");
	requestParams.put("spotify_url", "https://open.spotify.com/artist/7J2gs5q2wLq6lU4q4wkyuV");
	requestParams.put("sound_cloud_url", "https://soundcloud.com/shreya-ghoshal-music");
	requestParams.put("pandora_url", "https://www.pandora.com/restricted");
	requestParams.put("tiktok_url", "https://www.pandora.com/restricted");
//	requestParams.put("type", "music");

	httpRequest.header("Accept", "application/json");
	httpRequest.header("Authorization", "Basic bWVkZWx6Ok1lZGVsekBkZXZAMTk3Ng==");
	httpRequest.header("Content-Type", "application/json");
	httpRequest.header("Authorization-Token", gettoken);
//	httpRequest.header("Authorization-Token", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJJRCI6ODgwLCJ1c2VyX2xvZ2luIjoia2FzaGlmX3plYiBraGFuIDg3ODAxMSIsInVzZXJfZW1haWwiOiJrYXNoaWZ6azIxNkBnbWFpbC5jb20iLCJ1c2VyX3JlZ2lzdGVyZWQiOiIyMDIzLTA2LTA4IDA1OjM4OjM3IiwiZGlzcGxheV9uYW1lIjoia2FzaGlmIGtoYW4iLCJleHAiOjE3MDQ2OTQ2NjcsInNlY3JldF9rZXkiOiJOVEk1TnpJeCJ9.enoZJx5z9BrfzFTuzwFXOAb-i1KMVYHlSroV5nybMEE");
	
	httpRequest.body(requestParams.toJSONString());
	response = httpRequest.request(Method.POST,"/v1/change_user_details");
	
	Thread.sleep(5);
	}
			


	@Test
	void checkResposeBody()
	{
		logger.info("***********  Checking Respose Body **********");
		
		String responseBody = response.getBody().asString();
		System.out.println("response is ;"+responseBody);
		logger.info("Response Body==>"+responseBody);
		Assert.assertEquals(responseBody.contains("California"), true);
		Assert.assertEquals(responseBody.contains("Albany"), true);
		Assert.assertEquals(responseBody.contains("United States"), true);
		Assert.assertEquals(responseBody.contains("Entered details are now updated."), true);
		Assert.assertEquals(responseBody.contains(name), true);
		Assert.assertEquals(responseBody.contains("US"), true);
//		Assert.assertEquals(responseBody.contains("kashif musk"), true);
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
	logger.info("*********  Finished change_user_details **********");
}

}
