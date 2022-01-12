package com.testAPI;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import static org.hamcrest.Matchers.*;

import static io.restassured.RestAssured.*;

public class GET_Employees {
	
	/*
	 * given() -> For Get Call Preconditions , Post Call -> Precondition , body (map), requestHeaders("key","value")
	 * 
	 *  
	 * and()
	 * 
	 * when() -> Condition -> GET / POST /PUT /DELETE
	 * 
	 * and()
	 * 
	 * then() -> Assertions or Validations.
	 * 
	 * and()
	 * 
	 * 
	 */

	
	@BeforeClass
	public void initialiseApi() {
		
		RestAssured.baseURI="http://dummy.restapiexample.com/api/v1";
		RestAssured.basePath="/employees";
		
	}

	@Test (priority=1)
	public void testGetMultipleEmployees() {
		
		  //Response response = response = given().when().get("http://dummy.restapiexample.com/api/v1/employees");
		  Response response = given().when().get();
		  
		 while(response.getStatusCode()==429) {
			 
			 	response = given().when().get();
		 
		 }
		 String responseBody =response.asString();
		 System.out.println(responseBody);
		 
		 Assert.assertEquals(response.getStatusCode(),200);
		 Assert.assertEquals(response.getContentType(),"application/json");
        
		 response.then().assertThat().statusLine("HTTP/1.1 200 OK");
         response.then().assertThat().body("data[0].employee_name", equalTo("Tiger Nixon"));
         response.then().assertThat().body("data.employee_name", hasItems("Tiger Nixon","Garrett Winters", "Ashton Cox", "Cedric Kelly"));
         response.then().assertThat().body("message", equalTo("Successfully! All records has been fetched."));
     	
		}
	
	
}
