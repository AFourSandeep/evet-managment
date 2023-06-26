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
	
	OrganizerDTO entityToDTO(Organizer entity);
	
	Organizer DTOToEntity(OrganizerDTO dto);
	
	List<OrganizerDTO> entityToDTO(List<Organizer> entities);
	
	List<Organizer> DTOToEntity(List<OrganizerDTO> dtos);

	Organizer prepareForUpdate(Organizer entity, OrganizerDTO orgDTO);

}
