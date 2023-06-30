/**
 * 
 */
package com.afour.emgmt.util;

/**
 * 
 */
public enum RoleEnum {
	
	ORGANIZER(1),
	VISITOR(2);
	
	private Integer rollId;

	public Integer getRollId() {
		return this.rollId;
	}
	
	private RoleEnum(int value) {
        this.rollId = value;
    }
}
