/**
 * 
 */
package com.afour.emgmt.model;

import java.time.LocalDateTime;

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
	
	private Boolean isActive;
	
	private RoleDTO role;

	private String createdBy;
	
	private LocalDateTime createdAt;
	
	private String updatedBy;
	
	private LocalDateTime updatedAt;
	

}
