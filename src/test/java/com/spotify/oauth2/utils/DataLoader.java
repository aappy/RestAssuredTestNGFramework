package com.spotify.oauth2.utils;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class DataLoader {

	private static Properties properties = new Properties();
	private static DataLoader dataLoader;
	
	private DataLoader() {
		properties = PropertyUtils.propertyLoader("/Users/aparnachdhry/Rest_eclipseWorkplace/RestAssured_proj_om_Framework_2022/resources/data.properties");
	}
	
	public static DataLoader getInstance() {
		if(dataLoader == null) {
			dataLoader = new DataLoader(); // only one/single object is created and return same obj again and again
		}
		return dataLoader;
	}
//now create the public methods to return the individual properties
	
	public String getPlayList_id() throws IOException {
		
		
		String prop = properties.getProperty("get_playList_id");
		if(prop != null) return prop;
		else throw new RuntimeException("property get_playList_id is not specified in the config.properties file ");
	}
	
	public void set_PlayList_id(String id) throws FileNotFoundException, IOException {
		
		properties.setProperty("get_playList_id", id);		
		properties.store(new FileOutputStream("/Users/aparnachdhry/Rest_eclipseWorkplace/RestAssured_proj_om_Framework_2022/resources/data.properties"), null);

	}
	
    public String updatePlayList_id() {
		
		String prop = properties.getProperty("update_playList_id");
		if(prop != null) return prop;
		else throw new RuntimeException("property update_playList_id is not specified in the config.properties file ");
	}
}