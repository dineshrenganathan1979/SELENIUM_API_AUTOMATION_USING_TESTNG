package com.util;

import org.apache.commons.lang3.RandomStringUtils;

public class RestUtils {

	public static String getEmployeeName() {
		
		String generatedString= RandomStringUtils.randomAlphabetic(3);
		return ("Arun_"+generatedString);
	}
	
	public static int getStudentID() {
		return Integer.parseInt(RandomStringUtils.randomNumeric(2));
	}
	public static String getEmployeeSalary() {
		
		String generatedNumber= RandomStringUtils.randomNumeric(5);
		return (generatedNumber);
	}
	
	public static String getEmployeeAge() {
		
		String generatedNumber= RandomStringUtils.randomNumeric(2);
		return (generatedNumber);
	}
	
	
	public static String getStudentFirstName() {
		
		String generatedString= RandomStringUtils.randomAlphabetic(2);
		return ("Arun_"+generatedString);
	}
	
	public static String getStudentLastName() {
		
		String generatedString= RandomStringUtils.randomAlphabetic(2);
		return ("Kumar_"+generatedString);
	}
	
	public static String getStudentProgramme() {
		
		String generatedString= RandomStringUtils.randomNumeric(2);
		return ("Computer Science Syllabus_"+generatedString);
	}
	
	public static String getStudentEmail() {
		String generatedString= RandomStringUtils.randomAlphanumeric(3);
		return ("arun_"+generatedString+"@gmail.com");
	}
	
	
	
}
