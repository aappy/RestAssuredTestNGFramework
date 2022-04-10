package com.spotify.oauth2.api.applicationApi;

import com.spotify.oauth2.api.RestResource;
import com.spotify.oauth2.api.Route;
import com.spotify.oauth2.api.TokenManager;
import com.spotify.oauth2.pojo.PLaylist;
import com.spotify.oauth2.utils.ConfigLoader;

import io.restassured.response.Response;

public class PlaylistApi {

	//static String access_token = "BQAV9QSgf4PZrLR2mgfPlMdIJKLRyrjuwce2i5bXOonJ4IhHJcPOhb8_nEWBOltgIhajyTAjkN-hdNNm68lQkEimeNlJhPIDNa41anTzY3KtnCxMHUnqrq45QNy-ZvUURPBj3rpe0UzChVmBDklYeaMJ6GwPsTB3mpfeYaqpjiZiCJpJqA9l6_PmnkOA43Wl6YxAGLC0ujLzz-otCvZlCQqD4YBxcmi7ZeJHiNvnEvp7-Ihp";
	
	public static Response post(PLaylist requestPlaylist) {					 
		return RestResource.post(Route.USERS +"/" + ConfigLoader.getInstance().get_user_id() + Route.PLAYLISTS , TokenManager.getToken() , requestPlaylist );
	}

//invalid token post	
	public static Response post(String token , PLaylist requestPlaylist) {		
		return RestResource.post(Route.USERS +"/" + ConfigLoader.getInstance().get_user_id() + Route.PLAYLISTS , token , requestPlaylist );
	}
	
	public static Response get(String id) {		 
		String path = Route.PLAYLISTS +"/"+ id;	//Request URI:	https://api.spotify.com/v1/playlists2bEWkUT9cPNlwonsKK63mF
		return RestResource.get(TokenManager.getToken() , path);
	}
	
	public static Response put_update( String id , PLaylist requestPlaylist) {	 		 
		String path = Route.PLAYLISTS +"/"+ id;
		return RestResource.put_update(TokenManager.getToken() ,path ,  requestPlaylist);	
	}
}
