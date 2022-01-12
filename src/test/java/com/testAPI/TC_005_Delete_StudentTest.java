package com.testAPI;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;

import com.util.EXCEL_Reader;

import io.restassured.response.Response;

public class TC_005_Delete_StudentTest {
	
	@DataProvider
	public Object[][] getDeleteDataFromExcel() {
		return EXCEL_Reader.getExcelData("DELETE_STUDENT");
	}
	
	@Test(dataProvider ="getDeleteDataFromExcel", priority=1)
	public void deleteStudentTest(String baseURI, String method, String statusCode, String statusLine) {
		Response response = given().when().delete(baseURI);
		
		//System.out.println(response.statusLine());
		response.then().assertThat().statusCode(Integer.parseInt(statusCode));
		response.then().assertThat().statusLine(statusLine);
		System.out.println(response.asString());
	}

}
