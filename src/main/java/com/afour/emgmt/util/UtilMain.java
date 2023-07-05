/**
 * 
 */
package com.afour.emgmt.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 
 */
public class UtilMain {
	
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	public String encode(String input) {
		return passwordEncoder().encode(input);
	}
	
	public static void main(String args[]) {
		UtilMain um = new UtilMain();
		String given = "Password";
		System.out.println(given+" and its  Encoded value: \n"+ um.encode(given));
	}

}
