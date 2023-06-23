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
public class EsessionDTO {
	
	private Integer esessionId;
	
	private String esessionTitle;
	
	private Timestamp startAt;
	
	private Timestamp endAt;
	
	private EventDTO event;
	
	private String createdBy;
	
	private Timestamp createdAt;
	
	private String updatedBy;
	
	private Timestamp updatedAt;

}
