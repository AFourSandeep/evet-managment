/**
 * 
 */
package com.afour.emgmt.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.afour.emgmt.common.RoleEnum;
import com.afour.emgmt.entity.Role;
import com.afour.emgmt.entity.User;
import com.afour.emgmt.exception.NoDataFoundException;
import com.afour.emgmt.mapper.UserMapper;
import com.afour.emgmt.model.UserDTO;
import com.afour.emgmt.repository.RoleRepository;
import com.afour.emgmt.repository.UserRepository;

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
	public List<UserDTO> fetchAllOrganizers() throws NoDataFoundException {
		List<User> entities = repository.findAll();
		if (null == entities)
			throw new NoDataFoundException();
		log.info("DB operation success! Fetched {} Organizers!", entities.size());
		return mapper.entityToDTO(entities);
	}

	@Override
	public UserDTO findOrganizerByID(final Integer ID) throws NoDataFoundException {
		Optional<User> optional = repository.findById(ID);
		if (optional.isEmpty())
			throw new NoDataFoundException();

		User entity = optional.get();
		log.info("DB operation success! Fetched User:{} ", entity.getUserId());
		return mapper.entityToDTO(entity);
	}

	@Override
	public UserDTO findOrganizerByUserName(final String USERNAME) throws NoDataFoundException{
		Optional<User> optional = repository.findByUserName(USERNAME);
		if (optional.isEmpty())
			throw new NoDataFoundException();
		
		User entity = optional.get();
		log.info("DB operation success! Fetched User:{} by username: {}", entity.getUserId(), USERNAME);
		return mapper.entityToDTO(entity);
	}

	@Override
	public UserDTO addOrganizer(final UserDTO dto) {
		User entity = mapper.prepareForCreate(dto);

		Role role = roleRepository.findById(RoleEnum.ORGANIZER.getRoleId()).get();
		entity.setRole(role);

		entity = repository.save(entity);
		log.info("DB operation success! Added User : {}", entity.getUserId());
		return mapper.entityToDTO(entity);
	}

	@Override
	public UserDTO updateOrganizer(final UserDTO dto) throws NoDataFoundException{
		Optional<User> optional = repository.findById(dto.getUserId());
		if (optional.isEmpty())
			throw new NoDataFoundException();

		User entity = mapper.prepareForUpdate(optional.get(), dto);
		entity = repository.save(entity);

		log.info("DB operation success! Updated Organizer : {}", entity.getUserId());
		return mapper.entityToDTO(entity);
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
