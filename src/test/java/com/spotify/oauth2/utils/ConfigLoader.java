package com.spotify.oauth2.utils;
import java.util.Properties;
public class ConfigLoader {

	private static Properties properties = new Properties();
	private static ConfigLoader configLoader;
	
	private ConfigLoader() {
		properties = PropertyUtils.propertyLoader("/Users/aparnachdhry/Rest_eclipseWorkplace/RestAssured_proj_om_Framework_2022/resources/config.properties");
	}
	
	public static ConfigLoader getInstance() {
		if(configLoader == null) {
			configLoader = new ConfigLoader(); // only one/single object is created and return same obj again and again
		}
		return configLoader;
	}
//now create the public methods to return the individual properties
	
	public String get_client_id() {
		
		String prop = properties.getProperty("client_id");
		if(prop != null) return prop;
		else throw new RuntimeException("property client_id is not specified in the config.properties file ");
	}
	
    public String get_client_secret() {
		
		String prop = properties.getProperty("client_secret");
		if(prop != null) return prop;
		else throw new RuntimeException("property client_secret is not specified in the config.properties file ");
	}
    
    public String get_user_id() {
		
  		String prop = properties.getProperty("user_id");
  		if(prop != null) return prop;
  		else throw new RuntimeException("property user_id is not specified in the config.properties file ");
  	}
   
    public String get_refresh_token() {
		
  		String prop = properties.getProperty("refresh_token");
  		if(prop != null) return prop;
  		else throw new RuntimeException("property refresh_token is not specified in the config.properties file ");
  	}    
    public String get_grant_type() {		
  		String prop = properties.getProperty("grant_type");
  		if(prop != null) return prop;
  		else throw new RuntimeException("property grant_type is not specified in the config.properties file ");
  	}
}