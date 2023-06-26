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
public class VisitorDTO {
	
	private Integer visitorId;
	
	private String userName;
	
	private String firstName;
	
	private String lastName;
	
	private String password;
	
	private boolean isActive;
	
	private RoleDTO role;
	
	private String createdBy;
	
	private Timestamp createdAt;
	
	private String updatedBy;
	
	private Timestamp updatedAt;
}
