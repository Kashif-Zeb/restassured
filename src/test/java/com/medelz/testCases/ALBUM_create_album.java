
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


public class ALBUM_create_album extends TestBase{
	String gettoken = readFromFile("token.txt");
	String fileid = readFromFile("fileid.txt");
	int fid=Integer.parseInt(fileid);

		
	@BeforeClass
	void create_album() throws InterruptedException
	{
	
	logger.info("*********Started create_album **********");
		
	RestAssured.baseURI = TestBase.baseURI;
	httpRequest = RestAssured.given();
	JSONObject requestParams = new JSONObject();
	requestParams.put("type", "music"); // Cast
	requestParams.put("title", "kashif album");
	List<Integer> contentIdsList = new ArrayList<>();
	contentIdsList.add(fid);
	requestParams.put("content_ids", contentIdsList);
	requestParams.put("description", "This album has the hits of Adele");
	requestParams.put("availability", "exclusive");
	requestParams.put("banner_aws_key","public/1684152504639Audiotree-736885__480_medelz_large.jpg" );
	requestParams.put("banner_height",500 );
	requestParams.put("banner_width",200 );
	requestParams.put("banner_size",4);

	httpRequest.header("Accept", "application/json");
	httpRequest.header("Authorization", "Basic bWVkZWx6Ok1lZGVsekBkZXZAMTk3Ng==");
	httpRequest.header("Content-Type", "application/json");
	httpRequest.header("Authorization-Token", gettoken);
//	httpRequest.header("Authorization-Token", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJJRCI6ODgwLCJ1c2VyX2xvZ2luIjoia2FzaGlmX3plYiBraGFuIDg3ODAxMSIsInVzZXJfZW1haWwiOiJrYXNoaWZ6azIxNkBnbWFpbC5jb20iLCJ1c2VyX3JlZ2lzdGVyZWQiOiIyMDIzLTA2LTA4IDA1OjM4OjM3IiwiZGlzcGxheV9uYW1lIjoia2FzaGlmIGtoYW4iLCJleHAiOjE3MDQ2OTQ2NjcsInNlY3JldF9rZXkiOiJOVEk1TnpJeCJ9.enoZJx5z9BrfzFTuzwFXOAb-i1KMVYHlSroV5nybMEE");
	
	httpRequest.body(requestParams.toJSONString());
	response = httpRequest.request(Method.POST,"/v1/create_album");
	
	Thread.sleep(5);
	}
			


	@Test
	void checkResposeBody()
	{
		logger.info("***********  Checking Respose Body **********");
		
		String responseBody = response.getBody().asString();
		System.out.println("response is ;"+responseBody);
		logger.info("Response Body==>"+responseBody);
		Assert.assertEquals(responseBody.contains("exclusive"), true);
		Assert.assertEquals(responseBody.contains("kashif album"), true);
//		Assert.assertEquals(responseBody.contains("kashif khan"), true);
		Assert.assertEquals(responseBody.contains("music"), true);
//		Assert.assertEquals(responseBody.contains(PLAYLIST_create_playlist.getplaylistid()), true);
		Assert.assertEquals(responseBody.contains("Album has been created."), true);
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


public static Integer  albumid() {
		    JsonPath jsonPath = response.jsonPath();
		    int id = jsonPath.get("album_data.id");
		    
		    try (FileWriter emailFile = new FileWriter("albumid.txt")) {
	            emailFile.write(Integer.toString(id));
	            System.out.println("albumid written to albumid.txt");
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
		    System.out.println(id);
		    return id;
		    
		    

		}

@AfterClass
void tearDown()
{
	logger.info("*********  Finished create_album **********");
	albumid();
}

}
