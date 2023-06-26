/**
 * 
 */
package com.afour.emgmt.model;

import java.sql.Timestamp;
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
	
	private Timestamp startAt;
	
	private Timestamp endAt;
	
	private OrganizerDTO owner;
	
	private String createdBy;
	
	private Timestamp createdAt;
	
	private String updatedBy;
	
	private Timestamp updatedAt;
	
	private List<EsessionDTO> sessions;

}
