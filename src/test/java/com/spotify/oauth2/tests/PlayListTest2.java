package com.spotify.oauth2.tests;

import org.testng.annotations.Test;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import static org.hamcrest.Matchers.equalTo;
import java.io.IOException;
import static org.hamcrest.MatcherAssert.assertThat;
import com.spotify.oauth2.pojo.PLaylist;
import com.spotify.oauth2.utils.DataLoader;
import com.spotify.oauth2.utils.FakersUtils;
import com.spotify.oauth2.api.applicationApi.PlaylistApi;
import com.spotify.oauth2.pojo.Error;

public class PlayListTest2 {		
//	ResponseSpecification responseSpecification;
//	RequestSpecification requestSpecification;

//	@BeforeClass
//	public void beforeclass(){
//	
//		RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder()
//				.setBaseUri("https://api.spotify.com")
//				.setBasePath("/v1")	
//				.addHeader("Authorization", "Bearer " + "BQCHzpqteBS5GPVHiVqJ-Vgl_3aDw3bxaNJvhgl3ErCqHYTTXe2LXkU0mi7ieWCH_c0C9hOsK7S5DxsQeHoxmyJn5-JcHUYdaL_k2RMef1o4CGlA6yZy4w9GJswuRXGKAyjOmzKbMJ0UBxPeRveJQExrqq421XhHoUCrNSQpdBDlb_2YsrmkuhMUXM0feBQuJDjwpp4caduN5tF_gyszCwkgOfDqE_Tl0yz9WLPqXPI9XUJu")
//				.setContentType(ContentType.JSON)
//				.log(LogDetail.ALL);			
//		
//		requestSpecification = requestSpecBuilder.build();
//
//		ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder()
//				//.expectContentType(ContentType.JSON)
//				.log(LogDetail.ALL);
//		responseSpecification = responseSpecBuilder.build();
//	}	

	
	JsonPath jsonpath; 
	String id = "";
	
	public PLaylist playlistBuilder(String name , String description, boolean Public) {
		
		return new PLaylist() // we are repeating this code 
							.setName(name)
							.setDescription(description)
							.setPublic(Public);	
	  }
	
	public void assertPlayListEqual(PLaylist responsePLaylist , PLaylist requestPlaylist) {
		
		assertThat(responsePLaylist.getName() , equalTo(requestPlaylist.getName())); // doing assertion using java objects		
		assertThat(responsePLaylist.getDescription() , equalTo(requestPlaylist.getDescription()));
		assertThat(responsePLaylist.getPublic() , equalTo(requestPlaylist.getPublic()));
	}
	
	public void assertStatusCodeMethod(int actualStatusCode , int expectedStatusCode) {
		assertThat(actualStatusCode, equalTo(expectedStatusCode));
	}
	
