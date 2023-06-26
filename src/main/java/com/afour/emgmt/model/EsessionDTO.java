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
public class EsessionDTO {
	
	private Integer esessionId;
	
	private String esessionTitle;
	
	private LocalDateTime startAt;
	
	private LocalDateTime endAt;
	
	private EventDTO event;
	
	private String createdBy;
	
	private LocalDateTime createdAt;
	
	private String updatedBy;
	
	private LocalDateTime updatedAt;

}
