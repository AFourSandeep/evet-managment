/**
 * 
 */
package com.afour.emgmt.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.afour.emgmt.entity.Organizer;
import com.afour.emgmt.entity.Role;
import com.afour.emgmt.mapper.OrganizerMapper;
import com.afour.emgmt.model.OrganizerDTO;
import com.afour.emgmt.repository.OrganizerRepository;
import com.afour.emgmt.repository.RoleRepository;
import com.afour.emgmt.util.ActorEnum;
import com.afour.emgmt.util.RoleEnum;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 */
@Slf4j
@Service
public class OrganizerServiceImpl implements OrganizerService {

	@Autowired
	OrganizerMapper mapper;

	@Autowired
	OrganizerRepository repository;
	
	@Autowired
	RoleRepository roleRepository;

	@Override
	public List<OrganizerDTO> fetchAllOrganizers() {
		List<Organizer> entities = repository.findAll();
		if (null == entities)
			return null;
		log.info("DB operation success! Fetched {} Organizers!",entities.size());
		return mapper.entityToDTO(entities);
	}

	@Override
	public OrganizerDTO findOrganizerByID(final Integer ID) {
		Optional<Organizer> optional = repository.findById(ID);
		if (optional.isEmpty())
			return null;
		
		Organizer entity= optional.get();
		log.info("DB operation success! Fetched Organizer:{} ", entity.getOrganizerId());
		return mapper.entityToDTO(entity);
	}

	@Override
	public OrganizerDTO findOrganizerByUserName(final String USERNAME) {
		Organizer entity = repository.findByUserName(USERNAME);
		log.info("DB operation success!");
		if (null == entity)
			return null;

		log.info("DB operation success! Fetched Organizer:{} by username: {}", entity.getOrganizerId(), USERNAME);
		return mapper.entityToDTO(entity);
	}

	@Override
	public OrganizerDTO addOrganizer(final OrganizerDTO dto) {
		Organizer entity = mapper.DTOToEntity(dto);
		entity.setCreatedAt(LocalDateTime.now());
		entity.setCreatedBy(ActorEnum.DEFAULT_USER.getUser());
		entity.setUpdatedAt(LocalDateTime.now());
		entity.setUpdatedBy(ActorEnum.DEFAULT_USER.getUser());
		
		Role role = roleRepository.findById(RoleEnum.ORGANIZER.getRoleId()).get();
		entity.setRole(role);

		entity = repository.save(entity);
		log.info("DB operation success! Added Organizer : {}", entity.getOrganizerId());
		return mapper.entityToDTO(entity);
	}

	@Override
	public OrganizerDTO updateOrganizer(final OrganizerDTO dto) {
		Optional<Organizer> optional = repository.findById(dto.getOrganizerId());

		if (optional.isEmpty())
			return null;

		Organizer entity  = mapper.prepareForUpdate(optional.get(), dto);
		entity = repository.save(entity);

		log.info("DB operation success! Updated Organizer : {}", entity.getOrganizerId());
		return mapper.entityToDTO(entity);
	}

	@Override
	public Boolean deleteOrganizerByID(final Integer ID) {
		Boolean exist = repository.existsById(ID);

		if (exist)
			repository.deleteById(ID);

		exist = repository.existsById(ID);
		log.info("DB operation success! Deleted the Organizer : {}", !exist);
		return !exist;
	}

}
