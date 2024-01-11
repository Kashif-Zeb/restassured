
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


public class AUTH_pre_registeration_data_validation extends TestBase{

		
	@BeforeClass
	void pre_registeration_data_validation() throws InterruptedException
	{
	
	logger.info("*********Started pre_registeration_data_validation **********");
		
	RestAssured.baseURI = TestBase.baseURI;;
	httpRequest = RestAssured.given();
	JSONObject requestParams = new JSONObject();
	requestParams.put("firstName", "aziz"); // Cast
	requestParams.put("lastName", "khan");
	requestParams.put("email", "aziz@yopmail.com");
	requestParams.put("password", "Envy15gold@");
	requestParams.put("role", "artist");
	requestParams.put("country_code", "+1");
	requestParams.put("phoneNumber", "+12016883846");
	requestParams.put("dob", "1980/01/01");
	requestParams.put("gender", "male");


	httpRequest.header("Accept", "application/json");
	httpRequest.header("Authorization", "Basic bWVkZWx6Ok1lZGVsekBkZXZAMTk3Ng==");
	httpRequest.header("Content-Type", "application/json");
//	httpRequest.header("Authorization-Token", AUTH_user_login.getToken());
//	httpRequest.header("Authorization-Token", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJJRCI6ODgwLCJ1c2VyX2xvZ2luIjoia2FzaGlmX3plYiBraGFuIDg3ODAxMSIsInVzZXJfZW1haWwiOiJrYXNoaWZ6azIxNkBnbWFpbC5jb20iLCJ1c2VyX3JlZ2lzdGVyZWQiOiIyMDIzLTA2LTA4IDA1OjM4OjM3IiwiZGlzcGxheV9uYW1lIjoia2FzaGlmIGtoYW4iLCJleHAiOjE3MDQ2OTQ2NjcsInNlY3JldF9rZXkiOiJOVEk1TnpJeCJ9.enoZJx5z9BrfzFTuzwFXOAb-i1KMVYHlSroV5nybMEE");
	
	httpRequest.body(requestParams.toJSONString());
	response = httpRequest.request(Method.POST,"/v1/validate_registration_details");
	
	Thread.sleep(5);
//	org.json.JSONObject responseObject = new org.json.JSONObject(response);
	}
			


	@Test
	void checkResposeBody()
	{
		logger.info("***********  Checking Respose Body **********");
		
		String responseBody = response.getBody().asString();
		logger.info("Response Body==>"+responseBody);
		Assert.assertEquals(responseBody.contains("aziz@yopmail.com"), true);
		Assert.assertEquals(responseBody.contains("Envy15gold@"), true);
		Assert.assertEquals(responseBody.contains("+12016883846"), true);
		Assert.assertEquals(responseBody.contains("aziz"), true);
		Assert.assertEquals(responseBody.contains("User data is validated!."), true);
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

	 public static String  email() {
		    JsonPath jsonPath = response.jsonPath();
		    String email = jsonPath.get("response.validated_data.email");
		    try (FileWriter passwordFile = new FileWriter("email.txt")) {
		    	passwordFile.write(email);
	            System.out.println("Email written to password.txt");
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
		    System.out.println(email);
		    return email;

		}
	 public static String  password() {
		    JsonPath jsonPath = response.jsonPath();
		    String password = jsonPath.get("response.validated_data.password");
		    try (FileWriter passwordFile = new FileWriter("password.txt")) {
		    	passwordFile.write(password);
	            System.out.println("Email written to password.txt");
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
		    System.out.println(password);
		    return password;

		}
	 public static String  number() {
		    JsonPath jsonPath = response.jsonPath();
		    String number = jsonPath.get("response.validated_data.phoneNumber");
		    try (FileWriter emailFile = new FileWriter("number.txt")) {
	            emailFile.write(number);
	            System.out.println("number written to number.txt");
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
		    
		    System.out.println(number);
		    return number;

		}

	 public static String  name() {
		    JsonPath jsonPath = response.jsonPath();
		    String name = jsonPath.get("response.validated_data.firstname");
		    try (FileWriter emailFile = new FileWriter("name.txt")) {
	            emailFile.write(name);
	            System.out.println("name written to name.txt");
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
		    
		    System.out.println(name);
		    return name;

		}
	 public static String  username() {
		    JsonPath jsonPath = response.jsonPath();
		    String username = jsonPath.get("response.validated_data.username");
		    try (FileWriter emailFile = new FileWriter("username.txt")) {
	            emailFile.write(username);
	            System.out.println("username written to username.txt");
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
		    System.out.println(username);
		    return username;

		}

@AfterClass
void tearDown()
{
	logger.info("*********  Finished pre_registeration_data_validation **********");
	name();
	username();
	number();
	email();
	password();
}

}
