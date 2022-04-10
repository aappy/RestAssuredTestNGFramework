package com.spotify.oauth2.tests;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.ResponseSpecification;
import io.restassured.specification.RequestSpecification;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
/*
 * negative TC :
 *  send expired token
 *  send empty name - fields , special char, min value , max value
 *  wrong URI , recourse
 *  
 */
public class PlayListTest {	

	ResponseSpecification responseSpecification;
	RequestSpecification requestSpecification;
	
	JsonPath jsonpath; 
	String id = "";
	
	//String access_token = "BQB_OtgMUMPkfMfFk3cZjV13KibeA5ssSmkWSxZgvG4WcuEYna4ClKyje1xfKiy6nxkSNo75KfxttvTcscMwDJsXLIaXtp6XHyM65kCPKrV6HZuckgjsjAoX9TCp5YFEyhl6rcpNuJzvjuO0VCED2nglPGMN3n6Nj3CT";
	
	@BeforeClass
	public void beforeclass(){
	
		RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder()
				.setBaseUri("https://api.spotify.com")
				.setBasePath("/v1")	
				.addHeader("Authorization", "Bearer " + "BQCHzpqteBS5GPVHiVqJ-Vgl_3aDw3bxaNJvhgl3ErCqHYTTXe2LXkU0mi7ieWCH_c0C9hOsK7S5DxsQeHoxmyJn5-JcHUYdaL_k2RMef1o4CGlA6yZy4w9GJswuRXGKAyjOmzKbMJ0UBxPeRveJQExrqq421XhHoUCrNSQpdBDlb_2YsrmkuhMUXM0feBQuJDjwpp4caduN5tF_gyszCwkgOfDqE_Tl0yz9WLPqXPI9XUJu")
				.setContentType(ContentType.JSON)
				.log(LogDetail.ALL);			
		requestSpecification = requestSpecBuilder.build();

		ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder()
				//.expectContentType(ContentType.JSON)
				.log(LogDetail.ALL);
		responseSpecification = responseSpecBuilder.build();
	}	

//CREATE PLAYLIST	
	@Test
	public void CreateAPlayList(){
			
		String payload = "{\n"
				+ "  \"name\": \"Yohan\",\n"
				+ "  \"description\": \"Yohan description\",\n"
				+ "  \"public\": false\n"
				+ "}";
	Response res =	given(requestSpecification)
					.body(payload).
		when()
				.post("/users/317uyxvqw3wz6ovd5s4vl3sfrige/playlists").	  
		then().spec(responseSpecification)
						.assertThat()
						.statusCode(201)
						.body("name" , equalTo("Yohan"),										// *******
							  "description" , equalTo("Yohan description"),					// *******
							  "public" , equalTo(false)
							 ).extract().response();
		
		 jsonpath = new JsonPath(res.asString());
	 //	 Request_id = jsonpath.getString("owner.id");
	 //	 System.out.println("created request id *****" + Request_id);
			 id = jsonpath.getString("id");
			System.out.println("id = ************************************* " + id);
	}
	
//GET PLAYLIST	
	
	@Test
	public void GetAPlayList(){
		
	Response response = given(requestSpecification).
						when().get("/playlists/"+id).		//users/{user_id}/playlists	
						then().spec(responseSpecification).assertThat().statusCode(200)
							  .body("name" , equalTo("Yohan"),                                 // *******
				                    "description" , equalTo("Yohan description"), 		      // *******
				                    "public" , equalTo(false)
				                   ).extract().response();
	}

//UPDATE PLAYLIST
	
	@Test
	public void PutAPlayList(){
	
	System.out.println("created request id *****" + id);
	
	String payloadPUT = "{\n"
			+ "  \"name\": \"Arin\",\n"
			+ "  \"description\": \"Arin description\",\n"
			+ "  \"public\": false\n"
			+ "}";
	
	Response response = given(requestSpecification).
							
						body(payloadPUT).							
						when().put("/playlists/"+id).						
						then().spec(responseSpecification).assertThat().statusCode(200)
							  .extract().response();
	}
	
//NEGATIVE TEST CASES

	@Test
	public void Negative_CreateAPlayList_with_ExpiredToken(){
			
		String expiredToken = "sdfsdfsfsfs";
		
		String payload = "{\n"
				+ "  \"name\": \"ASASAS\",\n"
				+ "  \"description\": \"ASASAS description\",\n"
				+ "  \"public\": false\n"
				+ "}";	
		
	    given()
	    
			.baseUri("https://api.spotify.com")
			.basePath("/v1")	
			.header("Authorization", "Bearer " + expiredToken)
			.log().all()			
			.body(payload).
			
		when()
				.post("/users/317uyxvqw3wz6ovd5s4vl3sfrige/playlists").	  
		then().spec(responseSpecification)
						.assertThat()
						.statusCode(401)
						.body("error.status" , equalTo(401),										
							  "error.message" , equalTo("Invalid access token")					
							 );

/* Body:
{
    "name": "",
    "description": "ASASAS description",
    "public": false
}
*/
  	 }
	
	@Test
	public void Negative_CreateAPlayList_with_nuppVAluue(){
	
		String payload = "{\n"
				+ "  \"name\": \"\",\n"
				+ "  \"description\": \"ASASAS description\",\n"
				+ "  \"public\": false\n"
				+ "}";
		
		given(requestSpecification)
					.body(payload).
		when()
				.post("/users/317uyxvqw3wz6ovd5s4vl3sfrige/playlists").	  
		then().spec(responseSpecification)
						.assertThat()
						.statusCode(400)
						.body("error.status" , equalTo(400),										
							  "error.message" , equalTo("Missing required field: name")					
							 );
/*
{
"error":{
        "status": 400,
        "message": "Missing required field: name"
    	}
}
*/	
	}
}
