/**
 * 
 */
package com.afour.emgmt.service;

import java.util.List;

import com.afour.emgmt.model.UserDTO;

/**
 * 
 */
public interface OrganizerService {
	
	List<UserDTO> fetchAllOrganizers();

	UserDTO addOrganizer(UserDTO orgDTO) ;

	UserDTO findOrganizerByID(final Integer ID);

	UserDTO findOrganizerByUserName(final String USERNAME);

	UserDTO updateOrganizer(UserDTO orgDTO);

	boolean deleteOrganizerByID(Integer iD);

}
