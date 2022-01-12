package com.testAPI;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.util.RestUtils;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class StudentAPITestWithSerialization_DeSerialization {
	
    static String firstName;
	static String lastName;
	static String emailID;
	static String programmeName;
	
	
	@Test(priority=0)
	public void createNewStudentUsingSerialiation() {
		
		firstName = RestUtils.getStudentFirstName();
		lastName = RestUtils.getStudentLastName();
		emailID = RestUtils.getStudentEmail();
		programmeName = RestUtils.getStudentProgramme();
		
		Student student = new Student();
		ArrayList<String> courseList = new ArrayList<String>();
		
		courseList.add("Python");
		courseList.add("Artificial Intelligence");
		student.setCourses(courseList);
		student.setId(101);
		student.setFirstName(firstName);
		student.setLastName(lastName);
		student.setEmail(emailID);
		student.setProgramme(programmeName);		
		
		Response response = given()
				.contentType(ContentType.JSON)
				.body(student)
				.when()
				.post("http://localhost:8095/student");
		
		response.then().assertThat().statusCode(201);
		response.then().assertThat().statusLine( "HTTP/1.1 201 ");
		response.then().assertThat().body("msg", equalTo("Student added"));
		System.out.println(response.asString());
		
	}
	
	@Test(priority=1)
	public void getStudentRecordUsingDeSerialization() {
		
		Student student = given().when().get("http://localhost:8095/student/101").as(Student.class);
		Assert.assertEquals(student.getFirstName(), firstName);
		Assert.assertEquals(student.getLastName(), lastName);
		Assert.assertEquals(student.getEmail(), emailID);
		Assert.assertEquals(student.getProgramme(), programmeName);
		
	}


}
