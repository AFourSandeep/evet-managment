/**
 * 
 */
package com.afour.emgmt.util;

/**
 * 
 */
public enum ActorEnum {
	
	DEFAULT_USER("SYSTEM");
	
	private String user;

	public String getUser() {
		return this.user;
	}
	
	private ActorEnum(String user) {
        this.user = user;
    }

}
