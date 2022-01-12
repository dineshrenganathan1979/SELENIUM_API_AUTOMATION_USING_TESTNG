package com.testAPI;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import java.util.HashMap;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.util.RestUtils;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class PUT_Employee {

	
	HashMap map = new HashMap();	
	@BeforeClass
	public void initialiseApi() {
				
		map.put("employee_name", "Arun Kumar");
		map.put("employee_salary", RestUtils.getEmployeeSalary());
		map.put("employee_age", RestUtils.getEmployeeAge());

		RestAssured.baseURI="http://dummy.restapiexample.com/api/v1";
		RestAssured.basePath="/update/2291";
	}

	@Test (priority=1)
	public void testPutEmployee() throws JsonProcessingException {
		
		String json = new ObjectMapper().writeValueAsString(map);
		
		 
		 Response response = given().contentType("application/json").body(json).when().put();
				  
						  
		 while(response.getStatusCode()==429) {
			 
			 	response = given().contentType("application/json").body(json).when().put();
		 
		 }
		 String responseBody =response.asString();
		 System.out.println(responseBody);
		 
		 Assert.assertEquals(response.getStatusCode(),200);
		 Assert.assertEquals(response.getContentType(),"application/json");
        
		 response.then().assertThat().statusLine("HTTP/1.1 200 OK");
		 response.then().assertThat().body("status", equalTo("success"));
		 response.then().assertThat().body("data.employee_name", equalTo("Arun Kumar"));
		 response.then().assertThat().body("message", equalTo("Successfully! Record has been updated."));
     	
		}
	
}
