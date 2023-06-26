/**
 * 
 */
package com.afour.emgmt.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 
 */
public class UtilMain {

	public static void main(String[] args) {
		
		LocalDateTime now = LocalDateTime.now();
		System.out.println(now);
		
		final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

		LocalDateTime dateTime = LocalDateTime.parse("2019-03-27 10:15:30", formatter);
		System.out.println(dateTime);
		
		
		
	}

}