	public void assertErrorMethod(Error responseErr, int expectedStatusCode, String expectedmsg) {
	
		    assertThat(responseErr.getError().getStatus(), equalTo(expectedStatusCode)); 		
			assertThat(responseErr.getError().getMessage(), equalTo(expectedmsg)); 	
	}
	
//CREATE PLAYLIST POGO	
	@Test
	public void CreateAPlayList(){

		//PLaylist requestPlaylist = playlistBuilder("New PlayList" ,"New playlist description", false);

		PLaylist requestPlaylist = playlistBuilder(FakersUtils.generateName() , FakersUtils.generateDescription(), false); //******************
		
		
//		PLaylist requestPlaylist = new PLaylist() // we are repeating this code 
//				.setName("New PlayList")
//				.setDescription("New playlist description")
//				.setPublic(false);
		
		Response response  = PlaylistApi.post(requestPlaylist);		
		
		assertStatusCodeMethod(response.statusCode() , 201);
		
		//assertThat(response.statusCode(), equalTo(201));		
	   
		PLaylist responsePLaylist = response.as(PLaylist.class);	
	    assertPlayListEqual(responsePLaylist , requestPlaylist);
		
//***** set id value to properties file			
		id = responsePLaylist.getId();		
		try {			
			DataLoader.getInstance().set_PlayList_id(id);			
			System.out.println("SETTING UP ID  ********************" +  DataLoader.getInstance().getPlayList_id());
		}
		catch(Exception e) {
			throw new RuntimeException("********* PROPERTY NOT SET *********"); 
		}		
		//System.out.println("responsePLaylist ********************" + responsePLaylist.getId());		
	}
	
//GET PLAYLIST	POGO	
		@Test
		public void GetAPlayList() throws IOException{
			
			//PLaylist requestPlaylist = playlistBuilder("New PlayList" ,"New playlist description", false);

		PLaylist requestPlaylist = playlistBuilder("UPDATED PlayList" ,"UPDATED playlist description", false);

			
			System.out.println(" GET = DataLoader.getInstance().getPlayList_id() **********" + DataLoader.getInstance().getPlayList_id());
			
			Response response  = PlaylistApi.get(DataLoader.getInstance().getPlayList_id());		
			
			assertThat(response.statusCode(), equalTo(200));			
		   
			PLaylist responsePLaylist = response.as(PLaylist.class); // deserialization
			
			
			assertPlayListEqual(responsePLaylist, requestPlaylist);
			
			//assertThat(responsePLaylist.getName() , equalTo(requestPlaylist.getName())); // doing assertion using java objects		
			//assertThat(responsePLaylist.getDescription() , equalTo(requestPlaylist.getDescription()));
			//assertThat(responsePLaylist.getPublic() , equalTo(requestPlaylist.getPublic()));
		}
		
//UPDATE PLAYLIST POGO
		@Test
		public void PutAPlayList() throws IOException{
		
			//PLaylist requestPlaylist = playlistBuilder("UPDATED PlayList" ,"\"UPDATED playlist description\"", false);
			
			PLaylist requestPlaylist = playlistBuilder(FakersUtils.generateName() ,FakersUtils.generateDescription(), false);
		
			//PLaylist requestPlaylist = playlistBuilder(name , description, false);

			
//****		
			System.out.println(" UPDATE = DataLoader.getInstance().getPlayList_id() **********" + DataLoader.getInstance().getPlayList_id());
		
			Response response = PlaylistApi.put_update(DataLoader.getInstance().getPlayList_id(), requestPlaylist);			
			assertThat(response.statusCode(), equalTo(200));				
		}
		
//NEGATIVE TEST CASES	
 @Test
 public void Negative_CreateAPlayList_with_nullName(){		
			
		//PLaylist requestPlaylist = playlistBuilder("" ,"New playlist description", false);
	 
//		PLaylist requestPlaylist = new PLaylist() // we are repeating this code 
//		.setName("")
//		.setDescription("New playlist description")
//		.setPublic(false);

		PLaylist requestPlaylist = playlistBuilder("" ,FakersUtils.generateDescription(), false);
		
		  Response response  = PlaylistApi.post(requestPlaylist);		
			
			assertThat(response.statusCode(), equalTo(400));	
			
			//Error error = response.as(Error.class);	
			
			assertErrorMethod(response.as(Error.class) , 400, "Missing required field: name");
				
	/*
	* Body:
	{
	    "name": "",
	    "description": "ASASAS description",
	    "public": false
	}
	*/
	  	 }

	@Test
	public void Negative_CreateAPlayList_with_ExpiredToken(){
					
	//	PLaylist requestPlaylist = playlistBuilder("New PlayList" ,"New playlist description", false);	
		
		PLaylist requestPlaylist = playlistBuilder(FakersUtils.generateName() ,FakersUtils.generateDescription(), false);	//ramdom stirng gerate
			
			String expiredToken = "sdfsdfsfsfs";			
			Response response  = PlaylistApi.post(expiredToken , requestPlaylist);
			
			assertThat(response.statusCode(), equalTo(401));					    
			Error error = response.as(Error.class);				
			assertThat(error.getError().getStatus(), equalTo(401)); 		
			assertThat(error.getError().getMessage(), equalTo("Invalid access token")); 			
	/*
	{
       "error": {
        	"status": 401,
        	"message": "Invalid access token"
      }
    }
	*/
	  	 }
}
