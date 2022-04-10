package com.spotify.oauth2.utils;
import com.github.javafaker.Faker;

public class FakersUtils {

	public static String generateName() {
		
		Faker faker = new Faker();
		faker.name();
		return "Playlist" + faker.regexify("[A-Za-z0-9 ,_-]{10}");
	}
	
   public static String generateDescription() {
		
		Faker faker = new Faker();
		faker.name();
		return "Description" + faker.regexify("[ A-Za-z0-9_@./#&+-]{60}");
	
   }
}
