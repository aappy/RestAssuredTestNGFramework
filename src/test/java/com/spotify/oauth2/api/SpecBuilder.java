package com.spotify.oauth2.api;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class SpecBuilder {
	
	static String access_token = "BQAKtO3brUcAHVGz0VUvnXFL0JU0MKVXdNxyQQbBFlcTOgDnb6vlRNgOqYZWmrSJFrHznkF8fQQlRP0DbfB_scFv-MUFZlCP5F-2AcxgJm2S-V38MXX92iXFgrBCwU-uuQW6ga7lVfltQL0kLV-usqe9xagajmatk7hBbUda9De8-lvMkZoPWIByG1C4XUXeouFLGqw-pOL5LvJB5a6FsrLRnQymwqhNbw0eh0rFrbHGF5S3";
	
	ResponseSpecification responseSpecification;
	RequestSpecification requestSpecification;
	
	public static RequestSpecification getRequestSpec() {
		
			return new RequestSpecBuilder()
					
					
				.setBaseUri(System.getProperty("BASE_URI"))
					
			   // .setBaseUri("https://api.spotify.com")
					
				.setBasePath(Route.BASE_PATH)	
				//.addHeader("Authorization", "Bearer " + access_token)
				.setContentType(ContentType.JSON)
				.log(LogDetail.ALL)
				.build();			
	}	
	
	public static RequestSpecification getAccountRequestSpec() {
		
		return new RequestSpecBuilder()
		
				   .setBaseUri(System.getProperty("ACCOUNT_BASE_URI"))
				//    .setBaseUri("https://accounts.spotify.com")
					.setContentType(ContentType.URLENC)
					.log(LogDetail.ALL)
					.build();		
     }
	
	public static ResponseSpecification getResponseSpec() {
		
		return new ResponseSpecBuilder()
				//.expectContentType(ContentType.JSON)
				.log(LogDetail.ALL)
				.build();	
	}
}
