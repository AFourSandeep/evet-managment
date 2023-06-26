/**
 * 
 */
package com.afour.emgmt.model;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventDTO {
	
	private Integer eventId;
	
	private String eventName;
	
	private LocalDateTime startAt;
	
	private LocalDateTime endAt;
	
	private OrganizerDTO owner;
	
	private Boolean isClosed;
	
	private String createdBy;
	
	private LocalDateTime createdAt;
	
	private String updatedBy;
	
	private LocalDateTime updatedAt;
	
	private List<EsessionDTO> sessions;
	
	private List<VisitorDTO> visitors;

}
