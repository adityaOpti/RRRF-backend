package com.probity.echawdi.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class EncryptUtils {

	public static String encrypt(String password) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder.encode(password);
	}
	
	public static boolean match(String password, String encodedPassword) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder.matches(password, encodedPassword); 
	}

	public static String matcher(String password, String encodedPassword) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		if(encoder.matches(password, encodedPassword)){
			return password;
		}else {
			return "linkage_emuroll";
		}
	}
}