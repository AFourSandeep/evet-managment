/**
 * 
 */
package com.afour.emgmt.model;

import java.time.LocalDateTime;
import java.util.Set;

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
public class EventDTO {
	
	private Integer eventId;
	
	private String eventName;
	
	private LocalDateTime startAt;
	
	private LocalDateTime endAt;
	
	private UserDTO owner;
	
	private String location;
	
	private Boolean isClosed;
	
	private String createdBy;
	
	private LocalDateTime createdAt;
	
	private String updatedBy;
	
	private LocalDateTime updatedAt;
	
	private Set<EsessionDTO> sessions;
	
	private Set<UserDTO> visitors;

}
