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

public class TC_004_Update_StudentTest {
	static int studentId;
	static String emailID;
	@DataProvider
	public Object[][] getPutDatafromExcel(){
		return EXCEL_Reader.getExcelData("UPDATE_STUDENT");
	}
	
	@Test(dataProvider ="getPutDatafromExcel",priority=0)
	public void createNewStudentUsingSerialiation(String baseURL, String method, String requestFistName, String requestLastName, String requestProgramme,  String requestCourseList, String statusCode, String statusLine, String responseMessage) {
		
		emailID = RestUtils.getStudentEmail();
		String[] studentCoursesArray = requestCourseList.split(",");
		Student student = new Student();
		ArrayList<String> courseList = new ArrayList<String>(Arrays.asList(studentCoursesArray));
		student.setCourses(courseList);
		student.setFirstName(requestFistName);
		student.setLastName(requestLastName);
		student.setEmail(emailID);
		student.setProgramme(requestProgramme);		
		System.out.println(emailID);
		Response response = given()
				.contentType(ContentType.JSON)
				.body(student)
				.when()
				.put(baseURL);
		
		response.then().assertThat().statusCode(Integer.parseInt(statusCode));
		response.then().assertThat().statusLine(statusLine);
		response.then().assertThat().body("msg", equalTo(responseMessage));
		System.out.println(response.asString());
	}
	
	@Test(dataProvider ="getPutDatafromExcel",priority=1)
	public void getStudentRecordUsingDeSerialization(String baseURL, String method, String requestFistName, String requestLastName, String requestProgramme,  String requestCourseList, String statusCode, String statusLine, String responseMessage) {
		Student student = given().when().get(baseURL).as(Student.class);
		Assert.assertEquals(student.getFirstName(), requestFistName);
		Assert.assertEquals(student.getLastName(), requestLastName);
		Assert.assertEquals(student.getEmail(), emailID);
		Assert.assertEquals(student.getProgramme(), requestProgramme);
		
	}
}
