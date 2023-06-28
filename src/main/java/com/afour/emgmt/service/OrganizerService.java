/**
 * 
 */
package com.afour.emgmt.service;

import java.util.List;

import com.afour.emgmt.model.OrganizerDTO;

/**
 * 
 */
public interface OrganizerService {
	
	public List<OrganizerDTO> fetchAllOrganizers();

	public OrganizerDTO addOrganizer(OrganizerDTO orgDTO);

	public OrganizerDTO findOrganizerByID(final Integer ID);

	public OrganizerDTO findOrganizerByUserName(final String USERNAME);

	public OrganizerDTO updateOrganizer(OrganizerDTO orgDTO);

	public Boolean deleteOrganizerByID(Integer iD);

}
