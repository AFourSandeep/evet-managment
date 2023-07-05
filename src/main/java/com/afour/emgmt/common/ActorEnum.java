/**
 * 
 */
package com.afour.emgmt.common;

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
