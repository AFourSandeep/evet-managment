/**
 * 
 */
package com.afour.emgmt.mapper;

import java.util.List;
import java.util.Set;

import com.afour.emgmt.entity.User;
import com.afour.emgmt.model.UserDTO;

/**
 * 
 */
public interface UserMapper {
	
	UserDTO entityToDTO(User entity);
	
	User DTOToEntity(UserDTO dto);
	
	List<UserDTO> entityToDTO(List<User> entities);
	
	List<User> DTOToEntity(List<UserDTO> dtos);

	User prepareForUpdate(User entity, UserDTO orgDTO);

	User prepareForCreate(UserDTO dto);

	Set<UserDTO> entityToDTO(Set<User> visitors);

}
