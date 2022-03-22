package com.testAPI;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

import java.util.ArrayList;
import java.util.HashMap;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.util.RestUtils;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class Update_Student_Details {
	
	HashMap map = new HashMap();
	static String firstName;
	static String lastName;
	static String emailID;
	static String programmeName;
	@BeforeClass
	public void initializeAPI() {
		//java -jar Student_App.jar --server.port=8888
		RestAssured.baseURI="http://localhost:8888";
		RestAssured.basePath="student/3";
		
		firstName = RestUtils.getStudentFirstName();
		lastName = RestUtils.getStudentLastName();
		emailID = RestUtils.getStudentEmail();
		programmeName = RestUtils.getStudentProgramme();
		map.put("firstName", firstName);
		map.put("lastName", lastName);
		map.put("email", emailID);
		map.put("programme", programmeName );
		ArrayList<String> courseList = new ArrayList<String>();
		courseList.add("Java");
		courseList.add("Selenium");
		map.put("courses", courseList);
		
	}
	
	@Test(priority=0)
	public void updateStudent() {
		
		Response response = given().contentType(ContentType.JSON).body(map).when().put();
			
		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertEquals(response.statusLine(), "HTTP/1.1 200 ");
		response.then().assertThat().body("msg", equalTo("Student Updated"));
		System.out.println(response.asString());
		
	}
	
	@Test(priority=1)
	public void getUpdatedStudentDetails() {
		
		Response response = given().when().get();
			
		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertEquals(response.statusLine(), "HTTP/1.1 200 ");
		System.out.println(response.asString());
		response.then().assertThat().body("firstName", equalTo(firstName));
		response.then().assertThat().body("lastName", equalTo(lastName));
		response.then().assertThat().body("email", equalTo(emailID));
		response.then().assertThat().body("programme", equalTo(programmeName));
		response.then().assertThat().body("courses", hasItems("Java","Selenium"));
		
	}

	
	
}
