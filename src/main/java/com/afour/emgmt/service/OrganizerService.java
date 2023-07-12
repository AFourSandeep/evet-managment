/**
 * 
 */
package com.afour.emgmt.service;

import java.util.List;

import com.afour.emgmt.exception.NoDataFoundException;
import com.afour.emgmt.exception.UndefinedRoleException;
import com.afour.emgmt.exception.UserAlreadyExistException;
import com.afour.emgmt.model.UserDTO;

/**
 * 
 */
public interface OrganizerService {
	
	public List<UserDTO> fetchAllOrganizers();

	public UserDTO addOrganizer(UserDTO orgDTO) throws UserAlreadyExistException, UndefinedRoleException;

	public UserDTO findOrganizerByID(final Integer ID) throws NoDataFoundException;

	public UserDTO findOrganizerByUserName(final String USERNAME) throws NoDataFoundException;

	public UserDTO updateOrganizer(UserDTO orgDTO) throws NoDataFoundException;

	public Boolean deleteOrganizerByID(Integer iD) throws NoDataFoundException;

}
