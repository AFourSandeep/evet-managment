/**
 * 
 */
package com.afour.emgmt.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import com.afour.emgmt.entity.Organizer;
import com.afour.emgmt.mapper.OrganizerMapper;
import com.afour.emgmt.model.OrganizerDTO;
import com.afour.emgmt.repository.OrganizerRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 */
@Slf4j
@Service
public class OgranizerServiceImpl implements OrganizerService {

	@Autowired
	OrganizerMapper mapper;

	@Autowired
	OrganizerRepository repository;

	@Override
	public List<OrganizerDTO> fetchAllOrganizers() {
		List<Organizer> entities = repository.findAll();
		log.info("DB operation success!");
		if (null == entities)
			return null;
		return mapper.entityToDTO(entities);
	}

	@Override
	public OrganizerDTO addAnOrganizer(OrganizerDTO orgDTO)
			throws OptimisticLockingFailureException, IllegalArgumentException {
		Organizer entity = mapper.DTOToEntity(orgDTO);
		if (null == orgDTO.getOrganizerId()) {
			entity.setCreatedAt(LocalDateTime.now());
			entity.setCreatedBy("System");
		}
		entity.setUpdatedAt(LocalDateTime.now());
		entity.setUpdatedBy("System");

		entity = repository.save(entity);
		log.info("DB operation success!");
		return mapper.entityToDTO(entity);
	}

	@Override
	public OrganizerDTO findOrganizerByID(final Integer ID) {
		Organizer entity = repository.findById(ID).get();
		log.info("DB operation success!");
		if (null == entity)
			return null;
		else
			return mapper.entityToDTO(entity);
	}

	@Override
	public OrganizerDTO findOrganizerByUserName(final String USERNAME) {
		Organizer entity = repository.findByUserName(USERNAME);
		log.info("DB operation success!");
		if (null == entity)
			return null;
		else
			return mapper.entityToDTO(entity);
	}

	@Override
	public OrganizerDTO updateAnOrganizer(OrganizerDTO orgDTO) {

		final Integer ID = orgDTO.getOrganizerId();
		Organizer entity = repository.findById(ID).get();

		log.info("DB operation success! Fetched Organizer : {}", entity);

		if (null == entity)
			return null;
		
		entity = mapper.prepareForUpdate(entity,orgDTO);
		entity = repository.save(entity);

		return mapper.entityToDTO(entity);
	}

}
