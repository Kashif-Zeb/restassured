
package com.medelz.testCases;

import java.io.FileWriter;
import java.io.IOException;

import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.*;

import com.medelz.base.TestBase;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;


public class AUTH_user_login extends TestBase{
	RequestSpecification httpRequest;
	static Response response;
	
	String emaill = readFromFile("email.txt");
	String passwordd = readFromFile("password.txt");
	@BeforeClass
	void login() throws InterruptedException
	{
		String emaill = readFromFile("email.txt");
		String passwordd = readFromFile("password.txt");
		Thread.sleep(10000);
	logger.info("*********post_user_login **********");
		
	RestAssured.baseURI = TestBase.baseURI;
	RequestSpecification httpRequest=RestAssured.given();

	// JSONObject is a class that represents a simple JSON. We can add Key-Value pairs using the put method
	//{"name":"John123X","salary":"123","age":"23"}
	JSONObject requestParams = new JSONObject();
	requestParams.put("username",emaill); // Cast
	requestParams.put("password",passwordd);
	requestParams.put("platform", "postman_login");
	
	// Add a header stating the Request body is a JSON
	httpRequest.header("Accept", "application/json");
	httpRequest.header("Authorization", "Basic bWVkZWx6Ok1lZGVsekBkZXZAMTk3Ng==");
	httpRequest.header("Authorization-Token", "Bearer Basic bWVkZWx6Ok1lZGVsekBkZXZAMTk3Ng==");
	httpRequest.header("Content-Type", "application/json");
	// Add the Json to the body of the request
	httpRequest.body(requestParams.toJSONString());

	response = httpRequest.request(Method.POST, "/v1/users/login");
	
	Thread.sleep(5000);
	

}
	

@Test
void checkResposeBody()
{
	String responseBody = response.getBody().asString();
	System.out.println("response is ;"+responseBody);
	Assert.assertEquals(responseBody.contains(emaill), true);
	Assert.assertEquals(responseBody.contains("Congratulations! You are logged in."), true);
//	Assert.assertEquals(responseBody.contains(empAge), true);
}





@Test
void checkStatusCode()
{
	int statusCode = response.getStatusCode(); // Gettng status code
	Assert.assertEquals(statusCode, 200);
}
	



@Test
void checkContentType()
{
	String contentType = response.header("Content-Type");
	Assert.assertEquals(contentType, "application/json; charset=UTF-8");
}

@Test
void checkserverType()
{
	String serverType = response.header("Server");
	Assert.assertEquals(serverType, "cloudflare");
}




 public static String  getToken() {
    JsonPath jsonPath = response.jsonPath();
    String token = jsonPath.get("user_data.user.meta.token");
    try (FileWriter passwordFile = new FileWriter("token.txt")) {
    	passwordFile.write(token);
        System.out.println("token written to token.txt");
    } catch (IOException e) {
        e.printStackTrace();
    }
    
    System.out.println(token);
    return token;

}
 public static String  accountid() {
	    JsonPath jsonPath = response.jsonPath();
	    String accountid = jsonPath.get("user_data.user.id");
	    try (FileWriter passwordFile = new FileWriter("accountid.txt")) {
	    	passwordFile.write(accountid);
	        System.out.println("accountid written to accountid.txt");
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    
	    System.out.println(accountid);
	    return accountid;

	}


@AfterClass
void tearDown()
{
	logger.info("********* end post_user_login **********");
	
	getToken();
	accountid();
	
}



}
