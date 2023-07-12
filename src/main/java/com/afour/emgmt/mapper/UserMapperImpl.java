/**
 * 
 */
package com.afour.emgmt.mapper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.afour.emgmt.config.AuthenticationFacade;
import com.afour.emgmt.entity.User;
import com.afour.emgmt.model.UserDTO;
import com.afour.emgmt.util.UtilConstant;

/**
 * 
 */
@Component
public class UserMapperImpl implements UserMapper {

	private final ModelMapper modelMapper;

	private final PasswordEncoder passwordEncoder;

	private final AuthenticationFacade authentication;

	public UserMapperImpl(ModelMapper modelMapper, PasswordEncoder passwordEncoder, AuthenticationFacade authentication) {
		this.modelMapper = modelMapper;
		this.passwordEncoder = passwordEncoder;
		this.authentication = authentication;
	}

	@Override
	public UserDTO entityToDTO(User entity) {
		return modelMapper.map(entity, UserDTO.class);
	}

	@Override
	public User DTOToEntity(UserDTO dto) {
		return modelMapper.map(dto, User.class);
	}

	@Override
	public List<UserDTO> entityToDTO(List<User> entities) {
		return entities
				.stream()
				.map(this::entityToDTO)
				.collect(Collectors.toList());
	}

	@Override
	public List<User> DTOToEntity(List<UserDTO> dtos) {
		return dtos
				.stream()
				.map(this::DTOToEntity)
				.collect(Collectors.toList());
	}

	@Override
	public User prepareForUpdate(User entity, UserDTO dto) {
		if (null != dto.getFirstName())
			entity.setFirstName(dto.getFirstName());
		if (null != dto.getLastName())
			entity.setLastName(dto.getLastName());
		if (null != dto.getIsActive())
			entity.setActive(dto.getIsActive());
		if(null != dto.getPassword())
			entity.setPassword(passwordEncoder.encode(dto.getPassword()));
		
		entity.setUpdatedAt(LocalDateTime.now());
		entity.setUpdatedBy(authentication.getActor());
		
		return entity;
	}

	@Override
	public User prepareForCreate(UserDTO dto) {
		final String ACTOR = authentication.getActor();

		User entity = this.DTOToEntity(dto);
		entity.setCreatedAt(LocalDateTime.now());
		entity.setCreatedBy(ACTOR);
		entity.setUpdatedAt(LocalDateTime.now());
		entity.setUpdatedBy(ACTOR);
		entity.setPassword(passwordEncoder.encode(dto.getPassword()));
		return entity;
	}

	@Override
	public Set<UserDTO> entityToDTO(Set<User> entities) {
		return entities
				.stream()
				.map(this::entityToDTO)
				.collect(Collectors.toSet());
	}

}
