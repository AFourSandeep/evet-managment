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
	
	PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	
	public String encoder(String input) {
		return passwordEncoder.encode(input);
	}

	public static void main(String[] args) {
		
		UtilMain um = new UtilMain();
		
		/*
		 * LocalDateTime now = LocalDateTime.now(); System.out.println(now);
		 * 
		 * final DateTimeFormatter formatter =
		 * DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		 * 
		 * LocalDateTime dateTime = LocalDateTime.parse("2019-03-27 10:15:30",
		 * formatter); System.out.println(dateTime);
		 */
		String pwd  = "AFOUR";
		
		System.out.println("pwd: "+ um.encoder(pwd));
		
		
		System.out.println(RoleEnum.ORGANIZER+":"+RoleEnum.ORGANIZER.getRollId());
		
		System.out.println(RoleEnum.VISITOR+":"+RoleEnum.VISITOR.getRollId());
		
		System.out.println(ActorEnum.DEFAULT_USER+":"+ActorEnum.DEFAULT_USER.name()+":"+ActorEnum.DEFAULT_USER.getUser());
		
	}

}
