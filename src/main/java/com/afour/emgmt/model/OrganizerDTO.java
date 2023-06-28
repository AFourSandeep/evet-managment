/**
 * 
 */
package com.afour.emgmt.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
