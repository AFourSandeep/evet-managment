/**
 * 
 */
package com.afour.emgmt.model;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrganizerDTO {
	
	private Integer organizerId;
	
	private String userName;
	
	private String firstName;
	
	private String lastName;
	
	private String password;
	
	private boolean isActive;
	
	private RoleDTO role;

	private String created_By;
	
	private Timestamp createdAt;
	
	private String updated_By;
	
	private Timestamp updatedAt;
	

}
