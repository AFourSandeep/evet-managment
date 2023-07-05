/**
 * 
 */
package com.afour.emgmt.service;

import java.util.List;

import com.afour.emgmt.model.UserDTO;
import com.afour.emgmt.model.UserRegistrationDTO;

/**
 * 
 */
public interface VisitorService {

	List<UserDTO> fetchAllVisitors();

	UserDTO findVisitorByID(Integer ID);

	UserDTO findVisitorByUserName(String USERNAME);

	UserDTO addVisitor(UserDTO orgDTO);

	UserDTO updateVisitor(UserDTO dto);

	Boolean deleteVisitorByID(Integer ID);

	UserDTO registerVisitorForEvent(UserRegistrationDTO dto);

}
