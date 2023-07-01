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
