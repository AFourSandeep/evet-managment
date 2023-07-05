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
	
	public List<UserDTO> fetchAllOrganizers();

	public UserDTO addOrganizer(UserDTO orgDTO);

	public UserDTO findOrganizerByID(final Integer ID);

	public UserDTO findOrganizerByUserName(final String USERNAME);

	public UserDTO updateOrganizer(UserDTO orgDTO);

	public Boolean deleteOrganizerByID(Integer iD);

}
