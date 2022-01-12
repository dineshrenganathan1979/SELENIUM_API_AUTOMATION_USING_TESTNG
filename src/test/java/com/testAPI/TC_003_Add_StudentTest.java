package com.testAPI;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.util.ArrayList;
import java.util.Arrays;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.util.EXCEL_Reader;
import com.util.RestUtils;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import pojo.classes.Student;

public class TC_003_Add_StudentTest {
	static int studentId;
	static String emailID;
	
	@DataProvider
	public Object[][] getPostDatafromExcel(){
		return EXCEL_Reader.getExcelData("ADD_STUDENT");
	}
	
	@Test(dataProvider ="getPostDatafromExcel",priority=0)
	public void createNewStudentUsingSerialiation(String baseURL, String method, String requestFistName, String requestLastName, String requestProgramme,  String requestCourseList, String statusCode, String statusLine, String responseMessage) {
		
		studentId = RestUtils.getStudentID();
		emailID = RestUtils.getStudentEmail();
		String[] studentCoursesArray = requestCourseList.split(",");
		Student student = new Student();
		ArrayList<String> courseList = new ArrayList<String>(Arrays.asList(studentCoursesArray));
		student.setCourses(courseList);
		student.setId(studentId);
		student.setFirstName(requestFistName);
		student.setLastName(requestLastName);
		student.setEmail(emailID);
		student.setProgramme(requestProgramme);		
		System.out.println(studentId);
		System.out.println(emailID);
		Response response = given()
				.contentType(ContentType.JSON)
				.body(student)
				.when()
				.post(baseURL);
		
		response.then().assertThat().statusCode(Integer.parseInt(statusCode));
		response.then().assertThat().statusLine(statusLine);
		response.then().assertThat().body("msg", equalTo(responseMessage));
		System.out.println(response.asString());
	}
	
	@Test(dataProvider ="getPostDatafromExcel",priority=1)
	public void getStudentRecordUsingDeSerialization(String baseURL, String method, String requestFistName, String requestLastName, String requestProgramme,  String requestCourseList, String statusCode, String statusLine, String responseMessage) {
		String get_URL = baseURL+"/"+studentId;
		Student student = given().when().get(get_URL).as(Student.class);
		Assert.assertEquals(student.getFirstName(), requestFistName);
		Assert.assertEquals(student.getLastName(), requestLastName);
		Assert.assertEquals(student.getEmail(), emailID);
		Assert.assertEquals(student.getProgramme(), requestProgramme);
		
	}

}
