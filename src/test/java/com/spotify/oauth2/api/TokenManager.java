package com.spotify.oauth2.api;
import java.time.Instant;
import java.util.HashMap;
import com.spotify.oauth2.utils.ConfigLoader;
import io.restassured.response.Response;

public class TokenManager {		
	private static String access_token;
	private static Instant expiry_time;
	
	public static String getToken() {			
		try {
			if(access_token == null || Instant.now().isAfter(expiry_time)) {
				
				System.out.println("Renewing token ...");
				Response response = renewToken();
				access_token = response.path("access_token");
				int expiry_duration_In_Seconds = response.path("expires_in");
				expiry_time = Instant.now().plusSeconds(expiry_duration_In_Seconds - 300); // substract 5 min from expiry time
			
			   }else {
					System.out.println("Token is good to use");				
			   }
			}	
		catch(Exception e) {
			
			throw new RuntimeException("ABORT!!! Failed to get Token");	  
		}		
		return access_token;
	}

	private static Response renewToken() {
		
	 HashMap<String , String> form_urlencodede_HM = new HashMap();
		
		form_urlencodede_HM.put("grant_type", ConfigLoader.getInstance().get_grant_type());
		form_urlencodede_HM.put("refresh_token", ConfigLoader.getInstance().get_refresh_token());
		form_urlencodede_HM.put("client_id", ConfigLoader.getInstance().get_client_id());
		form_urlencodede_HM.put("client_secret", ConfigLoader.getInstance().get_client_secret());
	
	 Response response = RestResource.postAccount(form_urlencodede_HM);
	
	 if(response.statusCode() != 200) {		
		throw new RuntimeException("ABORT!!! Renew Token failed");	  
	  }	
	   return response;
	}
}







