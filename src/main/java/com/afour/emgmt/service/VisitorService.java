/**
 * 
 */
package com.afour.emgmt.service;

import java.util.List;

import com.afour.emgmt.exception.NoDataFoundException;
import com.afour.emgmt.exception.UserAlreadyExistException;
import com.afour.emgmt.model.UserDTO;
import com.afour.emgmt.model.UserRegistrationDTO;

/**
 * 
 */
public interface VisitorService {

	List<UserDTO> fetchAllVisitors() throws NoDataFoundException;

	UserDTO findVisitorByID(Integer ID) throws NoDataFoundException;

	UserDTO findVisitorByUserName(String USERNAME) throws NoDataFoundException;

	UserDTO addVisitor(UserDTO orgDTO) throws UserAlreadyExistException;

	UserDTO updateVisitor(UserDTO dto) throws NoDataFoundException;

	Boolean deleteVisitorByID(Integer ID) throws NoDataFoundException;

	UserDTO registerVisitorForEvent(UserRegistrationDTO dto) throws NoDataFoundException;

}
