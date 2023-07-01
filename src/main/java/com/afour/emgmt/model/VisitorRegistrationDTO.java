/**
 * 
 */
package com.afour.emgmt.model;

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
public class VisitorRegistrationDTO {
	
	private Integer visitorId;
	
	private Set<Integer> eventIds;

}
