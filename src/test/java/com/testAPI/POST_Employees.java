package com.testAPI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.*;
import java.util.HashMap;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.util.RestUtils;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class POST_Employees {
    HashMap map = new HashMap();	
	@BeforeClass
	public void initialiseApi() {
				
		map.put("employee_name", RestUtils.getEmployeeName());
		map.put("employee_salary", RestUtils.getEmployeeSalary());
		map.put("employee_age", RestUtils.getEmployeeAge());

		RestAssured.baseURI="http://dummy.restapiexample.com/api/v1";
		RestAssured.basePath="/create";
	}

	@Test (priority=1)
	public void testPostEmployee() throws JsonProcessingException {
		
		String json = new ObjectMapper().writeValueAsString(map);
		
		 
		 Response response = given().contentType("application/json").body(json).when().post();
				  
						  
		 while(response.getStatusCode()==429) {
			 
			 	response = given().contentType("application/json").body(json).when().post();
		 
		 }
		 String responseBody =response.asString();
		 System.out.println(responseBody);
		 
		 Assert.assertEquals(response.getStatusCode(),200);
		 Assert.assertEquals(response.getContentType(),"application/json");
        
		 response.then().assertThat().statusLine("HTTP/1.1 200 OK");
		 response.then().assertThat().body("status", equalTo("success"));
		 response.then().assertThat().body("data.id", is(notNullValue()));
		 response.then().assertThat().body("message", equalTo("Successfully! Record has been added."));
     	
		}
}
