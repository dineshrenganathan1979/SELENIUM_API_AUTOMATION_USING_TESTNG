package com.testAPI;

import java.util.ArrayList;
import java.util.HashMap;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.util.RestUtils;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import static org.hamcrest.Matchers.*;

import static io.restassured.RestAssured.*;

public class StudentAPITestWithoutSerialization {
	
	HashMap map = new HashMap();
	static String firstName;
	static String lastName;
	static String emailID;
	static String programmeName;
	
	@BeforeClass
	public void initializeAPI() {
		
		RestAssured.baseURI="http://localhost:8095";
	
		
	}
	@Test(priority=0)
	public void createNewStudent() {
		
		firstName = RestUtils.getStudentFirstName();
		lastName = RestUtils.getStudentLastName();
		emailID = RestUtils.getStudentEmail();
		programmeName = RestUtils.getStudentProgramme();
		
		map.put("id", 101);
		map.put("firstName", firstName);
		map.put("lastName", lastName);
		map.put("email", emailID);
		map.put("programme", programmeName);
		ArrayList<String> courseList = new ArrayList<String>();
		courseList.add("Java");
		courseList.add("Selenium");
		map.put("courses", courseList);
		
		
		Response response = given().contentType(ContentType.JSON).body(map).when().post("/student");
		
		Assert.assertEquals(response.getStatusCode(), 201);
		Assert.assertEquals(response.statusLine(), "HTTP/1.1 201 ");
		response.then().assertThat().body("msg", equalTo("Student added"));
		System.out.println(response.asString());
		
	}
	@Test(priority=1)
	public void verifyStudentCreation() {
		
		Response response = given().when().get("student/101");
		
		Assert.assertEquals(response.statusCode(), 200);
		System.out.println(response.asString());
		response.then().assertThat().body("firstName", equalTo(firstName));
		response.then().assertThat().body("lastName", equalTo(lastName));
		response.then().assertThat().body("email", equalTo(emailID));
		response.then().assertThat().body("programme", equalTo(programmeName));
		response.then().assertThat().body("courses", hasItems("Java","Selenium"));
	}

}
