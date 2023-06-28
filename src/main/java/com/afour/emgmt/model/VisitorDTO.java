/**
 * 
 */
package com.afour.emgmt.model;

import java.time.LocalDateTime;
import java.util.Set;

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
	
	private Boolean isActive;
	
	private RoleDTO role;
	
	private String createdBy;
	
	private LocalDateTime createdAt;
	
	private String updatedBy;
	
	private LocalDateTime updatedAt;
	
	private Set<EventDTO> eventDtos;
}
