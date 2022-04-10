package com.spotify.oauth2.api;

import static io.restassured.RestAssured.given;
import java.util.Map;
import io.restassured.response.Response;

public class RestResource {
	
	public static Response post(String path, String access_token, Object requestPlaylist) {
		 
		return 
				given(SpecBuilder.getRequestSpec())
	                .body(requestPlaylist) 	
	                .auth().oauth2(access_token). // alternate way passing headers
	               // .header("Authorization", "Bearer " + access_token).
	           when().post(path).	//("/users/317uyxvqw3wz6ovd5s4vl3sfrige/playlists").	      
		       then().spec(SpecBuilder.getResponseSpec())
			         .extract()
			         .response();
	}
	
	public static Response postAccount(Map<String, String> form_urlencodede_HM) { // creating requestSpec
			
		return 
				given(SpecBuilder.getAccountRequestSpec()).formParams(form_urlencodede_HM)	
				//.when().post("/api/token") // Route.API + Route.TOKEN;		
				.when().post(Route.API + Route.TOKEN)			  			  
				.then().spec(SpecBuilder.getResponseSpec())
				.extract()
				.response();	
	}
	
	public static Response get(String access_token , String path) {
		 
		return 
				given(SpecBuilder.getRequestSpec())
				.auth().oauth2(access_token).
			  //.header("Authorization", "Bearer " + access_token).    
	            when().get(path). 		//("/playlists/"+id).	    
		        then().spec(SpecBuilder.getResponseSpec())
			         .extract()
			         .response();
	}
	
	public static Response put_update( String access_token , String path, Object requestPlaylist) {
		 		 
		return	
				given(SpecBuilder.getRequestSpec())			
						 .body(requestPlaylist)
						// .header("Authorization", "Bearer " + access_token).
						 .auth().oauth2(access_token).
				when()
						.put(path). 	//("/playlists/"+ id).						
				then()
						.spec(SpecBuilder.getResponseSpec())
						.extract()
						.response();
	}
}
