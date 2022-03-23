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

public class OAuth2_AuthenticationTest {
	static String access_token;
	static String userId;
	static String clientID="CyPressApp";
	static String secret = "f0590fba402263485300ed0b4612217d";
	

	@Test(priority=0)
	public void getAccessToken() {
		
		Response response = 
		given().auth().preemptive()
		.basic(clientID, secret)
		.param("grant_type", "client_credentials")
		.log().all()
		.post("http://coop.apps.symfonycasts.com/token");

		//response.prettyPrint();
		System.out.println("Status Code is: "+response.statusCode());
		Assert.assertEquals(response.getStatusCode(), 200);
		access_token = response.getBody().path("access_token");
		System.out.println("Access Token is: "+access_token);
	
	}
	
	@Test(priority=1, dependsOnMethods="getAccessToken")
	public void getUserID() {
		
		Response response = given().contentType(ContentType.JSON)
						   .auth().oauth2(access_token).when().get("http://coop.apps.symfonycasts.com/api/me");
			
		Assert.assertEquals(response.getStatusCode(), 200);
		userId = response.getBody().path("id");
		System.out.println("User ID is: "+userId);
		
	}

	@Test(priority=1, dependsOnMethods="getUserID")
	public void verifyPostMethod() {
		String post_URL="http://coop.apps.symfonycasts.com/api/"+userId+"/chickens-feed";
		Response response = given().contentType(ContentType.JSON)
				   .auth().oauth2(access_token).when().post(post_URL);
		
		Assert.assertEquals(response.getStatusCode(), 200);
		response.then().assertThat().body("action", equalTo("chickens-feed"));
		response.then().assertThat().body("success", equalTo(true));
		response.then().assertThat().body("message", equalTo("Your chickens are now full and happy"));
		System.out.println(response.asString());
	}
	
}
