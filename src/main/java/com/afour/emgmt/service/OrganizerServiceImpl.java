/**
 * 
 */
package com.afour.emgmt.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.afour.emgmt.entity.Role;
import com.afour.emgmt.entity.User;
import com.afour.emgmt.exception.NoDataFoundException;
import com.afour.emgmt.exception.UndefinedRoleException;
import com.afour.emgmt.exception.UserAlreadyExistException;
import com.afour.emgmt.mapper.UserMapper;
import com.afour.emgmt.model.UserDTO;
import com.afour.emgmt.repository.RoleRepository;
import com.afour.emgmt.repository.UserRepository;
import com.afour.emgmt.util.UtilConstant;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 */
@Slf4j
@Service
public class OrganizerServiceImpl implements OrganizerService {

	@Autowired
	UserMapper mapper;

	@Autowired
	UserRepository repository;

	@Autowired
	RoleRepository roleRepository;

	@Override
	public List<UserDTO> fetchAllOrganizers() {
		List<User> entities = repository.findAll();
		log.info("DB operation success! Fetched {} Organizers!", entities.size());
		return mapper.entityToDTO(entities);
	}

	@Override
	public UserDTO findOrganizerByID(final Integer ID) throws NoDataFoundException {
		Optional<User> optional = repository.findById(ID);
		return optional.map(e -> {
			UserDTO user = mapper.entityToDTO(e);
			log.info("DB operation success! Fetched User:{} ", user.getUserId());
			return user;
		}).orElseThrow(() -> new NoDataFoundException());
	}

	@Override
	public UserDTO findOrganizerByUserName(final String USERNAME) throws NoDataFoundException {
		Optional<User> optional = repository.findByUserName(USERNAME);
		return optional.map(e -> {
			UserDTO user = mapper.entityToDTO(e);
			log.info("DB operation success! Fetched User:{} by username: {}", user.getUserId(), USERNAME);
			return user;
		}).orElseThrow(() -> new NoDataFoundException());
	}

	@Override
	public UserDTO addOrganizer(final UserDTO dto) throws UserAlreadyExistException, UndefinedRoleException {
		repository.findByUserName(dto.getUserName())
		.ifPresent(u -> new UserAlreadyExistException());

		Role role = roleRepository.findByRoleNameIgnoreCase(UtilConstant.ROLE_ORGANIZER)
				.orElseThrow(() -> new UndefinedRoleException());

		User entity = mapper.prepareForCreate(dto);
		entity.setRole(role);

		entity = repository.save(entity);
		log.info("DB operation success! Added User : {}", entity.getUserId());
		return mapper.entityToDTO(entity);
	}

	@Override
	public UserDTO updateOrganizer(final UserDTO dto) throws NoDataFoundException {
		User user = repository.findById(dto.getUserId())
				.map(e -> mapper.prepareForUpdate(e, dto))
				.orElseThrow(() -> new NoDataFoundException());

		user = repository.save(user);
		log.info("DB operation success! Updated Organizer : {}", user.getUserId());
		return mapper.entityToDTO(user);
	}

	@Override
	public Boolean deleteOrganizerByID(final Integer ID) throws NoDataFoundException {
		Boolean exist = repository.existsById(ID);
		if (!exist)
			throw new NoDataFoundException();

		repository.deleteById(ID);

		exist = repository.existsById(ID);
		log.info("DB operation success! Deleted the Organizer : {}", !exist);
		return !exist;
	}

}
