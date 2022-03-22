package com.testAPI;

import static io.restassured.RestAssured.given;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;
import static org.testng.Assert.assertEquals;

import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import org.testng.annotations.DataProvider;

import com.util.EXCEL_Reader;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class TC_002_Get_Student_By_IDTest {
	
	@BeforeClass
	public void initializeAPI() {
		//java -jar Student_App.jar --server.port=8888
		RestAssured.baseURI="http://localhost:8888";
		RestAssured.basePath="student/3";
		
	}
	
	@DataProvider
	public Object[][] getSingleStudentDetails() {
		return EXCEL_Reader.getExcelData("GET_STUDENY_BY_ID");
		
	}
	
	@Test(dataProvider="getSingleStudentDetails", priority=1)
	public void getAllStudentsList(String baseURI, String method, String statusCode, String statusLine, String responseFirstName,
			String responseLastName, String responseEmail, String responseProgram, String responseCources) {
		
		Response response = given().when().get(baseURI);
		response.then().assertThat().statusCode(Integer.parseInt(statusCode));
		response.then().assertThat().statusLine(statusLine);
		System.out.println(response.body().asString());
		response.then().assertThat().body("firstName", equalTo(responseFirstName));
		response.then().assertThat().body("lastName", equalTo(responseLastName));
		response.then().assertThat().body("email", equalTo(responseEmail));
		response.then().assertThat().body("programme", equalTo(responseProgram));
	  	String courseList = response.jsonPath().getString("courses");
		Assert.assertEquals(responseCources, courseList);
	
	}
	
}
