package com.spotify.oauth2.utils;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.io.FileReader;
import java.io.FileWriter;

public class PropertyUtils {
	
	public static Properties propertyLoader(String filePath) {
		
		Properties properties = new Properties();
		BufferedReader reader;
		
		try {
			reader = new BufferedReader(new FileReader(filePath));
		try {
			properties.load(reader);
			reader.close();
		}catch(IOException e){
			e.printStackTrace();
			throw new RuntimeException("Failed to load Properties file "+filePath);
		}
		}catch(FileNotFoundException e) {
			e.printStackTrace();
			throw new RuntimeException("Properties to load Properties file "+filePath);
		}
		return properties;
	}
}
