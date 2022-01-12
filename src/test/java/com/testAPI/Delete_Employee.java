package com.testAPI;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class Delete_Employee {
	
	@BeforeClass
	public void initialiseApi() {
		
		RestAssured.baseURI="http://dummy.restapiexample.com/api/v1";
		RestAssured.basePath="/delete/2";
		
	}

	@Test (priority=1)
	public void testDeleteEmployee() {
		
		  //Response response = response = given().when().get("http://dummy.restapiexample.com/api/v1/employees");
		  Response response = given().when().delete();
		  
		 while(response.getStatusCode()==429) {
			 
			 	response = given().when().delete();
		 
		 }
		 String responseBody =response.asString();
		 System.out.println(responseBody);
		 
		 Assert.assertEquals(response.getStatusCode(),200);
		 Assert.assertEquals(response.getContentType(),"application/json");
        
		 response.then().assertThat().statusLine("HTTP/1.1 200 OK");
		 response.then().assertThat().body("status", equalTo("success"));
		 response.then().assertThat().body("message", equalTo("Successfully! Record has been deleted"));
     	
		}

}
