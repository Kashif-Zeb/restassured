
package com.medelz.testCases;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.*;
import com.medelz.testCases.SINGLECONTENT2_add_comment;
import com.medelz.base.TestBase;
import com.medelz.testCases.PLAYLIST_create_playlist;
import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;


public class SINGLECONTENT2_update_comment extends TestBase{
	
	public static String readFromFileee(String fileName) {
        StringBuilder content = new StringBuilder();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                content.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        String abc = content.toString();
        return abc;
    }
	
	
	
		
	@BeforeClass
	void update_comment() throws InterruptedException
	{
		Thread.sleep(20000);
	logger.info("*********Started update_comment **********");
	String gettoken = readFromFile("token.txt");
	String fileid = readFromFile("fileid.txt");
	String commentid = readFromFileee("commentid.txt");
	int fid=Integer.parseInt(fileid);
	int cmid=Integer.parseInt(commentid);
		System.out.println(commentid);
	RestAssured.baseURI = TestBase.baseURI;
	httpRequest = RestAssured.given();
	JSONObject requestParams = new JSONObject();
	requestParams.put("file_id", fid); // Cast
	requestParams.put("comment_data", "I am updating the comment");
//	requestParams.put("comment_id", 1877);
	requestParams.put("comment_id",cmid );
	


	httpRequest.header("Accept", "application/json");
	httpRequest.header("Authorization", "Basic bWVkZWx6Ok1lZGVsekBkZXZAMTk3Ng==");
	httpRequest.header("Content-Type", "application/json");
	httpRequest.header("Authorization-Token", gettoken);
//	httpRequest.header("Authorization-Token", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJJRCI6ODgwLCJ1c2VyX2xvZ2luIjoia2FzaGlmX3plYiBraGFuIDg3ODAxMSIsInVzZXJfZW1haWwiOiJrYXNoaWZ6azIxNkBnbWFpbC5jb20iLCJ1c2VyX3JlZ2lzdGVyZWQiOiIyMDIzLTA2LTA4IDA1OjM4OjM3IiwiZGlzcGxheV9uYW1lIjoia2FzaGlmIGtoYW4iLCJleHAiOjE3MDQ2OTQ2NjcsInNlY3JldF9rZXkiOiJOVEk1TnpJeCJ9.enoZJx5z9BrfzFTuzwFXOAb-i1KMVYHlSroV5nybMEE");
	
	httpRequest.body(requestParams.toJSONString());
	response = httpRequest.request(Method.POST,"/v1/update_comment");
	
	Thread.sleep(5);
	}
			


	@Test
	void checkResposeBody()
	{
		logger.info("***********  Checking Respose Body **********");
		String gettoken = readFromFile("token.txt");
		String fileid = readFromFile("fileid.txt");
		String commentid = readFromFileee("commentid.txt");
		int fid=Integer.parseInt(fileid);
		int cmid=Integer.parseInt(commentid);
		
		String responseBody = response.getBody().asString();
		logger.info("Response Body==>"+responseBody);
		Assert.assertEquals(responseBody.contains("Comment has been updated."), true);
		Assert.assertEquals(responseBody.contains("I am updating the comment"), true);
//		Assert.assertEquals(responseBody.contains("1877"), true);
		Assert.assertEquals(responseBody.contains(Integer.toString(cmid)), true);
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
	logger.info("*********  Finished update_comment **********");
}

}
