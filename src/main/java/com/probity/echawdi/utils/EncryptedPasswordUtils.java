package com.probity.echawdi.utils;

import org.springframework.security.crypto.password.MessageDigestPasswordEncoder;

/**
 * @author Bhagyashri Lolage
 *
 */
@SuppressWarnings("deprecation")
public class EncryptedPasswordUtils{

	public static String encryptPassword(String password) {
		MessageDigestPasswordEncoder encoder=new MessageDigestPasswordEncoder("MD5");
		return encoder.encode(password);
	}

	public static boolean matchPassword(String password, String encryptedPassword) {
		MessageDigestPasswordEncoder encoder=new MessageDigestPasswordEncoder("MD5");
		if(encoder.matches(password, encryptedPassword)) {
			return true;
		}
		else {
			return false;
		}
	}
}
