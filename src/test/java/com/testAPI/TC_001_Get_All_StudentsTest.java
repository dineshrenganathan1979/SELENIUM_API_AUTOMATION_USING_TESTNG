package com.testAPI;

import com.util.EXCEL_Reader;

import static io.restassured.RestAssured.*;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import io.restassured.RestAssured;
import io.restassured.response.Response;

public class TC_001_Get_All_StudentsTest {
	
	@BeforeClass
	public void initializeAPI() {
		//java -jar Student_App.jar --server.port=8888
 		RestAssured.baseURI="http://localhost:8888";
 		RestAssured.basePath="/student/list";
		
	}
	@DataProvider
	public Object[][] getAllStudentDetails() {
		return EXCEL_Reader.getExcelData("GET_ALL_STUDENT");
		
	}
	
	@Test(dataProvider="getAllStudentDetails", priority=1)
	public void getAllStudentsList(String baseURI, String method, String statusCode, String statusLine, String responseBody ) {
		
		Response response = given().when().get(baseURI);
	    response.then().assertThat().statusCode(Integer.parseInt(statusCode));
		response.then().assertThat().statusLine(statusLine);
		response.getBody().asString().contains(responseBody);
		System.out.println(response.asString());
	}
		

}
