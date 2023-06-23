/**
 * 
 */
package com.afour.emgmt.mapper;

import java.util.List;

import com.afour.emgmt.entity.Organizer;
import com.afour.emgmt.model.OrganizerDTO;

/**
 * 
 */
public interface OrganizerMapper {
	
	OrganizerDTO entityToDTO(Organizer organizer);
	
	Organizer DTOToEntity(OrganizerDTO organizer);
	
	List<OrganizerDTO> entityToDTO(List<Organizer> organizers);
	
	List<Organizer> DTOToEntity(List<OrganizerDTO> organizer);

}
